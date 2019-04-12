package com.zkyong.util.xstream;

import java.util.Date;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.zkyong.exception.date.DateFormatException;
import com.zkyong.util.date.DateUtil;
/**
 * 实现 XStream格式化时间的接口
 * @author zkyong
 * @date 2017年9月26日 13:52:28
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
        return DateUtil.date2StringYMDHMSSZ((Date)arg0);
    }
}
