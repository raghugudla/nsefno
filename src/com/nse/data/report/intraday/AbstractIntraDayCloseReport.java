package com.nse.data.report.intraday;

import com.nse.data.report.AbstractReport;
import com.nse.data.report.util.CSVUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

public abstract class AbstractIntraDayCloseReport extends AbstractReport implements IntraDayCloseReport {

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
        //System.out.println("filteredData = " + filteredData);

        this.records = generateRecords(filteredData);

        CSVUtil.writeTo(dir, records);

        printResults(records);
    }

    @Override
    public List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data){
        List<List<String>> records = new ArrayList<>();
        List<String> header = Arrays.asList("Date", "Open", "Close", "Chg %");
        records.add(header);

        for(LocalDate date : data.keySet()) {

            Float[] prices = data.get(date);

            List<String> row = new ArrayList<>();
            row.add(date.toString());
            row.add(String.valueOf(prices[0]));
            row.add(String.valueOf(prices[1]));
            row.add(percent(prices[0], prices[1]));

            records.add(row);
        }

        return records;
    }
}
