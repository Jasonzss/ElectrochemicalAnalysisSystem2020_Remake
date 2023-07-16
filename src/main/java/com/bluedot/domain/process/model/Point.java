package com.bluedot.domain.process.model;

import com.bluedot.infrastructure.utils.Quantity;
import com.bluedot.infrastructure.utils.UnitUtil;

import java.math.BigDecimal;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:52
 *
 * 波形图点位
 */
public class Point {
    private static final UnitUtil.Unit DEFAULT_CURRENT_UNIT = UnitUtil.Unit.AMP_A;
    private static final UnitUtil.Unit DEFAULT_VOLTAGE_UNIT = UnitUtil.Unit.VOL_V;

    private Quantity x;
    private Quantity y;

    public Point(Quantity x, Quantity y) {
        this.x = x;
        this.y = y;
    }

    public Point(BigDecimal x, BigDecimal y){
        this.x = new Quantity(x, DEFAULT_VOLTAGE_UNIT);
        this.y = new Quantity(y, DEFAULT_CURRENT_UNIT);
    }

    public Point(Double x, Double y){
        this.x = new Quantity(BigDecimal.valueOf(x), DEFAULT_VOLTAGE_UNIT);
        this.y = new Quantity(BigDecimal.valueOf(y), DEFAULT_CURRENT_UNIT);
    }

    public Point(String x, String y){
        this.x = new Quantity(new BigDecimal(x), DEFAULT_VOLTAGE_UNIT);
        this.y = new Quantity(new BigDecimal(y), DEFAULT_CURRENT_UNIT);
    }

    public Quantity getX() {
        return x;
    }

    public void setX(Quantity x) {
        this.x = x;
    }

    public Quantity getY() {
        return y;
    }

    public void setY(Quantity y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
