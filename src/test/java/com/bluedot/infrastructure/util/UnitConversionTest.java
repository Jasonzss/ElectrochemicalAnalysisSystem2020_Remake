package com.bluedot.infrastructure.util;

import com.bluedot.BaseTest;
import com.bluedot.infrastructure.utils.UnitConversion;
import com.bluedot.infrastructure.utils.UnitConversion.Unit;
import org.junit.Test;

/**
 * @author Jason
 * @creationDate 2023/07/14 - 12:51
 */
public class UnitConversionTest extends BaseTest {
    @Test
    public void test(){
        Unit unit = UnitConversion.getUnit("mol/L");
        log.info(unit.getDescription());
    }
}
