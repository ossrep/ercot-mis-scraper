package com.ossrep.ercot.tdspesiidextract;

import com.ossrep.ercot.MisScraper;
import com.ossrep.ercot.Report;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TdspEsiidExtract {

    public static final int REPORT_ID = 203;

    public enum TDSP {
        CENTERPOINT, ONCOR_ELEC, SWEPCO_ENERG, NUECES_ELEC, TNMP,
        AEP_CENTRAL, AEP_NORTH, AEP_TEXAS_SP, SHARYLAND_UTILITIES, SHARYLAND_MCALLEN
    }

    public static TdspEsiidExtractReport fetchLatest(TDSP tdsp) {
        List<Report> reportList = MisScraper.fetchReports(REPORT_ID).stream()
                .filter(report -> report.getTitle().contains(tdsp.name()))
                .collect(Collectors.toList());
        Report latestReport = reportList.get(0);
        List<File> fileList = MisScraper.downloadReport(latestReport);
        List<TdspEsiidExtractRecord> records = MisScraper.convertCsvFile(fileList.get(0), TdspEsiidExtractRecord.class);
        TdspEsiidExtractReport report = TdspEsiidExtractReport.builder()
                .title(latestReport.getTitle())
                .url(latestReport.getUrl())
                .tdsp(tdsp)
                .dateTime(latestReport.getDateTime())
                .records(records)
                .build();
        return report;
    }

    public static List<TdspEsiidExtractReport> fetchLatestSince(TDSP tdsp, LocalDate localDate) {
        List<Report> reportList = MisScraper.fetchReports(REPORT_ID).stream()
                .filter(report -> report.getTitle().contains(tdsp.name()))
                .filter(report -> report.getDateTime().toLocalDate().isEqual(localDate) || report.getDateTime().toLocalDate().isAfter(localDate))
                .collect(Collectors.toList());
        List<TdspEsiidExtractReport> reports = new ArrayList<>();
        reportList.forEach(report -> {
            List<File> fileList = MisScraper.downloadReport(report);
            List<TdspEsiidExtractRecord> records = MisScraper.convertCsvFile(fileList.get(0), TdspEsiidExtractRecord.class);
            TdspEsiidExtractReport tdspEsiidExtractReport = TdspEsiidExtractReport.builder()
                    .title(report.getTitle())
                    .url(report.getUrl())
                    .tdsp(tdsp)
                    .dateTime(report.getDateTime())
                    .records(records)
                    .build();
            reports.add(tdspEsiidExtractReport);
        });
        return reports;
    }

}
