package com.nse.data.crunch;

import com.nse.data.crunch.util.ExpiryDateUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface ReportCommons {

    DecimalFormat DF = new DecimalFormat("###.##");

    ExpiryDateUtils EXPIRY_DATE_UTILS = ExpiryDateUtils.getInstance();

    SortedMap<LocalDate, Float[]> filter(final SortedMap<LocalDate, Float[]>  data);

    default String percent(final float f1, final float f2) {
        return DF.format(Math.abs((100*(f2-f1)/f1)));
    }

    default void printResults(List<List<String>> records) {
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
}
