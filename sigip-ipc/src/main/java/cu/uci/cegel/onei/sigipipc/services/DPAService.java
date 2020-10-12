package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.DPAResource;
import cu.uci.cegel.onei.sigipipc.persistence.DPA;

import java.util.List;

public interface DPAService {
    List<DPA> getAllActive();

    List<DPA> findAll();

    List<DPAResource> findAllDPAResource();

    List<DPA> findAllActives();

    List<DPA> dpasPaging(int page, int size);

    List<DPA> allChildrensByIdParent(long idParent);

    int totalDpa();

    DPA dpaById(Long id);

    List<DPA> findByDescriptionContains(String description, int page, int size);

    int totalByDescriptionContains(String description);

    DPA addDPA(String code, String description, boolean active, Long regionId, Long IdParent);

    DPA editDPA(long id, String code, String description, boolean active, Long regionId, Long IdParent);
}
