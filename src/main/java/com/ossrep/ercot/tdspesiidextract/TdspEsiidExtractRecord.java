package com.ossrep.ercot.tdspesiidextract;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class TdspEsiidExtractRecord {

    @CsvBindByPosition(position = 0)
    private String esiid;

    @CsvBindByPosition(position = 1)
    private String address;

    @CsvBindByPosition(position = 2)
    private String city;

    @CsvBindByPosition(position = 3)
    private String state;

    @CsvBindByPosition(position = 4)
    private String zipCodePlusFour;

    @CsvBindByPosition(position = 5)
    private String duns;

    @CsvBindByPosition(position = 6)
    private String meterReadCycle;

    @CsvBindByPosition(position = 7)
    private String status;

    @CsvBindByPosition(position = 8)
    private String premiseType;

    @CsvBindByPosition(position = 9)
    private String powerRegion;

    @CsvBindByPosition(position = 10)
    private String stationCode;

    @CsvBindByPosition(position = 11)
    private String stationName;

    @CsvBindByPosition(position = 12)
    private String metered;

    @CsvBindByPosition(position = 13)
    private String openServiceOrders;

    @CsvBindByPosition(position = 14)
    private String polrCustomerClass;

    @CsvBindByPosition(position = 15)
    private String settlementAmsIndicator;

    @CsvBindByPosition(position = 16)
    private String tdspAmsIndicator;

    @CsvBindByPosition(position = 17)
    private String switchHoldIndicator;

}
