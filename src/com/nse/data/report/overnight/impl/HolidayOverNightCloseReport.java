package com.nse.data.report.overnight.impl;

import com.nse.data.report.overnight.AbstractOverNightDataReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.time.temporal.ChronoUnit.DAYS;

public class HolidayOverNightCloseReport extends AbstractOverNightDataReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        LocalDate prevDay = null;

        for(LocalDate date: data.keySet()) {
            if(prevDay != null && DAYS.between(date, prevDay) >1 && EXPIRY_DATE_UTILS.isNonExpiry(prevDay)) {
                filteredData.put(prevDay, new Float[] {data.get(prevDay)[1], data.get(date)[1]});
            }
            prevDay = date;
        }

        return filteredData;
    }

    @Override
    public List<String> getRowHeader() {
        return Arrays.asList("Date", "Close", "Prev_Close", "Chg %");
    }
}
