package com.nse.data.report;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface Report {

    void crunch(String dir, int dateIndex, int valueIndex) throws Exception;

    void crunch(String dir, int dateIndex, int openIndex, int closeIndex) throws Exception;

    List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data);

    SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data);

    List<String> getRowHeader();
}
