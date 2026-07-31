package com.abc.multiVendorEProject.service.Customer;

import java.math.BigDecimal;

public final class PricingConstants {

    private PricingConstants() {
    }

    public static final BigDecimal BULK_DISCOUNT_RATE_FOR_5_ITEMS = new BigDecimal("0.10");
    public static final BigDecimal BULK_DISCOUNT_RATE_FOR_10_ITEMS = new BigDecimal("0.15");
    public static final BigDecimal VENDOR_DISCOUNT_RATE = new BigDecimal("0.05");
    public static final BigDecimal FREE_SHIPPING_LIMIT = new BigDecimal("50.00");
    public static final BigDecimal BASE_SHIPPING_FEE = new BigDecimal("5.00");
    public static final BigDecimal EXTRA_VENDOR_FEE = new BigDecimal("3.00");
    public static final BigDecimal MAX_DISCOUNT_RATE = new BigDecimal("0.50");

}
