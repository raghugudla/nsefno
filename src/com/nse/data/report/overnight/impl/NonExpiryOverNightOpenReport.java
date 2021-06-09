package com.nse.data.report.overnight.impl;

import com.nse.data.report.AbstractReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class NonExpiryOverNightOpenReport extends AbstractReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        LocalDate prevDay = null;

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isNonExpiry(date) && prevDay != null) {
                filteredData.put(date,
                        new Float[] {
                                data.get(date)[0],
                                data.get(prevDay)[1],
                                percent(data.get(prevDay)[1], data.get(date)[0])
                        });
            }
            prevDay = date;
        }

        return filteredData;
    }

    @Override
    public List<String> getRowHeader() {
        return Arrays.asList("Date", "Open", "Prev_Close", "Chg %");
    }
}
