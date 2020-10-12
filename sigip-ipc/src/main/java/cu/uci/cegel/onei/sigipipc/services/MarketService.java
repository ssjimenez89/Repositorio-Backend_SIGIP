package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Market;

import java.util.List;

public interface MarketService {
    List<Market> findAll();

    Market add(String description, Boolean active);

    Market edit(Long id, String description, Boolean active);

    List<Market> findAllPaging(int page, int size);

    int totalMarket();

    Market marketById(Long id);

    List<Market> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    List<Market> marketByDescriptionContains(String description);
}
