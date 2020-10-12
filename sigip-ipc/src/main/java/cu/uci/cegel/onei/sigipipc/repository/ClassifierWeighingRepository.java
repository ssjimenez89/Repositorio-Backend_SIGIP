package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.ClassifierWeighing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassifierWeighingRepository extends PagingAndSortingRepository<ClassifierWeighing, Long> {
    void deleteByClassifier_Id(long classifierId);
}
