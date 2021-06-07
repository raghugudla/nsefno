package com.nse.data.report.overnight;

import com.nse.data.report.overnight.impl.*;

public class OverNightDataReportTest {

    public static void main(String[] args) throws Exception {
        OverNightDataReport report =
                new HolidayOverNightOpenReport(); /*potential setup on nf/bnf*/
                new HolidayOverNightCloseReport();
                new MonthlyExpiryOverNightCloseReport();
                new MonthlyExpiryOverNightOpenReport(); /*potential setup on nf/bnf*/
                new WeeklyExpiryOverNightOpenReport(); /*potential setup on nf/bnf*/
                new WeeklyExpiryOverNightCloseReport();
                new NonExpiryOverNightOpenReport();
                new NonExpiryOverNightCloseReport();
        report.crunch("nf-weekly", 0, 1, 4);
    }
}
