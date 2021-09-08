package com.nse.data.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Test {
    public static void main(String[] args) throws ParseException {

        String[] strArr = {};
        List<String> list = Arrays.asList(strArr);
        System.out.println(list.stream().reduce((result , value) -> result + ", " + value).get());
    }
}
