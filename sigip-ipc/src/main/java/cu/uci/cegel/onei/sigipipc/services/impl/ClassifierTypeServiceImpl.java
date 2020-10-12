package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.ClassifierType;
import cu.uci.cegel.onei.sigipipc.persistence.QClassifierType;
import cu.uci.cegel.onei.sigipipc.repository.ClassifierTypeRepository;
import cu.uci.cegel.onei.sigipipc.services.ClassifierTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifierTypeServiceImpl implements ClassifierTypeService {

    @Autowired
    ClassifierTypeRepository classifierTypeRepository;

    @Override
    public List<ClassifierType> findAll() {
        return (List<ClassifierType>) classifierTypeRepository.findAll();
    }

    @Override
    public List<ClassifierType> classifierTypesPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<ClassifierType> page = classifierTypeRepository.findAll(pageable);
            List<ClassifierType> classifierTypes = page.getContent();
            return classifierTypes;
        } else {
            return findAll();
        }
    }

    @Override
    public ClassifierType classifierTypeById(Long id) {
        ClassifierType classifierType = classifierTypeRepository.findById(id).get();
        return classifierType;
    }

    @Override
    public int totalClassifierTypes() {
        List<ClassifierType> classifierTypes = (List<ClassifierType>) classifierTypeRepository.findAll();
        return classifierTypes.size();
    }

    @Override
    public ClassifierType addClassifierTypes(String description, Boolean active, String codeClassifier) {
        ClassifierType classifierType = new ClassifierType(description, active, codeClassifier);
        return classifierTypeRepository.save(classifierType);
    }

    @Override
    public ClassifierType editClassifierTypes(Long id, String description, Boolean active, String codeClassifier) {
        ClassifierType classifierType = classifierTypeRepository.findById(id).get();
        classifierType.setDescription(description);
        classifierType.setActive(active);
        classifierType.setCodeClassifier(codeClassifier);
        return classifierTypeRepository.save(classifierType);
    }

    @Override
    public List<ClassifierType> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<ClassifierType> page = classifierTypeRepository.findAll(getPredicate(description), pageable);
            List<ClassifierType> classifierTypes = page.getContent();
            return classifierTypes;
        } else {
            List<ClassifierType> classifierTypes = (List<ClassifierType>) classifierTypeRepository.findAll(getPredicate(description), Sort.by("description"));
            return classifierTypes;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<ClassifierType> classifierTypes = (List<ClassifierType>) classifierTypeRepository.findAll(getPredicate(description), Sort.by("description"));
        return classifierTypes.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QClassifierType.classifierType.description.toLowerCase().contains(description.toLowerCase());
    }
}
