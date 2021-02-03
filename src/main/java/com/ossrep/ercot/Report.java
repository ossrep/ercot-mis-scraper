package com.ossrep.ercot;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Value
@Builder
public class Report {

    private String title;
    private String url;
    private LocalDateTime dateTime;
    private Integer doclookupId;

}
