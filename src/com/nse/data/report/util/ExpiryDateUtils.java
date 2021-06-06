package com.nse.data.report.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class ExpiryDateUtils {

    private static List<LocalDate> monthlyExpiries = null;
    private static List<LocalDate> weeklyExpiries = null;
    private static ExpiryDateUtils EXPIRY_DATE_UTILS = null;

    private ExpiryDateUtils() {
        weeklyExpiries = new ArrayList<>();
        monthlyExpiries = new ArrayList<>();
        try {
            SortedSet<LocalDate> tradingDays = CSVUtil.readFrom("nifty", 0);
            setExpiries(tradingDays);
        } catch (Exception e) {
            e.printStackTrace();
            setExpiries();
        }
    }

    private void setExpiries(final SortedSet<LocalDate> tradingDays) {
        boolean isMonthlyExpCaptured = false;
        boolean isWeeklyExpCaptured = false;

        for(LocalDate date: tradingDays) {

            LocalDate lastThu = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

            if (date.isAfter(lastThu)) {
                isMonthlyExpCaptured = false;
            } else if(lastThu.equals(date) || !isMonthlyExpCaptured) {
                monthlyExpiries.add(date);
                isMonthlyExpCaptured = true;
                isWeeklyExpCaptured = true;
                continue;
            }

            LocalDate expDay = date.with(DayOfWeek.THURSDAY);

            if (date.isAfter(expDay)) {
                isWeeklyExpCaptured = false;
            } else if(expDay.equals(date) || !isWeeklyExpCaptured) {
                weeklyExpiries.add(date);
                isWeeklyExpCaptured = true;
            }
        }
    }

    private void setExpiries() {
        boolean isMonthlyExpCaptured = false;
        boolean isWeeklyExpCaptured = false;
        LocalDate cutoffDate = LocalDate.of(2000,1,1);
        LocalDate date = LocalDate.now();

        while (date.isAfter(cutoffDate)) {

            LocalDate lastThu = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));

            if (date.isAfter(lastThu)) {
                isMonthlyExpCaptured = false;
            } else if(lastThu.equals(date) || !isMonthlyExpCaptured) {
                if(date.getDayOfWeek().equals(DayOfWeek.THURSDAY)) {
                    monthlyExpiries.add(date);
                    isMonthlyExpCaptured = true;
                    isWeeklyExpCaptured = true;
                    date = date.minusDays(1);
                    continue;
                }
                date = date.minusDays(1);
            }

            LocalDate expDay = date.with(DayOfWeek.THURSDAY);

            if (date.isAfter(expDay)) {
                isWeeklyExpCaptured = false;
            } else if(expDay.equals(date) || !isWeeklyExpCaptured) {
                weeklyExpiries.add(date);
                isWeeklyExpCaptured = true;
            }

            date = date.minusDays(1);
        }
    }

    public static synchronized ExpiryDateUtils getInstance() {
        EXPIRY_DATE_UTILS = (EXPIRY_DATE_UTILS == null)
                    ? new ExpiryDateUtils()
                    : EXPIRY_DATE_UTILS;

        return EXPIRY_DATE_UTILS;
    }

    public boolean isWeeklyExpiry(final LocalDate date) {
        return weeklyExpiries.contains(date);
    }

    public boolean isMonthlyExpiry(final LocalDate date) {
        return monthlyExpiries.contains(date);
    }

    public boolean isExpiry(final LocalDate date) {
        return (monthlyExpiries.contains(date) || weeklyExpiries.contains(date));
    }

    public boolean isNonExpiry(final LocalDate date) {
        return !isExpiry(date);
    }
}
