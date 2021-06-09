package com.nse.data.report.overnight.impl;

import com.nse.data.report.AbstractReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MonthlyExpiryOverNightOpenReport extends AbstractReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        LocalDate expDay = null;

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isMonthlyExpiry(date)) {
                expDay = date;
            }
            else if(expDay != null) {
                filteredData.put(expDay,
                        new Float[] {
                                data.get(expDay)[0],
                                data.get(date)[1],
                                percent(data.get(expDay)[0], data.get(date)[1])
                        });
                expDay = null;
            }
        }

        return filteredData;
    }

    @Override
    public List<String> getRowHeader() {
        return Arrays.asList("Date", "Open", "Prev_Close", "Chg %");
    }
}
