package com.ossrep.ercot;

import com.ossrep.ercot.tdspesiidextract.TdspEsiidExtract;
import com.ossrep.ercot.tdspesiidextract.TdspEsiidExtractReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TdspEsiidExtractTest {

    @Test
    public void fetchLatestForCenterpoint() {
        TdspEsiidExtractReport tdspEsiidExtractReport = TdspEsiidExtract.fetchLatest(TdspEsiidExtract.TDSP.CENTERPOINT);
        Assertions.assertTrue(tdspEsiidExtractReport.getTdsp().equals(TdspEsiidExtract.TDSP.CENTERPOINT));
    }

    @Test
    public void fetchForCenterpoint() {
        List<TdspEsiidExtractReport> tdspEsiidExtractReports = TdspEsiidExtract.fetchLatestSince(TdspEsiidExtract.TDSP.CENTERPOINT, LocalDate.now().minus(3, ChronoUnit.DAYS));
        Assertions.assertTrue(tdspEsiidExtractReports.size() > 2);
    }

}
