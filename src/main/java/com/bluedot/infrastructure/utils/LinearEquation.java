package com.bluedot.infrastructure.utils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 21:10
 *
 * 线性方程组，也称为一元一次方程
 *
 * y = Ax + B
 */
public class LinearEquation {
    private BigDecimal A;
    private BigDecimal B;

    public LinearEquation(BigDecimal a, BigDecimal b) {
        A = a;
        B = b;
    }

    public BigDecimal getA() {
        return A;
    }

    public void setA(BigDecimal a) {
        A = a;
    }

    public BigDecimal getB() {
        return B;
    }

    public void setB(BigDecimal b) {
        B = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinearEquation that = (LinearEquation) o;
        return A.compareTo(that.A) == 0 && B.compareTo(that.B) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(A, B);
    }

    @Override
    public String toString() {
        return "y = "+A+"x + "+B;
    }

    public BigDecimal calculateY(BigDecimal x){
        return x.multiply(A).add(B);
    }
}
