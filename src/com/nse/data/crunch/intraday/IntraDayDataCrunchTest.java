package com.nse.data.crunch.intraday;

import com.nse.data.crunch.intraday.impl.MonthlyExpiryIntraDayReport;
import com.nse.data.crunch.intraday.impl.NonExpiryIntraDayReport;
import com.nse.data.crunch.intraday.impl.WeeklyExpiryIntraDayReport;

public class IntraDayDataCrunchTest {

    public static void main(String[] args) throws Exception {
        IntraDayDataReport report =
                new NonExpiryIntraDayReport();
                new WeeklyExpiryIntraDayReport();
                new MonthlyExpiryIntraDayReport();

        report.crunch("nf-weekly", 0,1, 4);
    }
}
