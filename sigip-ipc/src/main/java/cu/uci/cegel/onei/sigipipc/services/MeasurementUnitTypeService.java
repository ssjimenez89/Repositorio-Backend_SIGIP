package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.MeasurementUnitType;

import java.util.List;

public interface MeasurementUnitTypeService {

    List<MeasurementUnitType> findAll();

    MeasurementUnitType measurementUnitTypeById(Long id);

    MeasurementUnitType findByDescription(String description);

    List<MeasurementUnitType> measurementUnitTypesActives();

    MeasurementUnitType addMeasurementUnitType(String descrip, boolean activo);

    MeasurementUnitType editMeasurementUnitType(long id, String descrip, boolean activo);


    List<MeasurementUnitType> measurementUnitTypePaging(int page, int size);

    int totalMeasurementUnitType();

    List<MeasurementUnitType> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);
}

