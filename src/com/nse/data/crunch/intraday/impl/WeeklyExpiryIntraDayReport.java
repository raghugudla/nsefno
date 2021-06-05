package com.nse.data.crunch.intraday.impl;

import com.nse.data.crunch.intraday.AbstractIntraDayDataReport;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class WeeklyExpiryIntraDayReport extends AbstractIntraDayDataReport {

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

    @Override
    public String toString(){
        return "WeeklyExpiryIntraDayReport=[" + dir + "]";
    }
}
