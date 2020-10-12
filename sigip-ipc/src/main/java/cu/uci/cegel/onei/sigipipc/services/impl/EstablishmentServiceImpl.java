package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.repository.*;
import cu.uci.cegel.onei.sigipipc.services.CatchmentService;
import cu.uci.cegel.onei.sigipipc.services.EstablishmentService;
import cu.uci.cegel.onei.sigipipc.services.VarietyEstablishmentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstablishmentServiceImpl implements EstablishmentService {
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    MarketCurrencyRepository marketCurrencyRepository;
    @Autowired
    TypologyRepository typologyRepository;
    @Autowired
    DPARepository dpaRepository;
    @Autowired
    OneiIndexRepository oneiIndexRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    PlanningRepository planningRepository;
    @Autowired
    VarietyEstablishmentRepository varietyEstablishmentRepository;

    @Autowired
    VarietyEstablishmentService varietyEstablishmentService;

    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;
    @Autowired
    ClassifierRepository classifierRepository;

    @Autowired
    CatchmentService catchmentService;

    @Override
    public Establishment add(String organization, String name, String address, String contact, Integer phone, Long marketId, Long typologyId, Long dpaId, Long idnEstado) {
        MarketCurrency market = marketCurrencyRepository.findById(marketId).get();
        Typology typology = typologyRepository.findById(typologyId).get();
        DPA dpa = dpaRepository.findById(dpaId).get();
        State state = stateRepository.findById(idnEstado).get();

        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, "", "", idnEstado, null, null);
        List<Establishment> establishments = (List<Establishment>) establishmentRepository.findAll(predicate, Sort.by(Sort.Direction.ASC, "id"));

        int newConsecutive = 101;
        if (establishments.size() > 0) {
            String lasEstablishment = establishments.get(establishments.size() - 1).getCode();
            String lastConsecutive = lasEstablishment.substring(6);
            newConsecutive = Integer.parseInt(lastConsecutive);
            newConsecutive++;
        }
        String code = dpa.getCode() + "" + marketId + "" + typologyId + newConsecutive;

        Establishment establishment = new Establishment(code, organization, name, address, contact, phone, market, typology, dpa, state);
        return establishmentRepository.save(establishment);


    }

    @Override
    public List<Establishment> findAll() {
        return (List<Establishment>) establishmentRepository.findAll();
    }

    @Override
    public List<Establishment> findAllPaging(int paging, int size) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<Establishment> page = establishmentRepository.findAll(pageable);
            List<Establishment> establishments = page.getContent();
            return establishments;
        } else {
            return findAll();
        }
    }

    @Override
    public int totalEstablishment() {
        return establishmentRepository.countByActiveIsTrue();
    }

    @Override
    public Establishment edit(Long id, String name, String address, String contact, Integer phone) {
        Establishment establishment = establishmentRepository.findById(id).get();
        establishment.setName(name);
        establishment.setAddress(address);
        establishment.setContact(contact);
        establishment.setPhone(phone);

        establishmentRepository.save(establishment);
        return establishment;
    }

    @Override
    public Boolean delete(Long id) {
        establishmentRepository.deleteById(id);
        return true;
    }

    @Override
    public Establishment establishmentById(Long id) {
        Establishment e = establishmentRepository.findById(id).get();
        return e;

    }

    @Override
    public List<Establishment> filters(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, int paging, int size) {
        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, establishment, code, stateId, null, null);

        return getEstablishments(paging, size, predicate);
    }

    @NotNull
    private List<Establishment> getEstablishments(int paging, int size, BooleanExpression predicate) {
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<Establishment> page = establishmentRepository.findAll(predicate, pageable);
            List<Establishment> establishments = page.getContent();
            return establishments;
        } else {
            List<Establishment> establishments = (List<Establishment>) establishmentRepository.findAll(predicate);
            return establishments;
        }
    }

    @Override
    public List<Establishment> pending(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, int paging, int size) {
        // LocalDate e = LocalDate.of(2020, 2, 18);
        LocalDate hoy = LocalDate.now();
        LocalDate s = LocalDate.of(hoy.getYear(), hoy.getMonth(), 1);
        LocalDate e = LocalDate.of(hoy.getYear(), hoy.getMonth(), hoy.getDayOfMonth());
        List<Integer> a = varietyEstablishmentRepository.getEstablecimientosPlanificadosPara(s, e);
        List<Integer> establecimientosCaptados = varietyEstablishmentRepository.establecimientosCaptados(s, e);


        List<Long> lolo = establecimientosCaptados.stream().map(integer -> integer.longValue()).collect(Collectors.toList());
        List<Long> b = establecimientosCaptados.stream().map(integer -> integer.longValue()).collect(Collectors.toList());
        List<Long> ids = a.stream().map(integer -> integer.longValue()).collect(Collectors.toList());

        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, establishment, code, stateId, ids, lolo);

        return getEstablishments(paging, size, predicate);
    }

    @Override
    public List<Establishment> pendientesCaptar(Long establishmentId) {
        BooleanExpression predicate = createPendientesCaptarPredicate(establishmentId);

        return (List<Establishment>) establishmentRepository.findAll(predicate);
    }

    public BooleanExpression createPendientesCaptarPredicate(Long establishmentId) {
        Date start = new Date();
        LocalDate from = LocalDate.of(2019, 12, 1);
        LocalDate to = LocalDate.of(2019, 12, 31);
        QEstablishment qe = QEstablishment.establishment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.id.eq(establishmentId));
        // booleanExpressions.add(qe.varietyEstablishments.any().dcaptacionList.isEmpty().or(qe.varietyEstablishments.any().dcaptacionList.any().date.before(start)));
        //booleanExpressions.add(qe.plannings.any().dates.any().date.between(from, to)); la relacion no existe
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    @Override
    public int totalEstablishmentsByFilter(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId) {
        BooleanExpression predicate = createPredicate(marketId, typologyId, dpaId, establishment, code, stateId, null, null);
        Long salida = establishmentRepository.count(predicate);
        return salida.intValue();
    }

    private int cantVariedadesCaptadasBy(NumberPath<Long> idEstab) {
        QCatchment qCatchment = QCatchment.catchment;
        qCatchment.establishment.id.eq(idEstab);
        return 5;
    }

    private BooleanExpression createPredicate(Long marketId, Long typologyId, Long dpaId, String establishment, String code, Long stateId, List<Long> ids, List<Long> captados) {

        QEstablishment qe = QEstablishment.establishment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.active.isTrue());


        if (typologyId != -1) {
            Typology typology = typologyRepository.findById(typologyId).get();
            booleanExpressions.add(qe.typology.eq(typology));
        }
        if (marketId != -1) {
            booleanExpressions.add(qe.market.eq(marketCurrencyRepository.findById(marketId).get()));
        }
        if (dpaId != -1) {
            DPA dpa = dpaRepository.findById(dpaId).get();
            booleanExpressions.add(qe.dpa.eq(dpa));
        }
        if (!establishment.equals("-1") && !establishment.equals("")) {
            try {
                Long id = Long.valueOf(establishment);
                booleanExpressions.add(qe.id.eq(id));
            } catch (NumberFormatException e) {
                booleanExpressions.add(qe.name.toLowerCase().contains(establishment.toLowerCase()));
            }
        }
        if (!code.equals("")) {
            //booleanExpressions.add(qe.code.contains(code));
            booleanExpressions.add(qe.code.contains(code));
        }
        if (stateId != -1) {
            State state = stateRepository.findById(stateId).get();
            booleanExpressions.add(qe.state.eq(state));
        }
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public List<VarietyCharactSpecific> varietyCharactSpecificList(Long classifierId) {
        Classifier classifier = classifierRepository.findById(classifierId).get();
        return varietyCharactSpecificRepository.findAllByClassifier(classifier);
    }

    public boolean setActive(Long id, boolean active) {
        Establishment e = establishmentRepository.findById(id).get();
        e.setActive(active);
        establishmentRepository.save(e);
        return true;
    }

    @Override
    public boolean isCatched(Long idEstablishment) {
        return varietyEstablishmentService.cantPendienteByEstab(idEstablishment) == 0;

    }
}
