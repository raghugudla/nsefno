package com.nse.data.report.intraday.impl;

import com.nse.data.report.AbstractReport;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class NonExpiryIntraDayCloseReport extends AbstractReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isNonExpiry(date)){
                Float[] prices = data.get(date);
                filteredData.put(date, new Float[] {
                        prices[0],
                        prices[1],
                        percent(prices[0], prices[1])
                });
            }
        }

        return filteredData;
    }

    @Override
    public List<String> getRowHeader() {
        return Arrays.asList("Date", "Open", "Close", "Chg %");
    }
}
