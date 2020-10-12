package cu.uci.cegel.onei.sigipipc.resolvers.establecimiento;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import cu.uci.cegel.onei.sigipipc.persistence.Establishment;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;
import cu.uci.cegel.onei.sigipipc.services.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "EstablecimientoResolver")
public class EstablecimientoResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private EstablishmentService establishmentService;

    //Queries
    public List<Establishment> establishments() {
        return establishmentService.findAll();
    }

    public List<Establishment> establishmentsPaging(int page, int size) {
        return establishmentService.findAllPaging(page, size);
    }

    public int totalEstablishment() {
        return establishmentService.totalEstablishment();
    }

    public Establishment establishmentById(Long id) {
        Establishment e =  establishmentService.establishmentById(id);
        return e;
    }


    public List<Establishment> filterEstablishments(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, int page, int size) {
        return establishmentService.filters(marketId, typologyId, dpaId, establishment, code, stateId, page, size);
    }

    public List<Establishment> pendingEstablishments(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, int page, int size) {
        return establishmentService.pending(marketId, typologyId, dpaId, establishment, code, stateId, page, size);
    }

    public int totalEstablishmentsByFilter(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId) {
        return establishmentService.totalEstablishmentsByFilter(marketId, typologyId, dpaId, establishment, code, stateId);
    }
     public boolean isEstablishmentCatched(Long idEstablishment){
        return establishmentService.isCatched(idEstablishment);
     }

    //Mutations
    public Establishment addEstablishment(String organization, String name, String address, String contact, Integer phone, Long marketId, Long typologyId, Long dpaId, Long idnEstado) {
        return establishmentService.add(organization,
                name,
                address,
                contact,
                phone,
                marketId,
                typologyId,
//              oneiIndexId,
                dpaId,
                idnEstado);
    }

    public Establishment editEstablishment(Long id, String name, String address, String contact, Integer phone) {
        return establishmentService.edit(id, name, address, contact, phone);
    }

    public Boolean deleteEstablishment(Long id) {
        return establishmentService.delete(id);
    }

    public List<VarietyCharactSpecific> varietyCharactSpecificList(Long classifierId) {
        return establishmentService.varietyCharactSpecificList(classifierId);
    }

}
