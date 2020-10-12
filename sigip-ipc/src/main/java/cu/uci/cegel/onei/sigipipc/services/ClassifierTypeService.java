package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.ClassifierType;

import java.util.List;

public interface ClassifierTypeService {

    List<ClassifierType> findAll();

    List<ClassifierType> classifierTypesPaging(int page, int size);

    ClassifierType classifierTypeById(Long id);

    int totalClassifierTypes();

    List<ClassifierType> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    ClassifierType addClassifierTypes(String description, Boolean active, String codeClassifier);

    ClassifierType editClassifierTypes(Long id, String description, Boolean active, String codeClassifier);
}
