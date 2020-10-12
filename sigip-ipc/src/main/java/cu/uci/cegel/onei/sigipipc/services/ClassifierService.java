package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.ClassifierInput;
import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassifierService {

    Classifier findOneByDescription(String description);

    List<Classifier> getAll();

    List<Classifier> findAllByClassifierTypeEquals(Long id_Classifier_Type);

    List<Classifier> findAllByClassifierTypeEquals(Long id_Classifier_Type, int page, int size);

    Classifier manageClassifier(ClassifierInput input);

    Long deleteClassifier(Long id);

    List<Classifier> classifierById(Long id);

    Classifier findById(Long id);

    int totalClassifierXClassifierType(Long id);

    List<Classifier> classifierByMarket(Long id_market, Long id_Establishment, int size, int page);

    int totalClassifierByMarket( Long id_market, Long id_Establishment);

    List<Classifier> filters(String descripcion, int page, int size);

    List<Classifier> filters(String descripcion, int page, int size, int tipo);

    List<Classifier> variedadesFiltradasByMarket(Long id_market, Long id_Establishment, int size, int page, String descripcion );

    Integer totalvariedadesFiltradasByMarket(Long id_market, Long id_Establishment, String descripcion);


}
