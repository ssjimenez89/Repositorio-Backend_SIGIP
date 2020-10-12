package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.persistence.MeasurementUnitType;
import cu.uci.cegel.onei.sigipipc.persistence.QMeasurementUnitType;
import cu.uci.cegel.onei.sigipipc.repository.MeasurementUnitTypeRepository;
import cu.uci.cegel.onei.sigipipc.services.MeasurementUnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementUnitTypeServiceImpl implements MeasurementUnitTypeService {

    @Autowired
    private MeasurementUnitTypeRepository measurementUnitTypeRepository;


    @Override
    public List<MeasurementUnitType> findAll() {
        return (List<MeasurementUnitType>) measurementUnitTypeRepository.findAll();
    }

    @Override
    public MeasurementUnitType measurementUnitTypeById(Long id) {
        return measurementUnitTypeRepository.findById(id).get();
    }


    @Override
    public MeasurementUnitType findByDescription(String description) {
        return measurementUnitTypeRepository.findByDescriptionEquals(description);
    }

    @Override
    public List<MeasurementUnitType> measurementUnitTypesActives() {
        return measurementUnitTypeRepository.findByActiveIsTrue();
    }

    @Override
    public MeasurementUnitType addMeasurementUnitType(String descrip, boolean activo) {
        MeasurementUnitType measurementUnitType = new MeasurementUnitType(descrip, activo);
        return measurementUnitTypeRepository.save(measurementUnitType);
    }

    @Override
    public MeasurementUnitType editMeasurementUnitType(long id, String descrip, boolean activo) {
        MeasurementUnitType measurementUT = measurementUnitTypeRepository.findById(id).get();
        measurementUT.setActive(activo);
        measurementUT.setDescription(descrip);
        return measurementUnitTypeRepository.save(measurementUT);

    }

    @Override
    public List<MeasurementUnitType> measurementUnitTypePaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<MeasurementUnitType> page = measurementUnitTypeRepository.findAll(pageable);
            List<MeasurementUnitType> measurementUnitTypes = page.getContent();
            return measurementUnitTypes;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalMeasurementUnitType() {
        List<MeasurementUnitType> measurementUnitTypes = (List<MeasurementUnitType>) measurementUnitTypeRepository.findAll();
        return measurementUnitTypes.size();
    }

    @Override
    public List<MeasurementUnitType> findByDescriptionContains(String description, int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("description"));
            Page<MeasurementUnitType> page = measurementUnitTypeRepository.findAll(getPredicate(description), pageable);
            List<MeasurementUnitType> measurementUnitTypes = page.getContent();
            return measurementUnitTypes;
        } else {
            List<MeasurementUnitType> measurementUnitTypes = (List<MeasurementUnitType>) measurementUnitTypeRepository.findAll(getPredicate(description), Sort.by("description"));
            return measurementUnitTypes;
        }
    }

    @Override
    public int totalByDescriptionContains(String description) {
        List<MeasurementUnitType> measurementUnitTypes = (List<MeasurementUnitType>) measurementUnitTypeRepository.findAll(getPredicate(description), Sort.by("description"));
        return measurementUnitTypes.size();
    }

    public BooleanExpression getPredicate(String description) {
        return QMeasurementUnitType.measurementUnitType.description.toLowerCase().contains(description.toLowerCase());
    }


}
