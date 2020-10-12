package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Market;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarketRepository extends PagingAndSortingRepository<Market, Long>, QuerydslPredicateExecutor<Market> {

    List<Market> findMarketByDescriptionContains(String description);


}
