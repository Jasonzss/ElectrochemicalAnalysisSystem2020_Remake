package com.bluedot.infrastructure.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 19:59
 */
public class BigDecimalUtil {
    private static final int DEFAULT_SCALE = 10;

    public static BigDecimal sqrt(BigDecimal value, int scale){
        BigDecimal num2 = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num2, mc);
            cnt++;
        }

        deviation = deviation.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return deviation;
    }

    public static BigDecimal sqrt(BigDecimal value){
        return sqrt(value, value.scale());
    }
}
