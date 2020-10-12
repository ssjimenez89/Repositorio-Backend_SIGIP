package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.QWeighingType;
import cu.uci.cegel.onei.sigipipc.persistence.WeighingType;
import cu.uci.cegel.onei.sigipipc.repository.WeighingTypeRepository;
import cu.uci.cegel.onei.sigipipc.services.WeighingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeighingTypeServiceImpl implements WeighingTypeService {

    @Autowired
    WeighingTypeRepository weighingTypeRepository;

    @Override
    public List<WeighingType> findAll() {
        return (List<WeighingType>) weighingTypeRepository.findAll();
    }

    @Override
    public List<WeighingType> weighingTypesPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<WeighingType> page = weighingTypeRepository.findAll(pageable);
            List<WeighingType> weighingTypes = page.getContent();
            return weighingTypes;
        } else {
            return findAll();
        }
    }

    @Override
    public WeighingType weighingTypeById(Long id) {
        WeighingType weighingType = weighingTypeRepository.findById(id).get();
        return weighingType;
    }

    @Override
    public int totalWeighingTypes() {
        List<WeighingType> weighingTypes = (List<WeighingType>) weighingTypeRepository.findAll();
        return weighingTypes.size();
    }

    @Override
    public List<WeighingType> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<WeighingType> page = weighingTypeRepository.findAll(getPredicate(description), pageable);
            List<WeighingType> weighingTypes = page.getContent();
            return weighingTypes;
        } else {
            List<WeighingType> weighingTypes = (List<WeighingType>) weighingTypeRepository.findAll(getPredicate(description), Sort.by("description"));
            return weighingTypes;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<WeighingType> weighingTypes = (List<WeighingType>) weighingTypeRepository.findAll(getPredicate(description), Sort.by("description"));
        return weighingTypes.size();
    }

    @Override
    public WeighingType addWeighingType(String description, Boolean active) {
        WeighingType weighingType = new WeighingType(description, active);
        return weighingTypeRepository.save(weighingType);
    }

    @Override
    public WeighingType editWeighingType(Long id, String description, Boolean active) {
        WeighingType weighingType = weighingTypeRepository.findById(id).get();
        weighingType.setDescription(description);
        weighingType.setActive(active);
        return weighingTypeRepository.save(weighingType);
    }

    public BooleanExpression getPredicate(String description) {
        return QWeighingType.weighingType.description.toLowerCase().contains(description.toLowerCase());
    }
}
