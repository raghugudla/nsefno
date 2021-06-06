package com.nse.data.report.overnight.impl;

import com.nse.data.report.intraday.AbstractIntraDayCloseReport;
import com.nse.data.report.overnight.AbstractOverNightDataReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class NonExpiryOverNightCloseReport extends AbstractOverNightDataReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        LocalDate prevDay = null;

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isNonExpiry(date) && prevDay != null) {
                filteredData.put(date, new Float[] {data.get(date)[1], data.get(prevDay)[1]});
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
