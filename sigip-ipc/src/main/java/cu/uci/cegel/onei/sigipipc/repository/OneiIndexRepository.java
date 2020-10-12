package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.OneiIndex;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneiIndexRepository extends PagingAndSortingRepository<OneiIndex, Long> {
}
