package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import cu.uci.cegel.onei.sigipipc.persistence.Establishment;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyCharactSpecific;

import java.util.List;

public interface EstablishmentService {

    Establishment add(String organization, String name, String address, String contact, Integer phone, Long marketId, Long typologyId, Long dpaId, Long stateId);

    List<Establishment> findAll();

    List<Establishment> findAllPaging(int page, int size);

    int totalEstablishment();

    Establishment edit(Long id, String name, String address, String contact, Integer phone);

    Boolean delete(Long id);

    Establishment establishmentById(Long id);

    List<Establishment> filters(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, int page, int size);
    List<Establishment> pending(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, int page, int size);

    int totalEstablishmentsByFilter(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId);

    List<Establishment> pendientesCaptar(Long establishmentId);

    List<VarietyCharactSpecific>varietyCharactSpecificList(Long classifierId);

    boolean setActive(Long id, boolean active);
    boolean isCatched(Long idEstablishment);
}
