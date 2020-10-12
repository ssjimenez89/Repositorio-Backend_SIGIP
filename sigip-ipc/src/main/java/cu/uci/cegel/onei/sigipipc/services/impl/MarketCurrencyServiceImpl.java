package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.model.MercadoMonedaResource;
import cu.uci.cegel.onei.sigipipc.persistence.MarketCurrency;
import cu.uci.cegel.onei.sigipipc.repository.MarketCurrencyRepository;
import cu.uci.cegel.onei.sigipipc.services.MarketCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketCurrencyServiceImpl implements MarketCurrencyService {

    @Autowired
    MarketCurrencyRepository marketCurrencyRepository;

    @Override
    public List<MercadoMonedaResource> getAllMarketCurrency() {
        List<MarketCurrency> all = (List<MarketCurrency>) marketCurrencyRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));
        return all.stream().map(marketCurrency -> {
            String moneda = marketCurrency.getCurrency().getDescription();
            String mercado = marketCurrency.getMarket().getDescription();
            return MercadoMonedaResource.builder()
                    .id(marketCurrency.getId())
                    .description(mercado + "-" + moneda)
                    .build();
        }).collect(Collectors.toList());
    }
}
