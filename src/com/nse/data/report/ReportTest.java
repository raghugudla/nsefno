package com.nse.data.report;

import com.nse.data.report.intraday.impl.MonthlyExpiryIntraDayCloseReport;
import com.nse.data.report.intraday.impl.NonExpiryIntraDayCloseReport;
import com.nse.data.report.intraday.impl.WeeklyExpiryIntraDayCloseReport;
import com.nse.data.report.overnight.impl.*;
import com.nse.data.report.positional.impl.DailySMAReport;
import com.nse.data.report.positional.impl.MonthlyCloseReport;
import com.nse.data.report.positional.impl.WeeklyCloseReport;

public class ReportTest {
    public static void main(String[] args) throws Exception {
        Report report =
                new WeeklyExpiryIntraDayCloseReport();
                new MonthlyExpiryIntraDayCloseReport();
                new NonExpiryIntraDayCloseReport();


                new HolidayOverNightOpenReport(); /*potential setup on nf/bnf*/
                new HolidayOverNightCloseReport();
                new MonthlyExpiryOverNightCloseReport();
                new MonthlyExpiryOverNightOpenReport(); /*potential setup on nf/bnf*/
                new WeeklyExpiryOverNightOpenReport(); /*potential setup on nf/bnf*/
                new WeeklyExpiryOverNightCloseReport();
                new NonExpiryOverNightOpenReport();
                new NonExpiryOverNightCloseReport();
        //report.crunch("nf-weekly", 0, 1, 4);

        report =
                new DailySMAReport();
                new MonthlyCloseReport();
                new WeeklyCloseReport();
        report.crunch("nifty", 0, 4);
    }
}
