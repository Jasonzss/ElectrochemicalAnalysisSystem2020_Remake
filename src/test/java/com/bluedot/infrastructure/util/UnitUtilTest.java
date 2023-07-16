package com.bluedot.infrastructure.util;

import com.bluedot.BaseTest;
import com.bluedot.infrastructure.utils.UnitUtil;
import com.bluedot.infrastructure.utils.UnitUtil.Unit;
import org.junit.Test;

/**
 * @author Jason
 * @creationDate 2023/07/14 - 12:51
 */
public class UnitUtilTest extends BaseTest {
    @Test
    public void test(){
        Unit unit = UnitUtil.getUnit("mol/L");
        log.info(unit.getDescription());
    }
}
