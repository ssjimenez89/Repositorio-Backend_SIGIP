package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Correlacionador;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorrelacionadorRepository extends PagingAndSortingRepository<Correlacionador, Long>, QuerydslPredicateExecutor<Correlacionador> {

   Correlacionador findCorrelacionadorByUmConvertir(Specification um);
   Correlacionador findCorrelacionadorByUmBaseAndUmConvertir(Specification base,Specification convertir);

}
