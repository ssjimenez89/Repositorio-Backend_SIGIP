package cu.uci.cegel.onei.sigipipc.resolvers.captacion;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.model.*;
import cu.uci.cegel.onei.sigipipc.persistence.Catchment;

import cu.uci.cegel.onei.sigipipc.persistence.Specification;
import cu.uci.cegel.onei.sigipipc.services.CatchmentService;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Resolver(name = "CaptacionResolver")
public class CaptacionResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private CatchmentService catchmentService;
    @Autowired
    private VarietyEstablishmentService varietyEstablishmentService;


    // Queries
    public List<Catchment> catchments() {
        return catchmentService.findAll();
    }

    public List<Catchment> catchmentPaging(int page, int size) {
        return catchmentService.findAllPaging(page, size);
    }

    public Catchment catchmentById(Long id) {
        return catchmentService.catchmentById(id);
    }

    public int totalCatchment() {
        return catchmentService.totalCatchment();
    }

    public List<Catchment> catchmentByUsersContains(String users, int page, int size) {
        return catchmentService.findByUsersContains(users, page, size);
    }

    //captacionesRealizadas
    public List<CaptacionResource> catchmentsByFilter(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, LocalDate fechaDigitada, String year, String mes, String variedadName, int paging, int size) {
        return catchmentService.findAllByFilter(marketId, typologyId, establishment, dpaId, varietyId, fechaDigitada, year, mes, variedadName, paging, size);
    }

    public List<CaptacionResource> catchmentsFueraDeRango(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, int paging, int size) {
        return catchmentService.fueraDeRango(marketId, typologyId, establishment, dpaId, varietyId, paging, size);
    }

    public List<Integer> allYearsCatchment() {
        return catchmentService.allYearsCatchment();
    }

    //pendientes captar
    public List<PendietesCaptarResource> captacionesPendientes(Long establishmentId, LocalDate start, LocalDate end, Integer page, Integer size) {

        return varietyEstablishmentService.pruebaPendientesCaptar(establishmentId, (long) -1, (long) -1, (long) -1, page, size);
    }

    //pendientes captar todos
    public List<PendietesCaptarResource> captacionesPendientesAll(Long establishmentId, Long dpaId, Long marketId, Long tipologyId, LocalDate start, LocalDate end, Integer page, Integer size) {

        return varietyEstablishmentService.pruebaPendientesCaptar(establishmentId, dpaId, marketId, tipologyId, page, size);
    }

    public int cantFaltaOcacional(Long idVariedad, Long idEstableciemiento, LocalDate startDate, LocalDate endDate) {
        LocalDate finTemp = LocalDate.now();
        LocalDate fin = LocalDate.of(finTemp.getYear(), finTemp.getMonth(), 1);
        LocalDate inicio = fin.minusMonths(2);

        Date start = java.sql.Date.valueOf(inicio);
        Date end = java.sql.Date.valueOf(fin);
        return catchmentService.cantFaltaOcacional(idVariedad, idEstableciemiento, start, end);
    }

    public int totalCatchmentByUsersContains(String users) {
        return catchmentService.totalByUsersContains(users);
    }


    public Float cambioUM(Float precioObservado, Float cantidadObservada, Long specificationIdUMObservada, Float cantidadActual, Long specificationIdUMActual, Long varEstabId) {
        return catchmentService.cambioUM(precioObservado, cantidadObservada, specificationIdUMObservada, cantidadActual, specificationIdUMActual, varEstabId);
    }

    public List<Specification> listUMsByTipoUM(Long tipoUM) {
        return catchmentService.listUMsByTipoUM(tipoUM);
    }



    // Mutations
    public Boolean addCatchment(List<CatchmentInput> catchmentInputs) {


        catchmentService.add(catchmentInputs);

        return true;
    }

    public CaptacionResource editCatchment(CatchmentInput catchmentInput) {
        return catchmentService.edit(catchmentInput);
    }

    public Long deleteCatchment(Long id) {
        return catchmentService.delete(id);
    }


    public Boolean imputeCatchment(List<CatchmentImpute> catchmetns) {
        return catchmentService.imputeCatchment(catchmetns);
    }

    public CatchmentViewResourcePage catchmentImputarList(PageResource page) {
        return catchmentService.catchmentByImpute(page);
    }

    public CatchmentViewResourcePage catchmentOutRange(PageResource page) {
        return catchmentService.catchmentOutRange(page);
    }

    public Boolean agreeCatchmentOutRange(List<Long> id){
        return catchmentService.agreeCatchmentOutRange(id);
    }

    public CatchmentViewResourcePage calculoRelativoVar_Est(PageResource page){
        return catchmentService.calcularRelativoVar_Est(page);
    }

    public Boolean calculoArticuloNacion(Boolean mun){
        return catchmentService.calculoArticuloNacion(mun);
    }

    public IndiceArticuloViewResourcePage calculoArticuloNacionMes(PageResource page){
        return catchmentService.indiceArticuloDelMes(page);
    }

    public Boolean microIndiceMunicipio(){
        return catchmentService.calcularMicroIndiceMun();
    }
}
