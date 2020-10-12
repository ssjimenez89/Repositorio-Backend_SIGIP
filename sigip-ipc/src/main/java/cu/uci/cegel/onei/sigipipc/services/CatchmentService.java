package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.model.*;
import cu.uci.cegel.onei.sigipipc.persistence.Catchment;
import cu.uci.cegel.onei.sigipipc.persistence.Specification;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CatchmentService {
    List<Catchment> findAll();
    Catchment findById(Long id);
    List<CaptacionResource> findAllByFilter(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, LocalDate fechaDigitada, String year, String mes, String variedadName, int paging, int size);
    List<CaptacionResource> fueraDeRango(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, int paging, int size);

    List<Catchment> findAllPaging(int page, int size);

    Catchment catchmentById(Long id);

    int totalCatchment();

//    Catchment add(CatchmentInput capt);

    boolean  add(List<CatchmentInput> capts);
    Long delete(Long idCaptacion);

    CaptacionResource edit(CatchmentInput capt);

    List<Catchment> findByUsersContains(String users, int page, int size);

    int totalByUsersContains(String users);

    List<Integer> allYearsCatchment();

    CatchmentViewResourcePage catchmentByImpute(PageResource pageResource);

    boolean imputeCatchment(List<CatchmentImpute> cathments);

    int cantFaltaOcacional(Long idVariedad, Long  idEstableciemiento, Date startDate, Date endDate);

    int cantCaptadasByIdEstab(Long idEstablishment,LocalDate start, LocalDate end);
    List<Catchment> CaptadasByIdEstab(Long idEstablishment,LocalDate start, LocalDate end);

    CatchmentViewResourcePage catchmentOutRange(PageResource pageResource);

    Float cambioUM(Float precioObservado, Float cantidadObservada, Long specificationIdUMObservada, Float cantidadActual, Long specificationIdUMActual, Long varEstabId);

    List<Specification> listUMsByTipoUM(Long tipoUM);

    Boolean agreeCatchmentOutRange(List<Long> id);

    CatchmentViewResourcePage calcularRelativoVar_Est(PageResource pageResource);

    Boolean calculoArticuloNacion(boolean mun);

    IndiceArticuloViewResourcePage indiceArticuloDelMes(PageResource pageResource);

    Boolean calcularMicroIndiceMun();
}
