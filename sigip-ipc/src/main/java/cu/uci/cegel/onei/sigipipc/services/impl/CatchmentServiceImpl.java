package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.kernel.util.UtilFecha;
import cu.uci.cegel.onei.sigipipc.model.*;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.persistence.Currency;
import cu.uci.cegel.onei.sigipipc.persistence.TipoObservacion;
import cu.uci.cegel.onei.sigipipc.repository.*;
import cu.uci.cegel.onei.sigipipc.services.CaptacionPredicado.CaptacionPredicado;
import cu.uci.cegel.onei.sigipipc.services.CatchmentService;
import cu.uci.cegel.onei.sigipipc.services.StateService;
import cu.uci.cegel.onei.sigipipc.util.UtilHelper;
import cu.uci.cegel.onei.sigipipc.model.CaptacionResourceAssambler;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatchmentServiceImpl implements CatchmentService {
    @Autowired
    CatchmentRepository catchmentRepository;
    @Autowired
    ObservationRepository observationRepository;
    @Autowired
    IncidenceRepository incidenceRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    TypologyRepository typologyRepository;

    @Autowired
    MarketRepository marketRepository;

    @Autowired
    MarketCurrencyRepository marketCurrencyRepository;

    @Autowired
    EstablishmentRepository establishmentRepository;

    @Autowired
    DPARepository dpaRepository;

    @Autowired
    CaptacionResourceAssambler captacionResourceAssambler;
    @Autowired
    VarietyEstablishmentRepository varietyEstablishmentRepository;

    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;
    @Autowired
    VarietyEstablishmentService varietyEstablishmentService;

    @Autowired
    CaracteristicaEspecificacionResourceAssambler caracteristicaEspecificacionResourceAssambler;

    @Autowired
    StateService stateService;
    @Autowired
    UtilHelper utilHelper;

    @Autowired
    SpecificationRepository specificationRepository;

    @Autowired
    CorrelacionadorRepository correlacionadorRepository;
    @Autowired
    CaptacionPredicado captacionPredicado;

    List<Catchment> captacionesDelMes;

    List<Catchment> captacionesConRelativo;

    boolean municipio;

    @Autowired
    IndiceArticuloRepository indiceArticuloRepository;

    @Autowired
    IndiceArticuloResourceAssambler indiceArticuloResourceAssambler;

    @Override
    public List<Catchment> findAll() {
        return (List<Catchment>) catchmentRepository.findAll();
    }

    @Override
    public Catchment findById(Long id) {
        return catchmentRepository.findById(id).get();
    }

    @Transactional
    @Override
    public List<CaptacionResource> findAllByFilter(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, LocalDate fechaDigitada, String year, String mes, String variedadName, int paging, int size) {
        BooleanExpression predicate = captacionPredicado.generatePredicate(marketId, typologyId, establishment, dpaId, varietyId, fechaDigitada, year, mes, variedadName);
        if (size != -1) {
            return getCaptacionesByPredicateAndPage(predicate, paging, size, null);
        } else {

            List<Catchment> captaciones = (List<Catchment>) catchmentRepository.findAll(predicate);
            return captacionResourceAssambler.toResource(captaciones, ((Integer) captaciones.size()).longValue());
        }
    }

    @Override
    public List<CaptacionResource> fueraDeRango(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, int paging, int size) {
        BooleanExpression predicate = captacionPredicado.generatePredicate(marketId, typologyId, establishment, dpaId, varietyId, null, "", "", "");
        return getCaptacionesByPredicateAndPage(predicate, paging, size, null);
    }

    private List<CaptacionResource> getCaptacionesByPredicateAndPage(BooleanExpression predicate, int paging, int size, Sort orders) {
        Pageable pageable;
        if (orders != null)
            pageable = PageRequest.of(paging, size, orders);
        else
            pageable = PageRequest.of(paging, size);
        Page<Catchment> page = catchmentRepository.findAll(predicate, pageable);
        return captacionResourceAssambler.toResource(page.getContent(), page.getTotalElements());
    }

    private BooleanExpression generatePredicate(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, LocalDate fechaDigitada, String year, String mes, String variedadName) {
        QCatchment qe = QCatchment.catchment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
//        booleanExpressions.add(qe.imputed.eq(true));
        //booleanExpressions.add(qe.dvarEstabList.get(0).establishment.active.eq(true));
//        if (fechaDigitada != null) {
//
//        }
        if (!variedadName.equals("")) {
            booleanExpressions.add(qe.dVarCaracEspec.any().classifier.description.like(variedadName));
        }
        if (!mes.equals("")) {
            booleanExpressions.add(qe.date.month().eq(2));
        }
        if (!year.equals("")) {
            booleanExpressions.add(qe.date.year().eq(Integer.valueOf(year)));
        }
        if (typologyId != -1) {
            Typology typology = typologyRepository.findById(typologyId).get();
            booleanExpressions.add(qe.establishment.typology.eq(typology));
        }
        if (marketId != -1) {
            booleanExpressions.add(qe.establishment.market.eq(marketCurrencyRepository.findById(marketId).get()));
        }
        if (dpaId != -1) {
            DPA dpa = dpaRepository.findById(dpaId).get();
            //qe.dvarEstabList.isEmpty().or(qe.dvarEstabList.any().fechaFin.after(new Date()));
            booleanExpressions.add(qe.establishment.dpa.eq(dpa));
        }
        if (!establishment.equals("")) {
            booleanExpressions.add(qe.establishment.name.toLowerCase().contains(establishment.toLowerCase()));
        }
//        if(!code.equals("")){
//            //booleanExpressions.add(qe.code.contains(code));
//            booleanExpressions.add(qe.code.contains(code));
//        }
//        if(stateId!=-1){
//            State state=stateRepository.findById(stateId).get();
//            booleanExpressions.add(qe.state.eq(state));
//        }
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }


    @Override
    public List<Catchment> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<Catchment> page = catchmentRepository.findAll(pageable);
            List<Catchment> catchments = page.getContent();
            return catchments;
        } else {
            return findAll();
        }
    }

    @Override
    public Catchment catchmentById(Long id) {
        Catchment catchment = catchmentRepository.findById(id).get();
        return catchment;
    }

    public int totalCatchment() {
        List<Catchment> categories = (List<Catchment>) catchmentRepository.findAll();
        return categories.size();
    }

    public int cantFaltaOcacional(Long idVariedad, Long idEstableciemiento, Date startDate, Date endDate) {
        BooleanExpression predicate = captacionPredicado.predicateFaltaOcacional(idVariedad, idEstableciemiento, startDate, endDate);

        return ((List<Catchment>) catchmentRepository.findAll(predicate)).size();
    }

    @Override
    public int cantCaptadasByIdEstab(Long idEstablishment, LocalDate start, LocalDate end) {

        return catchmentRepository.countCatchmentByEstablishmentIdAndDateBetween(idEstablishment, java.sql.Date.valueOf(start), java.sql.Date.valueOf(end));
    }

    @Override
    public List<Catchment> CaptadasByIdEstab(Long idEstablishment, LocalDate start, LocalDate end) {
        return catchmentRepository.findAllByEstablishmentIdAndDateBetween(idEstablishment, java.sql.Date.valueOf(start), java.sql.Date.valueOf(end));
    }


    //este es el miooo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Transactional
    @Override
    public boolean add(List<CatchmentInput> capts) {
        if (capts.size() == 1 && capts.get(0).getIncidence() == 3) { //cierre definitivo
            return desactivarVariedades(capts.get(0));
        }

        List<Catchment> cList = new ArrayList<>();

        if (capts.size() == 1 && capts.get(0).getIncidence() == 2) { // cierre temporal
            CatchmentInput capt = capts.get(0);
            List<VarietyEstablishment> varList = varietyEstablishmentRepository.findAllByEstablishmentIdAndActiveTrue(capt.getEstablishment());
            varList.stream().forEach(varietyEstablishment -> {
                buildCaptaciones(cList, capt, varietyEstablishment);
            });
            return true;
        }
//estab visitado
        capts.stream().forEach(capt -> {
            VarietyEstablishment varietyEstablishment = varietyEstablishmentRepository.findById(capt.getDvarEstabId()).get();
            buildCaptaciones(cList, capt, varietyEstablishment);
        });

        return true;
    }


    private void test(Long varEstId, Long estabId) {
        QVarietyEstablishment q = QVarietyEstablishment.varietyEstablishment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(q.establishment.id.in(estabId));
        BooleanExpression pred = booleanExpressions.stream().reduce(BooleanExpression::and).orElse(null);
        List<VarietyEstablishment> list = (List<VarietyEstablishment>) varietyEstablishmentRepository.findAll(pred);
        booleanExpressions.add(q.id.in(varEstId));
        pred = booleanExpressions.stream().reduce(BooleanExpression::and).orElse(null);
        List<VarietyEstablishment> list2 = (List<VarietyEstablishment>) varietyEstablishmentRepository.findAll(pred);
        int a = 0;

    }

    private void buildCaptaciones(List<Catchment> cList, CatchmentInput capt, VarietyEstablishment varietyEstablishment) {
        varietyEstablishment.getVarietyCharactSpecifics().size();
        String id = new String(Base64.getDecoder().decode(capt.getUsers()));
        Long userId = Long.valueOf(id);
        Catchment c = Catchment.builder()
                .dVarCaracEspec(varietyEstablishment.getVarietyCharactSpecifics())
                .users(2l)
                .incidence(incidenceRepository.findById(capt.getIncidence()).get())
                .observation(observationRepository.findById(capt.getIncidence()).get())
                .price(capt.getPrice())
                .date(new Date())
                .imputed(false)
                .establishment(varietyEstablishment.getEstablishment())
                .relative(Float.parseFloat("2.5"))
                .currency(currencyRepository.findById(varietyEstablishment.getEstablishment().getMarket().getCurrency().getId()).get())
                .idVariedad(varietyEstablishment.getId())
                .classifier(varietyEstablishment.getClassifier())
                .cantidad(varietyEstablishment.getCantidad())
                .unidadMedida(varietyEstablishment.getUnidadMedida())
                .build();
        cList.add(c);
        //actualizar la VarEstab
        varietyEstablishment.setState(stateService.stateById(EstadoCaptacion.CAPTADO.getId()));
        varietyEstablishmentRepository.save(varietyEstablishment);
        Catchment k = catchmentRepository.save(c);
        System.out.println("se inserto un captacion con id " + k.getId());
    }

    private boolean desactivarVariedades(CatchmentInput c) {

        return this.varietyEstablishmentService.desactivarVariedadesEstabByEstabId(c.getId());
    }


    @Override
    public Long delete(Long idCaptacion) {
//        catchmentRepository.findById(idCaptacion).get()
        catchmentRepository.deleteById(idCaptacion);
        int a = 0;
        return idCaptacion;
    }

    ;

    @Override
    public CaptacionResource edit(CatchmentInput capt) {
        Catchment catchment = catchmentRepository.findById(capt.getId()).get();
        Incidence incidence = incidenceRepository.findById(capt.getIncidence()).get();
        Observation observation = observationRepository.findById(capt.getObservation()).get();
//        Currency currency=currencyRepository.findById(capt.getIdCurrency()).get();
//        catchment.setUsers(capt.getUsers());
        catchment.setPrice(capt.getPrice());
//        catchment.setRelative(capt.getRelative());
//        catchment.setImputed(capt.getImputed());
        catchment.setIncidence(incidence); //comentariar aki si hay que modificar todas las incidencias
        catchment.setObservation(observation);
//        catchment.setCurrency(currency);
        catchmentRepository.save(catchment);
        // updateAllIncidencesLikeCatchmentTo(catchment, incidence); // descoment si hay ke modificar todas las incidencias
        return captacionResourceAssambler.toResource(catchment);
    }

    private List<Catchment> getAllCathmentsLike(Catchment c) {
        Predicate p = captacionPredicado.predicateAllCatchmentsLike(c);
        return (List<Catchment>) catchmentRepository.findAll(p);
    }

    private void updateAllIncidencesLikeCatchmentTo(Catchment c, Incidence i) {
        List<Catchment> list = getAllCathmentsLike(c);
        list.forEach(catchment -> {
            catchment.setIncidence(i);
        });
        catchmentRepository.saveAll(list);
    }

    @Override
    public List<Catchment> findByUsersContains(String users, int paging, int size) {
        QCatchment qCategory = QCatchment.catchment;
        BooleanExpression predicate = qCategory.users.eq(Long.valueOf(users));

        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size, Sort.by("users"));
            Page<Catchment> page = catchmentRepository.findAll(predicate, pageable);
            List<Catchment> catchments = page.getContent();
            return catchments;
        } else {
            List<Catchment> catchments = (List<Catchment>) catchmentRepository.findAll(predicate, Sort.by("users"));
            return catchments;
        }
    }

    @Override
    public int totalByUsersContains(String users) {
        QCatchment qCatchment = QCatchment.catchment;
        BooleanExpression predicate = qCatchment.users.eq(Long.valueOf(users));
        List<Catchment> catchments = (List<Catchment>) catchmentRepository.findAll(predicate);
        return catchments.size();
    }

    @Override
    public List<Integer> allYearsCatchment() {
        List<Integer> years = catchmentRepository.getYears();
        return years;
    }

    @Override
    public Float cambioUM(Float precioObservado, Float cantidadObservada, Long specificationIdUMObservada, Float cantidadActual, Long specificationIdUMActual, Long varEstabId) {
//        Specification UMObservada = specificationRepository.findById(specificationIdUMObservada).get();
//        Specification UMActual = specificationRepository.findById(specificationIdUMActual).get();
//
//        //En caso que la UM Observada y UM Actual sean iguales (Ej. g y g).
//        if (UMObservada.getDescription().equalsIgnoreCase(UMActual.getDescription())) {
//
//            return calcularPrecio(precioObservado, cantidadObservada, cantidadActual);
//
//        } else { //EN todos los otros caso (Ej. g y Kg)
//
//            //Se obtiene la correlación de las UMs
////            Float correlacionUMObservada = correlacionadorRepository.findCorrelacionadorByUmConvertir(UMObservada).getRelacion();
////            Float correlacionUMActual = correlacionadorRepository.findCorrelacionadorByUmConvertir(UMActual).getRelacion();
//
//            Correlacionador correlacion = correlacionadorRepository.findCorrelacionadorByUmBaseAndUmConvertir(UMActual, UMObservada);
//            Float relacion = correlacion.getRelacion();
//
//
//            //Se obtiene la cantidad de cada UM (Actual y Observada) en la UM base
//            Float cantidadUMObservada = cantidadObservada * relacion;
//            Float cantidadUMActual = cantidadActual;
//
//            return calcularPrecio(precioObservado, cantidadUMObservada, cantidadUMActual);
//        }
        return Float.valueOf("5");
    }

    public Float precioMesAnterior(Long varEstabId) {
        Float precio = null;
        LocalDate current = LocalDate.now().minusMonths(1);
        LocalDate Localend = current.withDayOfMonth(31);
        LocalDate Localstart = current.withDayOfMonth(1);
        Date start = java.sql.Date.valueOf(Localstart);
        Date end = java.sql.Date.valueOf(Localend);
        List<Catchment> list = catchmentRepository.getCatchmentsByIdVariedadAndDateBetween(varEstabId, start, end);
        if (list.size() > 0) {
            precio = list.get(list.size() - 1).getPrice();
        }
        return precio;
    }

    public Float calcularPrecio(Float precioObs, Float cantObs, Float cantAct) {
        if (cantObs > cantAct) {
            Float factor = cantObs / cantAct;
            return precioObs / factor;
        } else {
            Float factor = cantAct / cantObs;
            return precioObs * factor;
        }
    }

    @Override
    public List<Specification> listUMsByTipoUM(Long tipoUM) {
        return specificationRepository.findAllByMeasurementUnitType_Id(tipoUM);
    }

    @Override
    public CatchmentViewResourcePage catchmentOutRange(PageResource pageResource) {
        Pageable pageable = utilHelper.buildPage(pageResource);
        QCatchment qe = QCatchment.catchment;
        BooleanExpression expression = captacionPredicado.generatePredicate(pageResource.getMarketId(), pageResource.getTypologyId(), pageResource.getEstablishment(), pageResource.getDpaId(), pageResource.getVarietyId(), null, pageResource.getYear(), pageResource.getMes(), pageResource.getVariedadName());
        if (expression == null)
            expression = captacionPredicado.predicadoCatchmentOutRange(pageResource);
        else {
            expression = expression.and(qe.fueraRango.isTrue());
            if (pageResource.getSemanaCaptacion() != -1) {
                //expression = expression.and(qe.establishment.plannings.any().week.eq(pageResource.getSemanaCaptacion())); no existe la relacion
            }

        }
        Page<Catchment> catchmentPage = catchmentRepository.findAll(expression, pageable);
        List<CaptacionResource> resources = buscarPrecioMesAnterior(captacionResourceAssambler.toResource(catchmentPage.getContent()));
        return captacionResourceAssambler.toResource(resources, catchmentPage.getTotalElements());
    }

    @Override
    public CatchmentViewResourcePage catchmentByImpute(PageResource pageResource) {
        Pageable pageable = utilHelper.buildPage(pageResource);
        Page<Catchment> catchmentPage = catchmentRepository.findAll(captacionPredicado.predImputarCaptacion(pageResource), pageable);
        List<CaptacionResource> resources = buscarPrecioMesAnterior(captacionResourceAssambler.toResource(catchmentPage.getContent()));
        return captacionResourceAssambler.toResource(resources, catchmentPage.getTotalElements());
    }

    @Override
    public boolean imputeCatchment(List<CatchmentImpute> cathments) {
        cathments.forEach(captacion -> {
            Catchment catchment = catchmentRepository.findById(captacion.getId()).get();
            catchment.setPrice(captacion.getLastPrice());
            catchmentRepository.save(catchment);
        });
        return false;
    }

    public boolean imputeCatchmentViejo(List<Long> cathments) {
        List<Catchment> catchmentList = catchmentRepository.getCatchmentIn(cathments);
        catchmentList.forEach(captacion -> {
            LocalDate localDate = UtilFecha.convertirToLocalDate(captacion.getDate()).minusMonths(1);
            LocalDate fechaIni;
            LocalDate fechaFin;
            if (captacion.getEstablishment().getMarket().getId() == TipoMercado.AGROPECUARIO.getTipo()) {
                LocalDate date = UtilFecha.convertirToLocalDate(captacion.getDate());
                if (date.getDayOfMonth() <= 15) {
                    fechaIni = localDate.withDayOfMonth(1);
                    fechaFin = localDate.withDayOfMonth(15);
                } else {
                    fechaIni = localDate.withDayOfMonth(15);
                    fechaFin = localDate.withDayOfMonth(localDate.lengthOfMonth());
                }
            } else {
                fechaIni = localDate.withDayOfMonth(1);
                fechaFin = localDate.withDayOfMonth(localDate.lengthOfMonth());
            }
            List<Catchment> list = catchmentRepository.captacionAnterior(captacion.getEstablishment().getId(), captacion.getIdVariedad(), UtilFecha.convertir(fechaIni), UtilFecha.convertir(fechaFin));
            if (list.size() != 0) {
                Catchment catchment = list.get(0);
                captacion.setImputed(true);
                captacion.setPrice(catchment.getPrice());

                catchmentRepository.save(captacion);
            }

        });
        return true;
    }

    private List<CaptacionResource> buscarPrecioMesAnterior(List<CaptacionResource> captacionResources) {
        captacionResources.forEach(captacion -> {
            LocalDate localDate = captacion.getFecha().minusMonths(1);
            LocalDate fechaIni;
            LocalDate fechaFin;
            if (captacion.getEstablishmentId() == TipoMercado.AGROPECUARIO.getTipo()) {
                if (captacion.getFecha().getDayOfMonth() <= 15) {
                    fechaIni = localDate.withDayOfMonth(1);
                    fechaFin = localDate.withDayOfMonth(15);
                } else {
                    fechaIni = localDate.withDayOfMonth(16);
                    fechaFin = localDate.withDayOfMonth(localDate.lengthOfMonth());
                }
                //para el calculo(ver si el promedio es por la misma semana de cada mes o las semanas del mismo mes)
                captacion.setPromedioPreciosAgropecuarioAnterior(promedioPreciosAgropecuario(localDate.withDayOfMonth(1), localDate.withDayOfMonth(localDate.lengthOfMonth()), captacion));
                captacion.setPromedioPreciosAgropecuario(promedioPreciosAgropecuario(captacion.getFecha().withDayOfMonth(1), captacion.getFecha().withDayOfMonth(localDate.lengthOfMonth()), captacion));
            } else {
                fechaIni = localDate.withDayOfMonth(1);
                fechaFin = localDate.withDayOfMonth(localDate.lengthOfMonth());
            }
            List<Catchment> list = catchmentRepository.captacionAnterior(captacion.getEstablishmentId(), captacion.getVarietyId(), UtilFecha.convertir(fechaIni), UtilFecha.convertir(fechaFin));
            if (list.size() != 0) {
                Catchment catchment = list.get(0);
                captacion.setPrecioMesAnterior(catchment.getPrice());
            }
        });
        return captacionResources;
    }

    @Override
    public Boolean agreeCatchmentOutRange(List<Long> id) {
        List<Catchment> catchmentList = catchmentRepository.getCatchmentIn(id);
        catchmentList.forEach(catchment -> {
            catchment.setFueraRango(false);
            catchmentRepository.save(catchment);
        });
        return true;
    }

    private Float promedioPreciosAgropecuario(LocalDate fechaIni, LocalDate fechaFin, CaptacionResource captacionResource) {
        List<Catchment> list = catchmentRepository.captacionAnterior(captacionResource.getEstablishmentId(), captacionResource.getVarietyId(), UtilFecha.convertir(fechaIni), UtilFecha.convertir(fechaFin));
        double precio = 0;
        if (list.size() != 0) {
            double suma = list.stream().mapToDouble(Catchment::getPrice).sum();
            precio = suma / list.size();
        }
        return (float) precio;
    }

    public CatchmentViewResourcePage calcularRelativoVar_Est(PageResource pageResource) {
        Pageable pageable = utilHelper.buildPage(pageResource);
        LocalDate fechaActual = LocalDate.now().minusMonths(1);
        Page<Catchment> catchmentPage = catchmentRepository.findAll(captacionPredicado.expressionCalculoRelativoVar_Est(UtilFecha.convertir(fechaActual.withDayOfMonth(1)), UtilFecha.convertir(fechaActual.withDayOfMonth(fechaActual.lengthOfMonth()))), pageable);
        List<CaptacionResource> resources = buscarPrecioMesAnterior(captacionResourceAssambler.toResource(catchmentPage.getContent()));
        resources.forEach(captacionResource -> {
            if (captacionResource.getEstablishmentId() == TipoMercado.AGROPECUARIO.getTipo()) {
                if ((captacionResource.getPromedioPreciosAgropecuarioAnterior() == 0 && captacionResource.getPromedioPreciosAgropecuarioAnterior() == null)
                        || (captacionResource.getPromedioPreciosAgropecuario() == 0 && captacionResource.getPromedioPreciosAgropecuario() == null)) {
                    resources.remove(captacionResource);
                } else {
                    captacionResource.setCalculoRelativoVar_Est(captacionResource.getPromedioPreciosAgropecuario() / captacionResource.getPromedioPreciosAgropecuarioAnterior());
                    Catchment catchment = catchmentById(captacionResource.getId());
                    catchment.setRelative(captacionResource.getCalculoRelativoVar_Est());
                }
            }
            if (captacionResource.getPrecioMesAnterior() == 0 && captacionResource.getPrecioMesAnterior() == null) {
                resources.remove(captacionResource);
            } else {
                captacionResource.setCalculoRelativoVar_Est(captacionResource.getPrecio() / captacionResource.getPrecioMesAnterior());
                Catchment catchment = catchmentById(captacionResource.getId());
                catchment.setRelative(captacionResource.getCalculoRelativoVar_Est());
            }
        });
        return captacionResourceAssambler.toResource(resources, resources.size());
    }

    @Override
    public Boolean calculoArticuloNacion(boolean mun) {
        List<IndiceArticulo> indiceArticulos = (List<IndiceArticulo>) indiceArticuloRepository.findAll(captacionPredicado.predicateIndice(null, (String.valueOf(LocalDate.now().getMonth().getValue())), mun, false));
        if (indiceArticulos.size() != 0) {
            indiceArticuloRepository.deleteAll(indiceArticulos);
        }
        //Duda si son las captaciones del mes o de todas las captaciones
        Month mesCaptacion = LocalDate.now().getMonth();
        captacionesDelMes = new ArrayList<>();
        captacionesDelMes = (List<Catchment>) catchmentRepository.findAll(captacionPredicado.predicateCaptacionesDelMes(mesCaptacion));
//        List<Catchment> paraBorrar = new ArrayList<>();
        //Para borrar las captaciones de provincia y solo dejar las de municipio
//        if (mun) {
//            if (captacionesDelMes.size() != 0) {
//                captacionesDelMes.forEach(catchment1 -> {
//                    if (catchment1.getEstablishment().getDpa().getParent() == null) {
//                        paraBorrar.add(catchment1);
//                    }
//                });
//                captacionesConRelativo.removeAll(paraBorrar);
//            }
//        }
        if (captacionesDelMes.size() != 0) {
            municipio = mun;
            calcularIndice();
        } else return false;

        return true;
    }

    private void calcularIndice() {
        int cont = 1;
        Catchment catchment = captacionesDelMes.get(0);
        double suma = catchment.getPrice();
        double prom = 0;
        List<Catchment> paraBorrar = new ArrayList<>();
        paraBorrar.add(catchment);
        if (captacionesDelMes.size() > 1) {
            for (int j = 1; j < captacionesDelMes.size(); j++) {
                if (municipio) {
                    if (catchment.getEstablishment().getDpa().getId().equals(captacionesConRelativo.get(j).getEstablishment().getDpa().getId())) {
                        if (catchment.getDVarCaracEspec().get(0).getClassifier().getId().equals(captacionesDelMes.get(j).getDVarCaracEspec().get(0).getClassifier().getId())) {
                            suma += captacionesDelMes.get(j).getPrice();
                            cont++;
                            paraBorrar.add(captacionesDelMes.get(j));
                        }
                    }
                } else if (catchment.getDVarCaracEspec().get(0).getClassifier().getId().equals(captacionesDelMes.get(j).getDVarCaracEspec().get(0).getClassifier().getId())) {
                    suma += captacionesDelMes.get(j).getPrice();
                    cont++;
                    paraBorrar.add(captacionesDelMes.get(j));
                }
            }
        }
        prom = suma / cont;
        //Se busca el indice del articulo a nivel nacional del mes anterior para multiplicar por el promedio del mes actual
        Optional<IndiceArticulo> articuloAnterior = indiceArticuloRepository.findOne(captacionPredicado.predicateIndice(catchment.getIdVariedad(), null, false, false));
        if (articuloAnterior.isPresent())
            prom *= articuloAnterior.get().getIndice();

        List<VarietyCharactSpecific> varietyCharactSpecifics = new ArrayList<>();
        varietyCharactSpecifics.addAll(catchment.getDVarCaracEspec());
        IndiceArticulo indiceArticulo = IndiceArticulo.builder()
                .indice((float) prom)
                .fecha(new Date())
                .idvariedad(catchment.getDVarCaracEspec().get(0).getClassifier().getId())
                .dVarCaracEspec(varietyCharactSpecifics)
                .idmunicipio(municipio == true ? catchment.getEstablishment().getDpa().getId() : null)
                .build();

        indiceArticuloRepository.save(indiceArticulo);
        captacionesDelMes.removeAll(paraBorrar);

        if (captacionesDelMes.size() != 0) {
            paraBorrar.clear();
            calcularIndice();
        }
    }

    //Duda si son las captaciones del mes o de todas las captaciones
//    private boolean buscarCaptacionesMes(String calcular){
//        Month mesCaptacion = LocalDate.now().getMonth();
//        captacionesDelMes = new ArrayList<>();
//        captacionesDelMes = (List<Catchment>) catchmentRepository.findAll(captacionPredicado.predicateCaptacionesDelMes(mesCaptacion));
//        if (captacionesDelMes.size() != 0) {
//            switch (calcular){
//                case "Indice Nacion":{
//                    calcularIndice();
//                }
//                case "Micro Mun":{
//                    microIndiceMun();
//                }
//            }
//        } else return false;
//
//        return true;
//    }

    @Override
    public IndiceArticuloViewResourcePage indiceArticuloDelMes(PageResource pageResource) {
        Pageable pageable = utilHelper.buildPage(pageResource);
        Page<IndiceArticulo> indiceArticuloPage = indiceArticuloRepository.findAll(captacionPredicado.predicateIndice(null, pageResource.getMes(), false, false), pageable);
        return indiceArticuloResourceAssambler.toResource(indiceArticuloPage, indiceArticuloPage.getTotalElements());
    }

    @Override
    public Boolean calcularMicroIndiceMun() {
        List<IndiceArticulo> indiceArticulos = (List<IndiceArticulo>) indiceArticuloRepository.findAll(captacionPredicado.predicateIndice(null, (String.valueOf(LocalDate.now().getMonth().getValue())), true, false));
        if (indiceArticulos.size() != 0) {
            indiceArticuloRepository.deleteAll(indiceArticulos);
        }
        captacionesConRelativo = (List<Catchment>) catchmentRepository.findAll(captacionPredicado.predicateCaptacionesRelativoTrue());
        List<Catchment> paraBorrar = new ArrayList<>();

        //Para borrar las captaciones de provincia y solo dejar las de municipio
//        if (captacionesConRelativo.size() != 0) {
//            captacionesConRelativo.forEach(catchment1 -> {
//                if (catchment1.getEstablishment().getDpa().getParent() == null) {
//                    paraBorrar.add(catchment1);
//                }
//            });
//            captacionesConRelativo.removeAll(paraBorrar);
//        }
        if (captacionesConRelativo.size() != 0) {
            microIndiceMun();
        } else return false;

        return true;
    }

    public void microIndiceMun() {
        Catchment catchment = captacionesConRelativo.get(0);
        int cont = 1;
        double suma = catchment.getRelative();
        double prom = 0;
        Long municipio = catchment.getEstablishment().getDpa().getId();
        List<Catchment> paraBorrar = new ArrayList<>();
        paraBorrar.add(catchment);
        if (captacionesConRelativo.size() > 1) {
            for (int j = 1; j < captacionesConRelativo.size(); j++) {
                if (catchment.getEstablishment().getDpa().getId().equals(captacionesConRelativo.get(j).getEstablishment().getDpa().getId())) {
                    if (catchment.getDVarCaracEspec().get(0).getClassifier().getId().equals(captacionesConRelativo.get(j).getDVarCaracEspec().get(0).getClassifier().getId())) {
                        suma += captacionesConRelativo.get(j).getRelative();
                        cont++;
                        paraBorrar.add(captacionesConRelativo.get(j));
                    }
                }
            }
        }
        prom = suma / cont;

        List<VarietyCharactSpecific> varietyCharactSpecifics = new ArrayList<>();
        varietyCharactSpecifics.addAll(catchment.getDVarCaracEspec());
        IndiceArticulo indiceArticulo = IndiceArticulo.builder()
                .indice((float) prom)
                .fecha(new Date())
                .idvariedad(catchment.getDVarCaracEspec().get(0).getClassifier().getId())
                .dVarCaracEspec(varietyCharactSpecifics)
                .idmunicipio(municipio)
                .build();

        indiceArticuloRepository.save(indiceArticulo);
        captacionesConRelativo.removeAll(paraBorrar);

        if (captacionesConRelativo.size() != 0) {
            paraBorrar.clear();
            microIndiceMun();
        }
    }
}
