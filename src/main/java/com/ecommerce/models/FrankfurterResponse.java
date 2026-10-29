package com.ecommerce.models;

import java.util.Map;

public record FrankfurterResponse(
    Double amount, 
    String base, 
    String date, 
    Map<String, Double> rates
) {}