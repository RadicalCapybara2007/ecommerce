package com.ecommerce.services;

import com.ecommerce.models.FrankfurterResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_URL = "https://api.frankfurter.app/latest?from=USD&to=MXN";

    public Double getMxnRate() {
        try {
            FrankfurterResponse response = restTemplate.getForObject(API_URL, FrankfurterResponse.class);
            
            // Si la API responde correctamente, extraemos el valor de MXN
            if (response != null && response.rates() != null && response.rates().containsKey("MXN")) {
                return response.rates().get("MXN");
            }
            return 20.00; // Valor de rescate si el JSON no trae la tasa
            
        } catch (Exception e) {
            // Tolerancia a fallos: Si la API de terceros falla, no tumbamos la app
            System.err.println("Error al consultar API externa Frankfurter: " + e.getMessage());
            return 20.00; 
        }
    }
}