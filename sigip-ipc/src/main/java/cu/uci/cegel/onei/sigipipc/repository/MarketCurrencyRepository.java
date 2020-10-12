package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.MarketCurrency;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketCurrencyRepository extends PagingAndSortingRepository<MarketCurrency, Long> {

}
