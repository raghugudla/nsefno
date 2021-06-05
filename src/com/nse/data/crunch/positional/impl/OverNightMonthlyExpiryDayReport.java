package com.nse.data.crunch.positional.impl;

import com.nse.data.crunch.positional.AbstractOverNightExpiryDayReport;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

public class OverNightMonthlyExpiryDayReport extends AbstractOverNightExpiryDayReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        LocalDate expDay = null;

        for(LocalDate date: data.keySet()) {
            if(EXPIRY_DATE_UTILS.isMonthlyExpiry(date)) {
                expDay = date;
            }
            else if(expDay != null) {
                filteredData.put(expDay, new Float[] {data.get(expDay)[0], data.get(date)[0]});
                expDay = null;
            }
        }

        return filteredData;
    }

    @Override
    public String toString(){
        return "OverNightMonthlyExpiryDayReport=[" + dir + "]";
    }

}
