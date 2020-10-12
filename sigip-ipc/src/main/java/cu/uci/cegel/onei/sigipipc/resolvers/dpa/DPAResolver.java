package cu.uci.cegel.onei.sigipipc.resolvers.dpa;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.DPAResource;
import cu.uci.cegel.onei.sigipipc.persistence.DPA;
import cu.uci.cegel.onei.sigipipc.services.DPAService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Resolver(name = "DPAResolver")
public class DPAResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private DPAService dpaService;

    // Queries
    public List<DPA> dpas() {
        return dpaService.getAllActive();
    } //habia findAll()

    public List<DPAResource> findAllDPAResource() {
        return dpaService.findAllDPAResource();
    }

    public List<DPA> dpasPaging(int page, int size) {
        return dpaService.dpasPaging(page, size);
    }

    public List<DPA> allChildrensByIdParent(long idPadre) {
        return dpaService.allChildrensByIdParent(idPadre);
    }

    public int totalDpa() {
        return dpaService.totalDpa();
    }

    public DPA dpaById(Long id) {
        return dpaService.dpaById(id);
    }

    public List<DPA> dpaByDescriptionContains(String description, int page, int size) {
        return dpaService.findByDescriptionContains(description, page, size);
    }

    public int totalDpaByDescriptionContains(String desciption) {
        return dpaService.totalByDescriptionContains(desciption);
    }

    // Mutations
    public DPA addDPA(String code, String description, boolean active, Long regionId, Long IdParent) {
        return dpaService.addDPA(code, description, active, regionId, IdParent);
    }

    public DPA editDPA(long id, String code, String description, boolean active, Long regionId, Long IdParent) {
        return dpaService.editDPA(id, code, description, active, regionId, IdParent);
    }
}
