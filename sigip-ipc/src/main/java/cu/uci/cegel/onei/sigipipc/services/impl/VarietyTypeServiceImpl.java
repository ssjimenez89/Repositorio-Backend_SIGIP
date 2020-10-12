package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.QVarietyType;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyType;
import cu.uci.cegel.onei.sigipipc.repository.VarietyTypeRepository;
import cu.uci.cegel.onei.sigipipc.services.VarietyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VarietyTypeServiceImpl implements VarietyTypeService {

    @Autowired
    VarietyTypeRepository varietyTypeRepository;

    @Override
    public List<VarietyType> findAll() {
        return (List<VarietyType>) varietyTypeRepository.findAll();
    }

    @Override
    public List<VarietyType> varietyTypesPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<VarietyType> page = varietyTypeRepository.findAll(pageable);
            List<VarietyType> varietyTypes = page.getContent();
            return varietyTypes;
        } else {
            return findAll();
        }
    }

    @Override
    public VarietyType varietyTypeById(Long id) {
        VarietyType varietyType = varietyTypeRepository.findById(id).get();
        return varietyType;
    }

    @Override
    public int totalVarietyTypes() {
        List<VarietyType> varietyTypes = (List<VarietyType>) varietyTypeRepository.findAll();
        return varietyTypes.size();
    }

    @Override
    public List<VarietyType> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<VarietyType> page = varietyTypeRepository.findAll(getPredicate(description), pageable);
            List<VarietyType> varietyTypes = page.getContent();
            return varietyTypes;
        } else {
            List<VarietyType> varietyTypes = (List<VarietyType>) varietyTypeRepository.findAll(getPredicate(description), Sort.by("description"));
            return varietyTypes;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<VarietyType> varietyTypes = (List<VarietyType>) varietyTypeRepository.findAll(getPredicate(description), Sort.by("description"));
        return varietyTypes.size();
    }

    @Override
    public VarietyType addVarietyType(String description, Boolean active) {
        VarietyType weighingType = new VarietyType(description, active);
        return varietyTypeRepository.save(weighingType);
    }

    @Override
    public VarietyType editVarietyType(Long id, String description, Boolean active) {
        VarietyType varietyType = varietyTypeRepository.findById(id).get();
        varietyType.setDescription(description);
        varietyType.setActive(active);
        return varietyTypeRepository.save(varietyType);
    }

    public BooleanExpression getPredicate(String description) {
        return QVarietyType.varietyType.description.toLowerCase().contains(description.toLowerCase());
    }
}
