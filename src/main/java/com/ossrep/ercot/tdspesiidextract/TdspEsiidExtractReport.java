package com.ossrep.ercot.tdspesiidextract;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class TdspEsiidExtractReport {

    private String title;
    private String url;
    private TdspEsiidExtract.TDSP tdsp;
    private LocalDateTime dateTime;
    private List<TdspEsiidExtractRecord> records;

}
