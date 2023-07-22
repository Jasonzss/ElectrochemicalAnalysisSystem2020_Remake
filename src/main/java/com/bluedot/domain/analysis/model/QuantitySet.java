package com.bluedot.domain.analysis.model;

import com.bluedot.infrastructure.utils.UnitUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 17:54
 */
public class QuantitySet {
    private List<BigDecimal> values;
    private UnitUtil.Unit unit;

    public QuantitySet(List<BigDecimal> values, UnitUtil.Unit unit) {
        this.values = values;
        this.unit = unit;
    }

    public List<BigDecimal> getValues() {
        return values;
    }

    public void setValues(List<BigDecimal> values) {
        this.values = values;
    }

    public UnitUtil.Unit getUnit() {
        return unit;
    }

    public void setUnit(UnitUtil.Unit unit) {
        this.unit = unit;
    }
}
