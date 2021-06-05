package com.nse.data.crunch.positional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

public abstract class AbstractOverNightExpiryDayReport extends AbstractOverNightDataReport {

    @Override
    public List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data, float initClose){
        List<List<String>> records = new ArrayList<>();
        List<String> header = Arrays.asList("Date", "Close", "Prev_Close", "Chg %");
        records.add(header);

        for(LocalDate date: data.keySet()) {
            float close = data.get(date)[0];
            float prevClose = data.get(date)[1];
            List<String> row = new ArrayList<>();
            row.add(date.toString());
            row.add(String.valueOf(close));
            row.add(String.valueOf(prevClose));
            row.add(percent(close, prevClose));

            records.add(row);
        }

        return records;
    }
}
