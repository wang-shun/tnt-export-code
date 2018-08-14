package com.lvmama.tnt.common.util;

import java.math.BigDecimal;

/**
 *
 */
public class PriceUtil {

    public static float convertToYuan(final Long price) {
        if (price == null) {
            return 0f;
        }
        BigDecimal p = new BigDecimal(price);
        return p.divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP)
                .floatValue();
    }
}
