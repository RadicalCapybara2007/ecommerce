package com.ecommerce.models;

public record ProductSummary(
    String product_id,
    String title,
    Double price_usd,
    Double price_mxn,
    String stock_status,
    Integer available_units,
    String currency_conversion_source,
    String status
) {}