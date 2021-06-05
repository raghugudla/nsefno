package com.nse.data.crunch.positional;

import com.nse.data.crunch.ReportCommons;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface OverNightDataReport extends ReportCommons {

    /**
     * @param dir CSV files directory name under resources
     * @param dateIndex Date index
     * @param valueIndex close price index
     * @throws Exception
     */
    void crunch(String dir, Integer dateIndex, Integer valueIndex) throws Exception;

    List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data, float initClose);
}
