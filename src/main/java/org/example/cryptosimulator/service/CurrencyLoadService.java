package org.example.cryptosimulator.service;

import org.example.cryptosimulator.model.Currency;
import org.example.cryptosimulator.repo.CurrencyRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyLoadService {

    private final CurrencyRepository currencyRepository;

    private final WebClient webClient;

    public CurrencyLoadService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        this.webClient = WebClient.create("https://api.coingecko.com/api/v3");
    }

    public void loadTop10Currencies() {
        List<Map<String, Object>> response = webClient.get().uri(uriBuilder -> uriBuilder.path("/coins/markets").queryParam("vs_currency", "usd").queryParam("order", "market_cap_desc").queryParam("per_page", "10").queryParam("page", "1").build()).retrieve().bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
        }).block();

        if (response != null) {
            for (Map<String, Object> coin : response) {
                String id = (String) coin.get("id");             // "bitcoin"
                String symbol = ((String) coin.get("symbol")).toUpperCase(); // "BTC"
                String name = (String) coin.get("name");         // "Bitcoin"
                Number priceNumber = (Number) coin.get("current_price");
                Double price = priceNumber.doubleValue();

                if (!currencyRepository.existsByCoingeckoId(id)) {
                    Currency currency = new Currency(symbol, name, id, price);
                    currencyRepository.save(currency);
                }
            }
        }
    }
}
