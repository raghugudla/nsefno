package com.nse.data.report;

import java.util.List;

public class AbstractReport {

    protected String dir = null;
    protected List<List<String>> records = null;

    @Override
    public String toString(){
        return this.getClass().getName() + "=[" + dir + "]";
    }
}
