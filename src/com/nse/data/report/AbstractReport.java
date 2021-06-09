package com.nse.data.report;

import com.nse.data.report.util.CSVUtil;
import com.nse.data.report.util.ExpiryDateUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public abstract class AbstractReport implements Report {

    protected String dir = null;
    protected List<List<String>> records = null;

    protected final DecimalFormat DF = new DecimalFormat("###.##");
    protected final ExpiryDateUtils EXPIRY_DATE_UTILS = ExpiryDateUtils.getInstance();

    protected float percent(final float f1, final float f2) {
        return (Math.abs((100*(f2-f1)/f1)));
    }

    protected void printResults(List<List<String>> records) {
        int zeroToThree = 0;
        int threeToFive = 0;
        int fiveToTen = 0;
        int tenPlus = 0;

        for(List<String> row: records.subList(1, records.size())) {
            float change = Float.parseFloat(row.get(row.size()-1));

            if(change < 1) {
                ++zeroToThree;
            } else if (change < 2) {
                ++threeToFive;
            } else if (change < 3) {
                ++fiveToTen;
            } else {
                ++tenPlus;
            }
        }

        int total = zeroToThree + threeToFive + fiveToTen + tenPlus;
        System.out.println("============ " + this + " ============");
        System.out.println("1. " + zeroToThree + " = " + (float)100*zeroToThree/total + "%");
        System.out.println("2. " + threeToFive + " = " + (float)100*threeToFive/total + "%");
        System.out.println("3. " + fiveToTen + " = " + (float)100*fiveToTen/total + "%");
        System.out.println("4. " + tenPlus + " = " + (float)100*tenPlus/total + "%");
    }

    @Override
    public List<List<String>> generateRecords(SortedMap<LocalDate, Float[]> data){
        List<List<String>> records = new ArrayList<>();
        records.add(getRowHeader());

        for(LocalDate date : data.keySet()) {

            Float[] prices = data.get(date);

            List<String> row = new ArrayList<>();
            row.add(date.toString());
            for(float price: prices) {
                row.add(DF.format(price));
            }

            records.add(row);
        }

        return records;
    }

    @Override
    public void crunch(String dir, int dateIndex, int valueIndex) throws Exception {
        if(dir == null) throw new IllegalArgumentException("Params missing");

        this.dir = dir;

        // Get the map,trading date and it's closing price in descending order
        SortedMap<LocalDate, Float[]> data = CSVUtil.readFrom(dir, dateIndex, valueIndex);

        process(data);
    }

    @Override
    public void crunch(String dir, int dateIndex, int openIndex, int closeIndex) throws Exception {
        if(dir == null) throw new IllegalArgumentException("Params missing");

        this.dir = dir;

        // Get the map,trading date and it's closing price in descending order
        SortedMap<LocalDate, Float[]> data = CSVUtil.readFrom(dir, dateIndex, openIndex, closeIndex);

        process(data);
    }

    private void process(final SortedMap<LocalDate, Float[]> data) throws Exception {

        // Filter data, get only expiry date and it's close price
        SortedMap<LocalDate, Float[]> filteredData = filter(data);

        this.records = generateRecords(filteredData);

        CSVUtil.writeTo(dir, records);

        printResults(records);
    }

    @Override
    public String toString(){
        return this.getClass().getName() + "=[" + dir + "]";
    }
}
