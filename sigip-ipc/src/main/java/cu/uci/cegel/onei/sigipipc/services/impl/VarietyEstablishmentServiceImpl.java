package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.model.*;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.repository.*;
import cu.uci.cegel.onei.sigipipc.services.EstablishmentService;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class VarietyEstablishmentServiceImpl implements VarietyEstablishmentService {


    @Autowired
    VarietyEstablishmentRepository varietyEstablishmentRepository;
    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;
    @Autowired
    ClassifierRepository classifierRepository;
    @Autowired
    CharacteristicRepository characteristicRepository;
    @Autowired
    SpecificationRepository specificationRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    CatchmentRepository catchmentRepository;
    @Autowired
    TypologyRepository typologyRepository;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    MarketCurrencyRepository marketCurrencyRepository;
    @Autowired
    DPARepository dpaRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    PlanningRepository planningRepository;
    @Autowired
    PendientesCaptarResourceAssambler pendientesCaptarResourceAssambler;

    @Autowired
    EstablishmentService establishmentService;

    @Override
    public List<VarietyEstablishment> add(Long establishmentId, Long stateId, List<CharactSpecificVariety> varietyCharactSpecificsId, List<Long> planningListId , Long unidadMedidaId, Long cantidadId ) {

        Establishment establishment = establishmentRepository.findById(establishmentId).get();
        State state = stateRepository.findById(stateId).get();
        Planning planning = planningRepository.findById(planningListId.get(0)).get();
        Classifier variedad = classifierRepository.findById(varietyCharactSpecificsId.get(0).getVarietyId()).get();
        Specification unidadMedida = specificationRepository.findById(unidadMedidaId).get();
        Specification cantidad = specificationRepository.findById(cantidadId).get();
        List<VarietyEstablishment> varietyEstablishmentList= new ArrayList();

        List<VarietyCharactSpecific> varietyCharactSpecifics = new ArrayList();

        varietyCharactSpecificsId.stream()
                .map(id -> varietyCharactSpecifics.add(varietyCharactSpecificRepository.findByClassifierAndSpecificationAndSpecification_Characteristic(
                        classifierRepository.findById(id.getVarietyId()).get(),
                        specificationRepository.findById(id.getSpecificationId()).get(),
                        characteristicRepository.findById(id.getCharacteristicId()).get()
                        )
                ))
                .collect(Collectors.toList());

        if (!establishment.isActive()) {
            establishment.setActive(true);
            establishmentRepository.save(establishment);
        }

        if(planningListId.get(1) != 0){

            Planning planning2 = planningRepository.findById(planningListId.get(1)).get();
            VarietyEstablishment varietyEstablishment = SaveLocal(establishment, state, varietyCharactSpecifics, planning, variedad,  unidadMedida, cantidad);
            VarietyEstablishment varietyEstablishment2 = SaveLocal(establishment, state, varietyCharactSpecifics, planning2, variedad,  unidadMedida, cantidad);

            varietyEstablishmentList.add(varietyEstablishment);
            varietyEstablishmentList.add(varietyEstablishment2);
            return  varietyEstablishmentList;

        }else {

            VarietyEstablishment varietyEstablishment = SaveLocal(establishment, state, varietyCharactSpecifics, planning, variedad,  unidadMedida, cantidad);
            varietyEstablishmentList.add(varietyEstablishment);
            return varietyEstablishmentList;
        }

    }

    @Override
    public List<VarietyEstablishment> edit(Long id, List<CharactSpecificVariety> charactSpecificVarietyList, List<Long> planningListId, Long unidadMedidaId, Long cantidadId) {

        List<VarietyEstablishment> varietyEstablishmentList= new ArrayList();
        VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(id).get();
        Planning planning = planningRepository.findById(planningListId.get(0)).get();
        Classifier variedad = classifierRepository.findById(charactSpecificVarietyList.get(0).getVarietyId()).get();
        Specification unidadMedida = specificationRepository.findById(unidadMedidaId).get();
        Specification cantidad = specificationRepository.findById(cantidadId).get();

        List<VarietyCharactSpecific> varietyCharactSpecifics = new ArrayList();

        charactSpecificVarietyList.stream()
                .map(it -> varietyCharactSpecifics.add(varietyCharactSpecificRepository.findByClassifierAndSpecificationAndSpecification_Characteristic(
                        classifierRepository.findById(it.getVarietyId()).get(),
                        specificationRepository.findById(it.getSpecificationId()).get(),
                        characteristicRepository.findById(it.getCharacteristicId()).get()
                        )
                ))
                .collect(Collectors.toList());

        if(planningListId.get(1) != 0){

            Planning planning2 = planningRepository.findById(planningListId.get(1)).get();
            List<VarietyEstablishment> varEstabAgrupadas = varietyEstablishmentRepository.agruparVarEstabByPlanning(varietyEstablishment.getEstablishment().getId(), varietyEstablishment.getState().getId(), varietyEstablishment.isActive(), varietyEstablishment.getClassifier().getId(), varietyEstablishment.getUnidadMedida().getId(), varietyEstablishment.getCantidad().getId());
            VarietyEstablishment varietyEstablishment2 = varEstabAgrupadas.get(1);

            varietyEstablishment.setVarietyCharactSpecifics(varietyCharactSpecifics);
            varietyEstablishment.setPlanning(planning);
            varietyEstablishment.setClassifier(variedad);
            varietyEstablishment.setUnidadMedida(unidadMedida);
            varietyEstablishment.setCantidad(cantidad);

            varietyEstablishment2.setVarietyCharactSpecifics(varietyCharactSpecifics);
            varietyEstablishment2.setPlanning(planning2);
            varietyEstablishment2.setClassifier(variedad);
            varietyEstablishment2.setUnidadMedida(unidadMedida);
            varietyEstablishment2.setCantidad(cantidad);

            varietyEstablishmentRepository.save(varietyEstablishment);
            varietyEstablishmentRepository.save(varietyEstablishment2);

            varietyEstablishmentList.add(varietyEstablishment);
            varietyEstablishmentList.add(varietyEstablishment2);

            return varietyEstablishmentList;


        }else{

            varietyEstablishment.setVarietyCharactSpecifics(varietyCharactSpecifics);
            varietyEstablishment.setPlanning(planning);
            varietyEstablishment.setClassifier(variedad);
            varietyEstablishment.setUnidadMedida(unidadMedida);
            varietyEstablishment.setCantidad(cantidad);

            varietyEstablishmentRepository.save(varietyEstablishment);

            varietyEstablishmentList.add(varietyEstablishment);

            return varietyEstablishmentList;

        }

    }

    private VarietyEstablishment SaveLocal(Establishment e, State s, List<VarietyCharactSpecific> vcs, Planning p, Classifier v, Specification um, Specification c){

        VarietyEstablishment varietyEstablishment = VarietyEstablishment.builder()
                .establishment(e)
                .state(s)
                .active(true)
                .varietyCharactSpecifics(vcs)
                .planning(p)
                .classifier(v)
                .unidadMedida(um)
                .cantidad(c).build();

        return varietyEstablishmentRepository.save(varietyEstablishment);
    }

    @Transactional
    public List<PendietesCaptarResource> pruebaPendientesCaptar(Long establishmentId, Long dpaId, Long marketId, Long tipologyId, int pageIndex, int size) {

        Pageable pageable = PageRequest.of(pageIndex, size);
        BooleanExpression predicate = generatePredPendientesCaptar2(establishmentId, dpaId, marketId, tipologyId);
        Page<VarietyEstablishment> page = varietyEstablishmentRepository.findAll(predicate, pageable);
        return pendientesCaptarResourceAssambler.toResource(page.getContent(), page.getTotalElements(), establishmentId);
    }

    public BooleanExpression generatePredPendientesCaptar2(Long establishmentId, Long dpaId, Long marketId, Long tipologyId) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        QVarietyEstablishment qe = QVarietyEstablishment.varietyEstablishment;
        booleanExpressions.add(qe.state.id.eq(EstadoCaptacion.PENDIENTE.getId()));
        booleanExpressions.add(qe.active.isTrue());
        if (establishmentId != -1) {
            booleanExpressions.add(qe.establishment.id.eq(establishmentId));
        }
        if (dpaId != -1)
            booleanExpressions.add(qe.establishment.dpa.id.eq(dpaId));
        if (marketId != -1)
            booleanExpressions.add(qe.establishment.market.id.eq(marketId));
        if (tipologyId != -1)
            booleanExpressions.add(qe.establishment.typology.id.eq(tipologyId));

        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);

        return predicate.orElse(null);
    }

    @Override
    public List<PendietesCaptarResource> getVariedadesPendientesCaptar(Long establishmentId, Long dpaId, Long marketId, Long tipologyId, LocalDate start, LocalDate end) {

        List<Integer> ids = null;
        if (establishmentId != -1) {
            ids = varietyEstablishmentRepository.getVariedadesPenditesCaptar(establishmentId, start, end);
        } else {
            ids = varietyEstablishmentRepository.getVariedadesPenditesCaptarAll(start, end);
        }

        List<Long> a = ids.stream().map(integer -> ((Integer) integer).longValue()).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(0, 5);
        BooleanExpression predicate = generatePredPendientesCaptar(a, dpaId, marketId, tipologyId);

        Page<VarietyEstablishment> page = varietyEstablishmentRepository.findAll(predicate, pageable);

        return pendientesCaptarResourceAssambler.toResource(page.getContent(), page.getTotalElements(), establishmentId);
    }

    public BooleanExpression generatePredPendientesCaptar(List<Long> ids, Long dpaId, Long marketId, Long tipologyId) {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        QVarietyEstablishment qe = QVarietyEstablishment.varietyEstablishment;

        //booleanExpressions.add(qe.varietyCharactSpecifics.any().varietyEstablishments.any().establishment.id.eq(Long.valueOf(2987)));
        booleanExpressions.add(qe.establishment.id.in(ids));

        if (dpaId != -1)
            booleanExpressions.add(qe.establishment.dpa.id.eq(dpaId));
        if (marketId != -1)
            booleanExpressions.add(qe.establishment.market.id.eq(marketId));
        if (tipologyId != -1)
            booleanExpressions.add(qe.establishment.typology.id.eq(tipologyId));

        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }


    public Integer CantvariedadesCaptadas(LocalDate start, LocalDate end) {
        return varietyEstablishmentRepository.CantvariedadesCaptadas(start, end);
    }

    @Override
    public Boolean isAllCatched() {
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        QVarietyEstablishment qe = QVarietyEstablishment.varietyEstablishment;
        booleanExpressions.add(qe.active.eq(true));
        booleanExpressions.add(qe.state.id.eq(EstadoCaptacion.PENDIENTE.getId()).or(qe.state.id.eq(EstadoCaptacion.INICIAL.getId())));

        Optional<BooleanExpression> optional = booleanExpressions.stream().reduce(BooleanExpression::and);

        BooleanExpression predicate = optional.orElse(null);
        if (predicate != null) {
            return varietyEstablishmentRepository.count(predicate) == 0;
        } else {
            return true;
        }

    }

    @Override
    public Long cantPendienteByEstab(Long estabId) {
        BooleanExpression predicate = generatePredPendientesCaptar2(estabId, -1L, -1L, -1L);
        return varietyEstablishmentRepository.count(predicate);
    }


    @Transactional
    @Override
    public VarietyEstablishment findByid(Long id) {
        VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(id).get();
        varietyEstablishment.getVarietyCharactSpecifics().size();
        return varietyEstablishment;
    }

    @Transactional
    @Override
    public VariedadEstablecimientoResource varietyEstablishmentResourceByid(Long id){

        VarietyEstablishment el = varietyEstablishmentRepository.findById(id).get();
        el.getVarietyCharactSpecifics().size();

        if (el.getEstablishment().getMarket().getMarket().getDescription().equalsIgnoreCase("Agropecuario")) {

            /*Agrupa los Var-Estab con todo igual excepto el Planning para hacerlo un mismo objeto a Listar*/
            List<VarietyEstablishment> varEstabAgrupadas = varietyEstablishmentRepository.agruparVarEstabByPlanning(el.getEstablishment().getId(), el.getState().getId(), el.isActive(), el.getClassifier().getId(), el.getUnidadMedida().getId(), el.getCantidad().getId());

            List<Long> idsVarEstab = new ArrayList<>();
            List<String> planningDay = new ArrayList<>();
            List<Long> planningId = new ArrayList<>();


            varEstabAgrupadas.stream().map(it -> {
                idsVarEstab.add(it.getId());
                planningDay.add(it.getPlanning().getDay());
                planningId.add(it.getPlanning().getId());
                return it;
            }).collect(Collectors.toList());

            while (planningDay.size() < 4) {
                planningDay.add("");
                planningId.add((long) 0);
            }

            return VariedadEstablecimientoResourceAssambler.toResourceById(el, idsVarEstab, planningDay, planningId);


        } else {

            List<Long> idsVarEstab = new ArrayList<>();
            List<String> planningDay = new ArrayList<>();
            List<Long> planningId = new ArrayList<>();

            idsVarEstab.add(el.getId());
            planningDay.add(el.getPlanning().getDay());
            planningId.add(el.getPlanning().getId());
            while (planningDay.size() < 4) {
                planningDay.add("");
                planningId.add((long) 0);
            }

            return VariedadEstablecimientoResourceAssambler.toResourceById(el, idsVarEstab, planningDay, planningId);

        }

    }

    @Override
    public VarietyEstablishment getById(Long id) {
        return varietyEstablishmentRepository.findById(id).get();
    }

    public List<VarietyEstablishment> findAll() {
        return (List<VarietyEstablishment>) varietyEstablishmentRepository.findAll();
    }

    @Transactional
    @Override
    public List<VarietyEstablishment> findAllPaging(int size, int paging) {
        List<Integer> idList = varietyEstablishmentRepository.varietyEstabPaging(size, paging);

        List<VarietyEstablishment> varietyEstablishmentListPaging = idList.stream()
                .map(el -> {
                    VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(el.longValue()).get();
//                    varietyEstablishment.getVarietyCharactSpecifics().size();

                    return varietyEstablishment;
                })
                .collect(Collectors.toList());

        return varietyEstablishmentListPaging;
    }

    @Override
    public int totalVarietyEstablishment() {
        return (int) varietyEstablishmentRepository.countByActiveIsTrue();
    }

    @Transactional
    @Override
    public List<VarietyEstablishment> filters(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId, int paging, int size) {

        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, establishment, classifier, stateId);

        if (size != -1) {

            Pageable pageable = PageRequest.of(paging, size);
            Page<VarietyEstablishment> page = varietyEstablishmentRepository.findAll(predicate, pageable);
            List<VarietyEstablishment> varietyEstablishments = page.getContent();

            return varietyEstablishments;

        } else {
            List<VarietyEstablishment> varietyEstablishments = (List<VarietyEstablishment>) varietyEstablishmentRepository.findAll(predicate);
            varietyEstablishments.stream().map(el -> {
//                el.getVarietyCharactSpecifics().size();

                return el;
            });
            return varietyEstablishments;
        }
    }

    @Transactional
    @Override
    public List<VariedadEstablecimientoResource> filtersResource(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId, int paging, int size) {

        List<VariedadEstablecimientoResource> varEstabResources = new ArrayList<>();
        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, establishment, classifier, stateId);

        if (size != -1) {

            varEstabResources = varEstabResourcePaging(predicate, paging, size);

            return varEstabResources;

        } else {
            List<VarietyEstablishment> varietyEstablishments = (List<VarietyEstablishment>) varietyEstablishmentRepository.findAll(predicate);

            varEstabResources = varietyEstablishments.stream().map(el -> {

                List<Long> idsVarEstab = new ArrayList<>();
                List<String> dayPlannings = new ArrayList<>();

                idsVarEstab.add(el.getId());
                dayPlannings.add(el.getPlanning().getDay());
                while (dayPlannings.size() < 4) {
                    dayPlannings.add("");
                }

                return VariedadEstablecimientoResourceAssambler.toResource(el, idsVarEstab, dayPlannings);

            }).collect(Collectors.toList());

            return varEstabResources;
        }
    }

    public List<VariedadEstablecimientoResource> varEstabResourcePaging(BooleanExpression predicate, int paging, int size ){

        List<Long> idsComparables = new ArrayList<>();
        List<VariedadEstablecimientoResource> varEstabResources = new ArrayList<>();

        Pageable pageable = PageRequest.of(paging, size);
        Page<VarietyEstablishment> page = varietyEstablishmentRepository.findAll(predicate, pageable);
        List<VarietyEstablishment> varietyEstablishmentsPage = page.getContent();

        Pageable pageable1 = PageRequest.of(paging + 1, size);
        Page<VarietyEstablishment> page1 = varietyEstablishmentRepository.findAll(predicate, pageable1);
        List<VarietyEstablishment> varietyEstablishmentsPage1 = page1.getContent();

        List<VarietyEstablishment> varietyEstablishments = fusionar(varietyEstablishmentsPage, varietyEstablishmentsPage1);

        final int[] valorSize = {0};

        varEstabResources = varietyEstablishments.stream().map(el -> {

            /*Se verifica si está agrupado con anterioridad y en caso positivo ya no se entra a agruparlo*/
            boolean noEstaAgrupado = noEstaAgrupado(idsComparables, el.getId());

            if(noEstaAgrupado && valorSize[0] < size ) {

                if (el.getEstablishment().getMarket().getMarket().getDescription().equalsIgnoreCase("Agropecuario")) {

                    /*Agrupa los Var-Estab con todo igual excepto el Planning para hacerlo un mismo objeto a Listar*/
                    List<VarietyEstablishment> varEstabAgrupadas = varietyEstablishmentRepository.agruparVarEstabByPlanning(el.getEstablishment().getId(), el.getState().getId(), el.isActive(), el.getClassifier().getId(), el.getUnidadMedida().getId(), el.getCantidad().getId());

                    List<Long> idsVarEstab = new ArrayList<>();
                    List<String> dayPlannings = new ArrayList<>();
                    valorSize[0]++;

                    varEstabAgrupadas.stream().map(it -> {
                        idsVarEstab.add(it.getId());
                        dayPlannings.add(it.getPlanning().getDay());
                        idsComparables.add(it.getId());
                        return it;
                    }).collect(Collectors.toList());

                    while (dayPlannings.size() < 4) {
                        dayPlannings.add("");
                    }

                    return VariedadEstablecimientoResourceAssambler.toResource(el, idsVarEstab, dayPlannings);


                } else {

                    List<Long> idsVarEstab = new ArrayList<>();
                    List<String> dayPlannings = new ArrayList<>();
                    valorSize[0] ++;

                    idsVarEstab.add(el.getId());
                    dayPlannings.add(el.getPlanning().getDay());
                    while (dayPlannings.size() < 4) {
                        dayPlannings.add("");
                    }

                    return VariedadEstablecimientoResourceAssambler.toResource(el, idsVarEstab, dayPlannings);

                }
            }else{
                return null;
            }

        }).collect(Collectors.toList());

        return varEstabResources.stream().filter(value -> value != null).collect(Collectors.toList());

    }

    private boolean noEstaAgrupado(List<Long> idsComparables, Long idVarEstab){
        return idsComparables.stream().filter(value -> value.equals(idVarEstab)).count() == 0;
    }

    private List<VarietyEstablishment> fusionar(List<VarietyEstablishment> varietyEstablishmentList1, List<VarietyEstablishment> varietyEstablishmentList2){
        List<VarietyEstablishment> listaFusionada = new ArrayList<>();

        varietyEstablishmentList1.stream()
                .map(id -> listaFusionada.add(id
                ))
                .collect(Collectors.toList());

        varietyEstablishmentList2.stream()
                .map(id -> listaFusionada.add(id
                ))
                .collect(Collectors.toList());

        return listaFusionada;
    }

    @Override
    public int totalByFilter(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId,int paging, int size) {
        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, establishment, classifier, stateId);
        Long count = varietyEstablishmentRepository.count(predicate);

       // if(count.intValue() < 25){
            List<Long> idsComparables = new ArrayList<>();
            final int[] valorSize = {0};
            final int[] countFinal = {0};
            int countVarEstab = 0;
            final int[] agrupados = {0};

                Pageable pageable = PageRequest.of(paging, size );
                Page<VarietyEstablishment> page = varietyEstablishmentRepository.findAll(predicate, pageable);
                List<VarietyEstablishment> varietyEstablishmentsPage = page.getContent();

                Pageable pageable1 = PageRequest.of(paging + 1, size );
                Page<VarietyEstablishment> page1 = varietyEstablishmentRepository.findAll(predicate, pageable1);
                List<VarietyEstablishment> varietyEstablishmentsPage1 = page1.getContent();

                List<VarietyEstablishment> varietyEstablishments = fusionar(varietyEstablishmentsPage, varietyEstablishmentsPage1);

                   countVarEstab = (int) varietyEstablishments.stream().map(el -> {

                    /*Se verifica qsi está agrupado con anterioridad y en caso positivo ya no se entra a agruparlo*/
                    boolean noEstaAgrupado = noEstaAgrupado(idsComparables, el.getId());

                    if(noEstaAgrupado && valorSize[0] != size ) {

                        if (el.getEstablishment().getMarket().getMarket().getDescription().equalsIgnoreCase("Agropecuario")) {

                            /*Agrupa los Var-Estab con todo igual excepto el Planning para hacerlo un mismo objeto a Listar*/
                            List<VarietyEstablishment> varEstabAgrupadas = varietyEstablishmentRepository.agruparVarEstabByPlanning(el.getEstablishment().getId(), el.getState().getId(), el.isActive(), el.getClassifier().getId(), el.getUnidadMedida().getId(), el.getCantidad().getId());

                            varEstabAgrupadas.stream().map(it -> {
                                idsComparables.add(it.getId());
                                return it;
                            }).collect(Collectors.toList());

                            valorSize[0]++;
                           return countFinal[0]++;

                        } else {

                            valorSize[0] ++;
                            return countFinal[0]++;

                        }
                    }else{
                        agrupados[0] ++;
                      return countFinal[0];
                    }

                }).count();

       if(countFinal[0] < size && paging == 0  ){
            return countFinal[0];
       }else {
            return count.intValue();
        }

    }

    @Override
    public Boolean delete(Long id) {
        VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(id).get();
        varietyEstablishmentRepository.delete(varietyEstablishment);

        //setActive establishment
        Long idEstablishment = varietyEstablishment.getEstablishment().getId();
        Establishment establishment = establishmentRepository.findById(idEstablishment).get();
        Collection<VarietyEstablishment> varietyEstablishments = establishment.getVarietyEstablishments();
        if (varietyEstablishments.stream().filter(value -> value.isActive()).count() == 0) {
            establishment.setActive(false);
            establishmentRepository.save(establishment);
        }
        return true;
    }

    @Override
    public VarietyEstablishment setActive(Long id) {
        //setActive VarietyEstablishment
        VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(id).get();
        Boolean active = varietyEstablishment.isActive();
        varietyEstablishment.setActive(!active);
        varietyEstablishment = varietyEstablishmentRepository.save(varietyEstablishment);

        //setActive establishment
        Establishment establishment = varietyEstablishment.getEstablishment();
        Collection<VarietyEstablishment> varietyEstablishments = establishment.getVarietyEstablishments();
        if (varietyEstablishments.stream().filter(value -> value.isActive()).count() == 0) {
            establishment.setActive(false);
            establishmentRepository.save(establishment);
        } else {
            if (!establishment.isActive()) {
                establishment.setActive(true);
                establishmentRepository.save(establishment);
            }
        }

        return varietyEstablishment;
    }

    @Override
    public VarietyEstablishment setEstadoVarietyEstablishment(Long varietyStabId, Long stateId) {
        VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(varietyStabId).get();
        List<VarietyEstablishment> varEstabAgrupadas = varietyEstablishmentRepository.agruparVarEstabByPlanning(varietyEstablishment.getEstablishment().getId(), varietyEstablishment.getState().getId(), varietyEstablishment.isActive(), varietyEstablishment.getClassifier().getId(), varietyEstablishment.getUnidadMedida().getId(), varietyEstablishment.getCantidad().getId());
        List<State> states = (List<State>) stateRepository.findAll();
        State stateBaja = stateRepository.findByDescripcionEquals("Baja");
        State stateAlta = stateRepository.findByDescripcionEquals("Alta");
        if (states.get(0).getId() != stateId) {
            varEstabAgrupadas.stream().map( el -> {
                el.setState(states.get(0));
                varietyEstablishmentRepository.save(el);
                return el;
            }).collect(Collectors.toList());

            /*varietyEstablishment.setState(states.get(0));
            varietyEstablishmentRepository.save(varietyEstablishment);*/

        } else {
            varEstabAgrupadas.stream().map( el -> {
                el.setState(states.get(1));
                varietyEstablishmentRepository.save(el);
                return el;
            }).collect(Collectors.toList());

            /*varietyEstablishment.setState(states.get(1));
            varietyEstablishmentRepository.save(varietyEstablishment);*/
        }
        Establishment establishment = varietyEstablishment.getEstablishment();

        //No carga la lista de Variedad-Establecimientos, Revisar!!!
        //establishment.getVarietyEstablishments().size();

        Collection<VarietyEstablishment> varietyEstablishments = varietyEstablishmentRepository.findAllByEstablishment(establishment);
        long count = 0L;
        for (VarietyEstablishment it : varietyEstablishments) {
            if (it.getState().getDescripcion().equalsIgnoreCase("Alta")) {
                count++;
            }
        }
        if (count == 0) {
            establishment.setState(stateBaja);
            establishmentRepository.save(establishment);
        } else {
            establishment.setState(stateAlta);
            establishmentRepository.save(establishment);
        }
        return varietyEstablishment;
    }

    private BooleanExpression createPredicate(Long marketId, Long typologyId, Long dpaId, String establishment, String classifier, Long stateId) {
        QVarietyEstablishment qVarietyEstablishment = QVarietyEstablishment.varietyEstablishment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qVarietyEstablishment.active.isTrue());
        if (typologyId != -1) {
            Typology typology = typologyRepository.findById(typologyId).get();
            booleanExpressions.add(qVarietyEstablishment.establishment.typology.eq(typology));
        }
        if (marketId != -1) {
            booleanExpressions.add(qVarietyEstablishment.establishment.market.eq(marketCurrencyRepository.findById(marketId).get()));
        }
        if (dpaId != -1) {
            DPA dpa = dpaRepository.findById(dpaId).get();
            booleanExpressions.add(qVarietyEstablishment.establishment.dpa.eq(dpa));
        }
        if (!establishment.equals("")) {
            booleanExpressions.add(qVarietyEstablishment.establishment.name.toLowerCase().contains(establishment.toLowerCase()));
        }
        if (!classifier.equals("")) {
            booleanExpressions.add(qVarietyEstablishment.varietyCharactSpecifics.any().classifier.description.toLowerCase().contains(classifier.toLowerCase()));
        }
        if (stateId != -1) {
            State state = stateRepository.findById(stateId).get();
            booleanExpressions.add(qVarietyEstablishment.state.eq(state));
        }
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        //return predicate.get();
        return predicate.orElse(null);
    }

    public List<VarietyEstablishment> getGetAllVariedadEstabByIdEstab(Long id) {
        return varietyEstablishmentRepository.findAllByEstablishmentIdAndActiveTrue(id);
    }

//    private BooleanExpression predicateToGetAllVariedadEstabByIdEstab(Long id) {
//        QVarietyEstablishment qe = QVarietyEstablishment.varietyEstablishment;
//        List<BooleanExpression> booleanExpressions = new ArrayList<>();
//        booleanExpressions.add(qe.establishment.id.eq(id));
//        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
//        return predicate.orElse(null);
//    }

    public boolean desactivarVariedadesEstabByEstabId(Long id) {
        List<VarietyEstablishment> list = getGetAllVariedadEstabByIdEstab(id);
        list.forEach(varietyEstablishment -> {
            varietyEstablishment.setActive(false);
        });
        varietyEstablishmentRepository.saveAll(list);
        establishmentService.setActive(id, false);
        return true;

    }
}
