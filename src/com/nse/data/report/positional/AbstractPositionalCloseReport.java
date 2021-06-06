package com.nse.data.report.positional;

import com.nse.data.report.AbstractReport;
import com.nse.data.report.util.CSVUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

public abstract class AbstractPositionalCloseReport extends AbstractReport implements PositionalCloseReport {

    @Override
    public void crunch(String dir, int dateIndex, int valueIndex) throws Exception {
        this.dir = dir;
        //csv files directory name
        //close price index in the csv file
        if(dir == null)
            throw new IllegalArgumentException("Params missing");

        // Get the map,trading date and it's closing price in descending order
        SortedMap<LocalDate, Float[]> data = CSVUtil.readFrom(dir, dateIndex, valueIndex);

        // Filter data, get only expiry date and it's close price
        SortedMap<LocalDate, Float[]> filteredData = filter(data);

        this.records = generateRecords(filteredData, data.get(data.firstKey())[0]);

        CSVUtil.writeTo(dir, records);

        printResults(records);
    }

    @Override
    public List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data, float initClose){
        List<List<String>> records = new ArrayList<>();
        List<String> header = Arrays.asList("Date", "Close", "Chg %");
        records.add(header);

        float prevClose = initClose;

        for(LocalDate date : data.keySet()) {

            float close = data.get(date)[0];

            List<String> row = new ArrayList<>();
            row.add(date.toString());
            row.add(String.valueOf(close));
            row.add(percent(prevClose, close));

            records.add(row);

            prevClose = close;
        }

        return records;
    }

}
