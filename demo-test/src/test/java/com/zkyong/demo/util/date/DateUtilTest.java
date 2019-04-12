package com.zkyong.demo.util.date;

import java.util.Date;

import com.zkyong.demo.date.DateUtil;
import com.zkyong.demo.exception.DateFormatException;

public class DateUtilTest {
    public static void main(String[] args) throws DateFormatException {
        Date fromDate = DateUtil.string2DateYMDHMSSZ("2017-10-07 16:02:30.121 CST");
        Date toDate = DateUtil.string2DateYMDHMSSZ("2017-11-07 16:02:30.221 CST");
        System.out.println(DateUtil.getDateInterval(fromDate, toDate));
    }
}
