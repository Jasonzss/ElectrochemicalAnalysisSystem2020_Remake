package com.bluedot.domain.process.model;

import com.bluedot.infrastructure.utils.Quantity;
import com.bluedot.infrastructure.utils.UnitConversion;

import java.math.BigDecimal;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:52
 *
 * 波形图点位
 */
public class Point {
    private static final UnitConversion.Unit DEFAULT_CURRENT_UNIT = UnitConversion.Unit.AMP_A;
    private static final UnitConversion.Unit DEFAULT_VOLTAGE_UNIT = UnitConversion.Unit.VOL_V;

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
