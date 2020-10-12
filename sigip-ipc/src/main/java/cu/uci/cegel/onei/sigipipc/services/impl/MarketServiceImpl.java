package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.Market;
import cu.uci.cegel.onei.sigipipc.persistence.QMarket;
import cu.uci.cegel.onei.sigipipc.repository.MarketRepository;
import cu.uci.cegel.onei.sigipipc.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    MarketRepository marketRepository;

    @Override
    public List<Market> findAll() {
        return (List<Market>) marketRepository.findAll(Sort.by("description"));
    }

    @Override
    public Market add(String description, Boolean active) {
        return marketRepository.save(new Market(description, active));
    }

    @Override
    public Market edit(Long id, String description, Boolean active) {
        Market market = marketRepository.findById(id).get();
        market.setDescription(description);
        market.setActive(active);
        return marketRepository.save(market);
    }

    @Override
    public List<Market> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Market> page = marketRepository.findAll(pageable);
            List<Market> markets = page.getContent();
            return markets;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalMarket() {
        List<Market> markets = (List<Market>) marketRepository.findAll();
        return markets.size();
    }

    @Override
    public Market marketById(Long id) {
        Market market = marketRepository.findById(id).get();
        return market;
    }

    @Override
    public List<Market> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<Market> page = marketRepository.findAll(getPredicate(description), pageable);
            List<Market> markets = page.getContent();
            return markets;
        } else {
            List<Market> markets = (List<Market>) marketRepository.findAll(getPredicate(description), Sort.by("description"));
            return markets;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<Market> markets = (List<Market>) marketRepository.findAll(getPredicate(description), Sort.by("description"));
        return markets.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QMarket.market.description.toLowerCase().contains(description.toLowerCase());
    }

    @Override
    public List<Market> marketByDescriptionContains(String description) {
        List<Market> market = marketRepository.findMarketByDescriptionContains(description);
        return market;
    }
}
