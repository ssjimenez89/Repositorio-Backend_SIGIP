package cu.uci.cegel.onei.sigipipc.resolvers.variedad;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.CharactSpecificVariety;
import cu.uci.cegel.onei.sigipipc.model.VariedadEstablecimientoResource;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyEstablishment;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Resolver(name = "VariedadEstablecimientoResolver")
public class VariedadEstablecimientoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private VarietyEstablishmentService varietyEstablishmentService;

    // Queries

    public Boolean isMonthClosed() {
        return false;
    }

    public VarietyEstablishment varietyEstablishmentByid(Long id) {
        return varietyEstablishmentService.findByid(id);
    }

    public VariedadEstablecimientoResource varietyEstablishmentResourceByid(Long id) {
        return varietyEstablishmentService.varietyEstablishmentResourceByid(id);
    }

    public List<VarietyEstablishment> varietyEstablishmentPaging(int size, int page) {
        return varietyEstablishmentService.findAllPaging(size, page);
    }

    public int totalVarietyEstablishment() {
        return varietyEstablishmentService.totalVarietyEstablishment();
    }

    public List<VarietyEstablishment> filterVarietyEstablishment(Long marketId, Long typologyId, Long dpaId, String establishment, String variety, Long stateId, int page, int size) {
        return varietyEstablishmentService.filters(marketId, typologyId, dpaId, establishment, variety, stateId, page, size);
    }

    public List<VariedadEstablecimientoResource> filterVarietyEstablishmentResource(Long marketId, Long typologyId, Long dpaId, String establishment, String variety, Long stateId, int page, int size) {
        return varietyEstablishmentService.filtersResource(marketId, typologyId, dpaId, establishment, variety, stateId, page, size);
    }

    public int totalVarietyEstablishmentByFilter(Long marketId, Long typologyId, Long dpaId, String establishment, String variety, Long stateId, int page, int size) {
        return varietyEstablishmentService.totalByFilter(marketId, typologyId, dpaId, establishment, variety, stateId, page, size);
    }

    public Boolean isAllCathedValidation() {
        LocalDate end = LocalDate.now();
        LocalDate start = LocalDate.of(end.getYear(), end.getMonth(), 1);

        return varietyEstablishmentService.CantvariedadesCaptadas(start, end) == 0;
    }

    // Mutations
    public List<VarietyEstablishment> addVarietyEstablishment(Long establishmentId, Long stateId, List<CharactSpecificVariety> varietyCharactSpecificsId, List<Long> planningListId, Long unidadMedidaId, Long cantidadId) {
        return varietyEstablishmentService.add(establishmentId, stateId, varietyCharactSpecificsId, planningListId, unidadMedidaId, cantidadId);
    }

    public List<VarietyEstablishment> editVarietyEstablishment(Long id, List<CharactSpecificVariety> charactSpecificVarietyList, List<Long> planningListId, Long unidadMedidaId, Long cantidadId) {
        return varietyEstablishmentService.edit(id, charactSpecificVarietyList, planningListId, unidadMedidaId, cantidadId);
    }

    public Boolean deleteVarietyEstablishment(Long id) {
        return varietyEstablishmentService.delete(id);
    }

    public VarietyEstablishment setEstadoVarietyEstablishment(Long varietyStabId, Long stateId) {
        return varietyEstablishmentService.setEstadoVarietyEstablishment(varietyStabId, stateId);
    }

    public VarietyEstablishment setActiveVarietyEstablishment(Long id) {
        return varietyEstablishmentService.setActive(id);
    }


}
