package com.nse.data.report.positional.impl;

import com.nse.data.report.positional.AbstractPositionalCloseReport;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeeklyCloseReport extends AbstractPositionalCloseReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isWeeklyExpiry(date)){
                filteredData.put(date, data.get(date));
            }
        }

        return filteredData;
    }
}
