package com.zkyong.demo.headfirst;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        BigDecimal capital = new BigDecimal("10000");
        BigDecimal interestRate = new BigDecimal("0.0441");
        for (int i = 1; i <= 360; i++) {
            System.out.println(calculateAverageCapitalPlusInteres(capital, interestRate, 360, i));            
        }
        
    }
    
    
    /**
     * 设贷款额为a，月利率为i，年利率为I，还款月数为n，每月还款额为b，还款利息总和为Y :
     *      1  I＝12×i 
     *      2  Y＝n×b－a 
     *      3  第n月还款利息为: (a×i－b)×(1＋i)^(n－1)+b 
     *      4  每月还款额为: b=a×i×(1＋i)^n÷((1＋i)^n－1〕
     */
    public static Map <String, Object> calculateAverageCapitalPlusInteres(BigDecimal a, BigDecimal I, int n, Integer term){
        Map <String, Object> mapout = new HashMap<>();
        mapout.put("term", term);
        BigDecimal i = I.divide(new BigDecimal("12"), 16, BigDecimal.ROUND_HALF_UP);
        BigDecimal ip1 = i.add(BigDecimal.ONE);
        BigDecimal ip1n = ip1.pow(n);
        BigDecimal ai = a.multiply(i);//ai
        BigDecimal b = ip1n.multiply(ai).divide(ip1n.subtract(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
        if(null != term) {
            BigDecimal b2 = ai.subtract(b).multiply(ip1.pow(term-1)).add(b).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal b1 = b.subtract(b2);
            mapout.put("b1", b1);
            mapout.put("b2", b2);
            mapout.put("b", b);
            mapout.put("Y", b.multiply(new BigDecimal(n)).subtract(a));
        }
        return mapout;
    }
}
