package com.nse.data.report.positional.impl;

import com.nse.data.report.AbstractReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MonthlyCloseReport extends AbstractReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        LocalDate prevExpDay = data.firstKey();

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isMonthlyExpiry(date)){
                filteredData.put(date, new Float[] {
                        data.get(date)[0],
                        data.get(prevExpDay)[0],
                        percent(data.get(prevExpDay)[0], data.get(date)[0])
                });
                prevExpDay = date;
            }
        }
        return filteredData;
    }

    @Override
    public List<String> getRowHeader() {
        return Arrays.asList("Date", "Close", "Chg %");
    }
}
