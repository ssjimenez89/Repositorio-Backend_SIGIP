package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Currency;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends PagingAndSortingRepository<Currency, Long>, QuerydslPredicateExecutor<Currency> {

    Currency findByDescriptionEquals(String description);

    Currency findByAcronymEquals(String acronym);

    List<Currency> findByActiveIsTrue();

}
