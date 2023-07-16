package com.bluedot.infrastructure.utils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 计量单位换算
 *
 * @author jason
 */
public class UnitUtil {
    /**
     * 四舍五入
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @return BigDecimal - 转换后的值
     */
    public static BigDecimal conversion(BigDecimal value, String original, String need) {
        return conversion(value, getUnit(original), getUnit(need));
    }

    /**
     * 指定小数点位数，四舍五入
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @param scale    - 小数点位数
     * @return BigDecimal - 转换后的值
     */
    public static BigDecimal conversion(BigDecimal value, String original, String need, final Integer scale) {
        return conversion(value, getUnit(original), getUnit(need), scale);
    }

    /**
     * 使用默认为数（2 或 need 的小数点位数）四舍五入
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @return BigDecimal - 转换后的值
     */
    public static BigDecimal conversion(final BigDecimal value, final Unit original, final Unit need) {
        String needString = need.rate.toString();
        int scale = 2;
        if (needString.indexOf('.') > 0) {
            scale = needString.substring(needString.indexOf('.')).length() - 1;
        }
        return conversion(value, original, need, scale);
    }

    /**
     * 转换主方法，指定小数点位数，四舍五入
     *
     * @param value    - 原始数值
     * @param original - 原始单位
     * @param need     - 转换的单位
     * @param scale    - 小数点位数
     * @return BigDecimal - 数值
     */
    public static BigDecimal conversion(final BigDecimal value, final Unit original, final Unit need, final Integer scale) {
        if (original == Unit.UN_KNOWN || need == Unit.UN_KNOWN) {
            throw new IllegalArgumentException("存在不支持的计量单位参数");
        }
        if (original.category != need.category) {
            throw new IllegalArgumentException(String.format("转换计量单位不统一 %s 不能转换为 %s ", original.category.name, need.category.name));
        }
        return value == null ? new BigDecimal(0) : value.multiply(need.rate).divide(original.rate, scale, RoundingMode.HALF_UP);
    }

    /**
     * 获取计量单位枚举
     *
     * @param unit - 计量单位名称
     * @return UnitsEnum - 计量单位
     */
    public static Unit getUnit(String unit) {
        if (!isEmpty(unit)) {
            for (Unit unitEnum : Unit.values()) {
                for (String possibleName : unitEnum.possibleNames) {
                    if (possibleName.equals(unit)) {
                        return unitEnum;
                    }
                }
            }
        }

        return Unit.UN_KNOWN;
    }

    /**
     * 获取计量单位集合
     *
     * @return Map<String, List < Map < String, String>>> - 计量单位集合
     */
    public static Map<String, List<Map<String, String>>> getUnitListMap() {
        Map<String, List<Map<String, String>>> listMap = new HashMap<>(Unit.CategoryEnum.values().length);
        for (Unit unit : Unit.values()) {
            if (unit.category != null) {
                String categoryName = unit.category.name;
                Map<String, String> lengthMap = new HashMap<>(2);
                lengthMap.put("code", unit.getUnit());
                lengthMap.put("name", unit.getDescription());
                List<Map<String, String>> list = listMap.get(categoryName);
                if (list == null || list.isEmpty()) {
                    list = new ArrayList<>();
                }
                list.add(lengthMap);
                listMap.put(categoryName, list);
            }
        }
        return listMap;
    }

    /**
     * 获取计量单位类型
     *
     * @return List<Map < String, String>> - 计量单位类型
     */
    public static List<Map<String, String>> getCategoryList() {
        List<Map<String, String>> categoryList = new ArrayList<>();
        for (Unit.CategoryEnum category : Unit.CategoryEnum.values()) {
            Map<String, String> lengthMap = new HashMap<>(2);
            lengthMap.put("code", category.getName());
            lengthMap.put("name", category.getDescription());
            categoryList.add(lengthMap);
        }
        return categoryList;
    }

    /**
     * 计量单位枚举
     */
    public enum Unit {
        /**
         * 长度计量单位
         */
        LG_KM(CategoryEnum.LENGTH, "km", new String[]{"km", "千米"}, new BigDecimal("0.001"), "千米"),
        LG_M(CategoryEnum.LENGTH, "m", new String[]{"m", "米"}, new BigDecimal("1"), "米"),
        LG_DM(CategoryEnum.LENGTH, "dm", new String[]{"dm", "分米"}, new BigDecimal("10"), "分米"),
        LG_CM(CategoryEnum.LENGTH, "cm", new String[]{"cm", "厘米"}, new BigDecimal("100"), "厘米"),
        LG_MM(CategoryEnum.LENGTH, "mm", new String[]{"mm", "毫米"}, new BigDecimal("1000"), "毫米"),
        LG_UM(CategoryEnum.LENGTH, "um", new String[]{"um", "微米"}, new BigDecimal("1000000"), "微米"),
        LG_NM(CategoryEnum.LENGTH, "nm", new String[]{"nm", "纳米"}, new BigDecimal("1000000000"), "纳米"),
        LG_INCH(CategoryEnum.LENGTH, "in", new String[]{"in", "inch", "英寸"}, new BigDecimal("39.3700787"), "英寸"),
        LG_FOOT(CategoryEnum.LENGTH, "ft", new String[]{"ft", "foot", "英尺"}, new BigDecimal("3.2808399"), "英尺"),
        LG_MILES(CategoryEnum.LENGTH, "mi", new String[]{"mi", "miles", "英里"}, new BigDecimal("0.00062137"), "英里"),
        LG_NAUTICAL_MILE(CategoryEnum.LENGTH, "nmile", new String[]{"nmile", "nauticalmile", "海里"}, new BigDecimal("0.00053996"), "海里"),

        /**
         * 质量计量单位
         */
        EG_T(CategoryEnum.WEIGHT, "t", new String[]{"t", "吨"}, new BigDecimal("0.001"), "吨"),
        EG_KG(CategoryEnum.WEIGHT, "kg", new String[]{"kg", "千克"}, new BigDecimal("1"), "千克"),
        EG_G(CategoryEnum.WEIGHT, "g", new String[]{"g", "克"}, new BigDecimal("1000"), "克"),
        EG_MG(CategoryEnum.WEIGHT, "mg", new String[]{"mg", "毫克"}, new BigDecimal("1000000"), "毫克"),
        EG_UG(CategoryEnum.WEIGHT, "μg", new String[]{"μg", "ug", "微克"}, new BigDecimal("1000000000"), "微克"),
        EG_LB(CategoryEnum.WEIGHT, "lb", new String[]{"lb", "lbs", "磅"}, new BigDecimal("2.2046226"), "磅"),
        EG_OZ(CategoryEnum.WEIGHT, "oz", new String[]{"oz", "盎司"}, new BigDecimal("35.2739619"), "盎司"),
        EG_CT(CategoryEnum.WEIGHT, "ct", new String[]{"ct", "克拉"}, new BigDecimal("5000"), "克拉"),

        /**
         * 温度计量单位
         */
        TG_DEGREE_CELSIUS(CategoryEnum.TEMPERATURE, "°C", new String[]{"°C", "degreecelsius", "摄氏度"}, new BigDecimal("1"), "摄氏度"),
        TG_FAHRENHEIT_SCALE(CategoryEnum.TEMPERATURE, "°F", new String[]{"°F", "fahrenheitscale", "华氏度"}, new BigDecimal("33.8"), "华氏度"),

        /**
         * 速度计量单位
         */
        TS_KM(CategoryEnum.SPEED, "km/h", new String[]{"km/h", "公里/小时"}, new BigDecimal("1"), "公里/小时"),
        TS_MILES(CategoryEnum.SPEED, "mi/h", new String[]{"mi/h", "英里/小时"}, new BigDecimal("0.62137119"), "英里/小时"),
        TS_NAUTICAL_MILE(CategoryEnum.SPEED, "nmile/h", new String[]{"nmile/h", "海里/小时"}, new BigDecimal("0.5399568"), "海里/小时"),
        TS_KNOT(CategoryEnum.SPEED, "knot", new String[]{"knot", "节"}, new BigDecimal("0.5399568"), "节"),
        TS_MACH(CategoryEnum.SPEED, "mach", new String[]{"mach", "machnumber", "马赫"}, new BigDecimal("0.00080985"), "马赫"),

        /**
         * 体积摩尔浓度单位（化学中的一种通用浓度单位）
         */
        MOL_M(CategoryEnum.MOLARITY, "mol/L", new String[]{"mol/L", "摩尔/升", "molar", "M"}, new BigDecimal("1"), "摩尔/升"),
        MOL_MM(CategoryEnum.MOLARITY, "mmol/L", new String[]{"mmol/L", "毫摩尔/升", "mM"}, new BigDecimal("1000"), "毫摩尔/升"),
        MOL_UM(CategoryEnum.MOLARITY, "μmol/L", new String[]{"μmol/L", "微摩尔/升", "μM"}, new BigDecimal("1000000"), "微摩尔/升"),
        MOL_NM(CategoryEnum.MOLARITY, "nmol/L", new String[]{"nmol/L", "纳摩尔/升", "nM"}, new BigDecimal("1000000000"), "纳摩尔/升"),
        MOL_PM(CategoryEnum.MOLARITY, "pmol/L", new String[]{"pmol/L", "皮摩尔/升", "pM"}, new BigDecimal("1000000000000"), "皮摩尔/升"),
        MOL_FM(CategoryEnum.MOLARITY, "fmol/L", new String[]{"fmol/L", "飞摩尔/升", "fM"}, new BigDecimal("1000000000000000"), "飞摩尔/升"),
        MOL_AM(CategoryEnum.MOLARITY, "amol/L", new String[]{"amol/L", "阿摩尔/升", "aM"}, new BigDecimal("1000000000000000000"), "阿摩尔/升"),
        MOL_ZM(CategoryEnum.MOLARITY, "zmol/L", new String[]{"zmol/L", "介摩尔/升", "zM"}, new BigDecimal("1000000000000000000000"), "介摩尔/升"),
        MOL_YM(CategoryEnum.MOLARITY, "ymol/L", new String[]{"ymol/L", "攸摩尔/升", "yM"}, new BigDecimal("1000000000000000000000000"), "攸摩尔/升"),

        /**
         * 电流强度计量单位
         */
        AMP_A(CategoryEnum.CURRENT, "A", new String[]{"A", "amp", "安", "安培"}, new BigDecimal("1"), "安"),
        AMP_MA(CategoryEnum.CURRENT, "mA", new String[]{"mA", "毫安"}, new BigDecimal("1000"), "毫安"),
        AMP_UA(CategoryEnum.CURRENT, "μA", new String[]{"μA", "微安"}, new BigDecimal("1000000"), "微安"),

        /**
         * 电压强度计量单位
         */
        VOL_V(CategoryEnum.VOLTAGE, "V", new String[]{"V", "伏", "伏特"}, new BigDecimal("1"), "伏"),
        VOL_KV(CategoryEnum.VOLTAGE, "kV", new String[]{"kV", "千伏"}, new BigDecimal("0.001"), "千伏"),
        VOL_MV(CategoryEnum.VOLTAGE, "mV", new String[]{"mV", "毫伏"}, new BigDecimal("1000"), "毫伏"),
        VOL_UV(CategoryEnum.VOLTAGE, "μV", new String[]{"μV", "微伏"}, new BigDecimal("1000000"), "微伏"),

        /**
         * 未知
         */
        UN_KNOWN(null, "未知", null, new BigDecimal("0"), "未知");

        /**
         * 计量类别
         */
        private final CategoryEnum category;
        /**
         * 计量单位
         */
        private final String units;
        /**
         * 可能的名称
         */
        private final String[] possibleNames;
        /**
         * 换算率
         */
        private final BigDecimal rate;
        /**
         * 描述
         */
        private final String description;

        Unit(CategoryEnum category, String units, String[] possibleNames, BigDecimal rate, String description) {
            this.category = category;
            this.units = units;
            this.possibleNames = possibleNames;
            this.rate = rate;
            this.description = description;
        }

        public CategoryEnum getCategory() {
            return category;
        }

        public String getUnit() {
            return units;
        }

        public String[] getPossibleNames() {
            return possibleNames;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public String getDescription() {
            return description;
        }

        /**
         * 计量类别
         */
        private enum CategoryEnum {
            /**
             * 长度
             */
            LENGTH("Length", Unit.LG_M, "长度"),
            /**
             * 质量
             */
            WEIGHT("Weight", Unit.EG_KG, "质量"),
            /**
             * 温度
             */
            TEMPERATURE("Temperature", Unit.TG_DEGREE_CELSIUS, "温度"),
            /**
             * 速度
             */
            SPEED("Speed", Unit.TS_KM, "速度"),
            /**
             * 体积摩尔浓度（化学中的一种通用浓度单位）
             */
            MOLARITY("Molarity", Unit.MOL_M, "体积摩尔浓度"),
            /**
             * 电流单位
             */
            CURRENT("Current", Unit.AMP_A, "电流强度"),
            /**
             * 体积摩尔浓度（化学中的一种通用浓度单位）
             */
            VOLTAGE("Voltage", Unit.VOL_V, "电压强度"),

            ;
            /**
             * 类别名称
             */
            private final String name;
            /**
             * 类别基准
             */
            private final Unit base;
            /**
             * 描述
             */
            private final String description;

            CategoryEnum(String name, Unit base, String description) {
                this.name = name;
                this.base = base;
                this.description = description;
            }

            public String getName() {
                return name;
            }

            public Unit getBase() {
                return base;
            }

            public String getDescription() {
                return description;
            }
        }
    }

    /**
     * 判断指定对象是否为空，支持：
     *
     * <pre>
     * 1. CharSequence
     * 2. Map
     * 3. Iterable
     * 4. Iterator
     * 5. Array
     * </pre>
     *
     * @param object 被判断的对象
     * @return 是否为空，如果类型不支持，返回false
     */
    public static Boolean isEmpty(final Object object) {
        if (null == object) {
            return Boolean.TRUE;
        }
        if (object instanceof CharSequence) {
            return 0 == object.toString().length();
        } else if (object instanceof Map) {
            return ((Map<?, ?>) object).isEmpty();
        } else if (object instanceof Iterable) {
            return !((Iterable<?>) object).iterator().hasNext();
        } else if (object instanceof Iterator) {
            return !((Iterator<?>) object).hasNext();
        } else if (object.getClass().isArray()) {
            return 0 == Array.getLength(object);
        }
        return Boolean.FALSE;
    }
}
