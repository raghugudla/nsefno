package com.nse.data.report.overnight;

import com.nse.data.report.ReportCommons;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface OverNightDataReport extends ReportCommons {

    void crunch(String dir, int dateIndex, int openIndex, int closeIndex) throws Exception;

    List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data);

    List<String> getRowHeader();
}
