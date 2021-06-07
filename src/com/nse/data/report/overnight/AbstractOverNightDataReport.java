package com.nse.data.report.overnight;

import com.nse.data.report.AbstractReport;
import com.nse.data.report.util.CSVUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public abstract class AbstractOverNightDataReport extends AbstractReport implements OverNightDataReport {

    @Override
    public void crunch(String dir, int dateIndex, int openIndex, int closeIndex) throws Exception {
        this.dir = dir;
        //csv files directory name
        //close price index in the csv file
        if(dir == null)
            throw new IllegalArgumentException("Params missing");

        // Get the map,trading date and it's closing price in descending order
        SortedMap<LocalDate, Float[]> data = CSVUtil.readFrom(dir, dateIndex, openIndex, closeIndex);

        // Filter data, get only expiry date and it's close price
        SortedMap<LocalDate, Float[]> filteredData = filter(data);

        this.records = generateRecords(filteredData);

        CSVUtil.writeTo(dir, records);

        printResults(records);
    }

    @Override
    public List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data){
        List<List<String>> records = new ArrayList<>();
        records.add(getRowHeader());

        for(LocalDate date: data.keySet()) {
            float priceOne = data.get(date)[0];
            float priceTwo = data.get(date)[1];

            List<String> row = new ArrayList<>();
            row.add(date.toString());
            row.add(String.valueOf(priceOne));
            row.add(String.valueOf(priceTwo));
            row.add(percent(priceOne, priceTwo));
            records.add(row);
        }

        return records;
    }
}
