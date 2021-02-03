package com.ossrep.ercot;

import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MisScraper {

    private static final Logger log = LoggerFactory.getLogger(MisScraper.class);

    public static List<Report> fetchReports(int reportTypeId) {
        Document doc = null;
        try {
            doc = Jsoup.connect(String.format(Constants.URL_GET_REPORTS, reportTypeId)).get();
        } catch (IOException ioException) {
            throw new ScraperException("Unable to fetch reports from {}", String.format(Constants.URL_GET_REPORTS, reportTypeId));
        }
        Elements links = doc.select("a[href]");
        List<Report> reports = new ArrayList<>();
        links.forEach(element -> {
            String title = element.parent().parent().parent().child(0).text();
            String url = element.attr("href");
            Integer doclookupId = parseDoclookupIdFromUrl(url);
            Report report = Report.builder()
                    .title(title)
                    .url(String.format(Constants.URL_DOWNLOAD_REPORT, doclookupId))
                    .dateTime(parseDateTimeFromTitle(title))
                    .doclookupId(parseDoclookupIdFromUrl(url))
                    .build();
            reports.add(report);
            log.debug("{}", report);
        });
        return reports;
    }

    public static LocalDateTime parseDateTimeFromTitle(String title) {
        String[] split = title.split("\\.");
        return LocalDateTime.parse(split[3] + split[4], DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }

    public static Integer parseDoclookupIdFromUrl(String url) {
        return Integer.parseInt(url.substring(url.indexOf("doclookupId=") + 12));
    }

    public static List<File> downloadReport(Report report) {
        try {
            Connection.Response response = Jsoup.connect(report.getUrl()).maxBodySize(0).timeout(0).execute();
            BufferedInputStream bufferedInputStream = response.bodyStream();
            ZipInputStream zis = new ZipInputStream(bufferedInputStream);
            List<File> files = new ArrayList<>();
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File tempFile = File.createTempFile("ercot", zipEntry.getName());
                tempFile.deleteOnExit();
                try (FileOutputStream out = new FileOutputStream(tempFile)) {
                    IOUtils.copy(new ByteArrayInputStream(zis.readAllBytes()), out);
                }
                files.add(tempFile);
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            return files;
        } catch (IOException ioException) {
            throw new ScraperException("Unable to download report from %s", report.getUrl());
        }
    }

    public static <T> List<T> convertCsvFile(File file, Class type) {
        try {
            List<T> beans = new CsvToBeanBuilder(new FileReader(file))
                    .withType(type)
                    .withEscapeChar('\0')
                    .build()
                    .parse();
            return beans;
        } catch (FileNotFoundException e) {
            throw new ScraperException("Unable to parse from %s", file.getName());
        }
    }

}
