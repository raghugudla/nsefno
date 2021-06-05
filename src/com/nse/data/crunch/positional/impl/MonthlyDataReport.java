package com.nse.data.crunch.positional.impl;

import com.nse.data.crunch.positional.AbstractOverNightDataReport;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class MonthlyDataReport extends AbstractOverNightDataReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isMonthlyExpiry(date)){
                filteredData.put(date, data.get(date));
            }
        }
        return filteredData;
    }

    @Override
    public String toString(){
        return "MonthlyDataReport=[" + dir + "]";
    }
}