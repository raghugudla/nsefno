package com.nse.data.crunch.intraday;

import com.nse.data.crunch.ReportCommons;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface IntraDayDataReport extends ReportCommons {

    void crunch(String dir, Integer dateIndex, Integer openIndex, Integer closeIndex) throws Exception;

    List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data);
}
