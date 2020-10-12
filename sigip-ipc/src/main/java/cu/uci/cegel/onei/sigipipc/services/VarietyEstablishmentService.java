package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResource;
import cu.uci.cegel.onei.sigipipc.model.CharactSpecificVariety;
import cu.uci.cegel.onei.sigipipc.model.PendietesCaptarResource;
import cu.uci.cegel.onei.sigipipc.model.VariedadEstablecimientoResource;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyEstablishment;


import java.time.LocalDate;
import java.util.List;
public interface VarietyEstablishmentService {

    List<VarietyEstablishment> add(Long establishmentId, Long stateId, List<CharactSpecificVariety> varietyCharactSpecificsId, List<Long> planningListId, Long unidadMedidaId, Long cantidadId);

    List<VarietyEstablishment> edit(Long id, List<CharactSpecificVariety> charactSpecificVarietyList, List<Long> planningListId, Long unidadMedidaId, Long cantidadId);

    public List<PendietesCaptarResource> pruebaPendientesCaptar(Long establishmentId, Long dpaId, Long marketId, Long tipologyId, int pageIndex, int size);

//    VarietyEstablishment edit(Long id, Long measurementUnitId, List<CharactSpecificVariety> charactSpecificVarietyList);
    List<PendietesCaptarResource> getVariedadesPendientesCaptar(Long establishmentId, Long dpaId, Long marketId, Long tipologyId,LocalDate start, LocalDate end);

    VarietyEstablishment findByid(Long id);

    VariedadEstablecimientoResource varietyEstablishmentResourceByid(Long id);

    VarietyEstablishment getById(Long id);

    List<VarietyEstablishment> findAllPaging(int size, int page);

    int totalVarietyEstablishment();

    List<VarietyEstablishment> filters(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId, int page, int size);

    List<VariedadEstablecimientoResource> filtersResource(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId, int page, int size);

    int totalByFilter(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId, int page, int size);

    public List<VarietyEstablishment> getGetAllVariedadEstabByIdEstab(Long id);
    public boolean desactivarVariedadesEstabByEstabId(Long id);
    Boolean delete(Long id);

    VarietyEstablishment setActive(Long id);

    VarietyEstablishment setEstadoVarietyEstablishment(Long VarietyStabId, Long stateId);

    Integer CantvariedadesCaptadas(LocalDate start, LocalDate end);

    Boolean isAllCatched();

    Long cantPendienteByEstab(Long estabId);


}
