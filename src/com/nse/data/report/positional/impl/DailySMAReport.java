package com.nse.data.report.positional.impl;

import com.nse.data.report.AbstractReport;

import java.time.LocalDate;
import java.util.*;

public class DailySMAReport  extends AbstractReport {

    @Override
    public SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data) {

        SortedSet<LocalDate> reverseSortedSet = new TreeSet<>(data.keySet());

        SortedMap<LocalDate, Float[]> filteredData = new TreeMap<>();
        Queue<Float> queue = new LinkedList<>();
        for(LocalDate date: reverseSortedSet) {
            float close = data.get(date)[0];
            queue.add(close);
            if(queue.size() == 20) {
                float sma20 = (queue.stream().reduce(Float::sum).orElse(0F))/20;
                filteredData.put(date,
                        new Float[]{
                                close,
                                sma20,
                                percent(close, sma20)
                        });
                queue.remove();
            }
        }

        return filteredData;
    }

    @Override
    public List<String> getRowHeader() {
        return Arrays.asList("Date", "Close", "SMA20", "Chg %");
    }
}
