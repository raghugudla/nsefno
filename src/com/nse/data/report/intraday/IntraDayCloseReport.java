package com.nse.data.report.intraday;

import com.nse.data.report.ReportCommons;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface IntraDayCloseReport extends ReportCommons {

    void crunch(String dir, int dateIndex, int openIndex, int closeIndex) throws Exception;

    List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data);
}
