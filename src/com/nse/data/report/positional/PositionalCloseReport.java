package com.nse.data.report.positional;

import com.nse.data.report.ReportCommons;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface PositionalCloseReport extends ReportCommons {

    /**
     * @param dir CSV files directory name under resources
     * @param dateIndex Date index
     * @param valueIndex close price index
     * @throws Exception
     */
    void crunch(String dir, int dateIndex, int valueIndex) throws Exception;

    List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data, float initClose);
}
