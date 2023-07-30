package com.bluedot.infrastructure.util;

import com.bluedot.BaseTest;
import com.bluedot.infrastructure.utils.PojoUtil;
import org.junit.Test;

/**
 * @author Jason
 * @creationDate 2023/06/26 - 17:16
 */
public class PojoUtilTest extends BaseTest {
    public static class User{
        private Integer a;
        private String b;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "User{" +
                    "a=" + a +
                    ", b='" + b + '\'' +
                    '}';
        }
    }

    @Test
    public void testUpdatePojo(){
        User u1 = new User();
        User u2 = new User();

        u1.setA(1);
        u1.setB("before");
        log.info(u1.toString());

        u2.setA(null);
        u2.setB("updated");

        PojoUtil.updatePojo(u2, u1);

        log.info(u1.toString());
    }
}
