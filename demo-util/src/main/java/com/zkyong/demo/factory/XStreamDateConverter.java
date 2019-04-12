package com.zkyong.demo.factory;

import java.util.Date;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.zkyong.demo.date.DateUtil;
import com.zkyong.demo.exception.DateFormatException;

/**
 * 实现 XStream格式化时间的接口
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 15:17:57
 */
public class XStreamDateConverter implements SingleValueConverter {

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") Class arg0) {
        return Date.class == arg0;
    }

    @Override
    public Object fromString(String arg0) {
        try {
            return DateUtil.string2DateYMDHMSSZ(arg0);
        } catch (DateFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString(Object arg0) {
        return DateUtil.date2StringYMDHMSSZ((Date) arg0);
    }
}
