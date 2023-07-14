package com.bluedot.infrastructure.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bluedot.infrastructure.utils.UnitConversion.Unit;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 14:00
 *
 * 数量：数值+单位
 */
public class Quantity {
    private BigDecimal value;
    private Unit unit;

    public Quantity(BigDecimal value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Quantity unitConvert(Unit unit){
        BigDecimal v = this.unit.getRate().multiply(value).divide(unit.getRate(), RoundingMode.HALF_UP);
        return new Quantity(v, unit);
    }


    @Override
    public String toString() {
        return value.toString()+unit.getUnits();
    }
}
