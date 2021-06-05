package com.nse.data.crunch.positional;

import com.nse.data.crunch.positional.impl.MonthlyDataReport;
import com.nse.data.crunch.positional.impl.OverNightMonthlyExpiryDayReport;
import com.nse.data.crunch.positional.impl.OverNightWeeklyExpiryDayReport;
import com.nse.data.crunch.positional.impl.WeeklyDataReport;

public class OverNightDataCrunchTest {

    public static void main(String[] args) throws Exception {
        OverNightDataReport report =
                new MonthlyDataReport();
                new WeeklyDataReport();
                new OverNightMonthlyExpiryDayReport();
                new OverNightWeeklyExpiryDayReport();

        report.crunch("nifty", 0,4);
    }
}
