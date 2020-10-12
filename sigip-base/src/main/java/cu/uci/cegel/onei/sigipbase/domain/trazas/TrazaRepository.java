package cu.uci.cegel.onei.sigipbase.domain.trazas;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TrazaRepository extends CrudRepository<Traza, String>
        , QuerydslPredicateExecutor<Traza>, QuerydslBinderCustomizer<QTraza> {

    @Override
    default void customize(QuerydslBindings bindings, QTraza traza) {
    }
}
