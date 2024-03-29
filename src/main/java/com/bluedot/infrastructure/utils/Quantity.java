package com.bluedot.infrastructure.utils;

import com.bluedot.infrastructure.utils.UnitUtil.Unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public Quantity(String quantity) {
        String[] a = quantity.split(" ");
        value = new BigDecimal(a[0]);
        unit = UnitUtil.getUnit(a[1]);
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getValue(Unit unit){
        return this.unit.getRate().multiply(value).divide(unit.getRate(), RoundingMode.HALF_UP);
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Quantity){
            Quantity o = (Quantity) obj;
            return UnitUtil.conversion(value, unit, o.getUnit()).compareTo(o.getValue()) == 0;
        }

        return false;
    }

    @Override
    public String toString() {
        return value.toString()+" "+unit.getUnit();
    }
}
