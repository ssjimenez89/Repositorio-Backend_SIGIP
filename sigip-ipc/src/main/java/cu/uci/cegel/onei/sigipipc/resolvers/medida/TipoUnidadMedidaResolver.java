package cu.uci.cegel.onei.sigipipc.resolvers.medida;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.MeasurementUnitType;
import cu.uci.cegel.onei.sigipipc.services.MeasurementUnitTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "TipoUnidadMedidaResolver")
public class TipoUnidadMedidaResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private MeasurementUnitTypeService measurementUnitTypeService;

    // Queries
    public List<MeasurementUnitType> allMeasurementUnitTypes() {
        return measurementUnitTypeService.findAll();
    }

    public MeasurementUnitType measurementUnitTypeById(long ident) {
        return measurementUnitTypeService.measurementUnitTypeById(ident);
    }

    public MeasurementUnitType measurementUnitTypeByDescrip(String descrip) {
        return measurementUnitTypeService.findByDescription(descrip);
    }

    public List<MeasurementUnitType> measurementUnitTypesActives() {
        return measurementUnitTypeService.measurementUnitTypesActives();
    }

    public List<MeasurementUnitType> measurementUnitTypesPaging(int page, int size) {
        return measurementUnitTypeService.measurementUnitTypePaging(page, size);
    }

    public int totalMeasurementUnitType() {
        return measurementUnitTypeService.totalMeasurementUnitType();
    }

    public List<MeasurementUnitType> measurementUnitTypeByDescriptionContains(String description, int page, int size) {
        return measurementUnitTypeService.findByDescriptionContains(description, page, size);
    }

    public int totalMeasurementUnitTypeByDescriptionContains(String description) {
        return measurementUnitTypeService.totalByDescriptionContains(description);
    }

    // Mutations
    public MeasurementUnitType addMeasurementUnitType(String description, boolean active) {
        return measurementUnitTypeService.addMeasurementUnitType(description, active);
    }

    public MeasurementUnitType editMeasurementUnitType(long id, String description, boolean active) {
        return measurementUnitTypeService.editMeasurementUnitType(id, description, active);
    }

}
