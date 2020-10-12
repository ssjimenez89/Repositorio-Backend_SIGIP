package cu.uci.cegel.onei.sigipipc.services.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.kernel.errors.EntityNotFound;
import cu.uci.cegel.onei.sigipipc.model.ClassifierInput;
import cu.uci.cegel.onei.sigipipc.model.varietyCharactSpecificsInput;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.repository.*;
import cu.uci.cegel.onei.sigipipc.services.ClassifierService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class ClassifierServiceImpl implements ClassifierService {

    @Autowired
    ClassifierRepository classifierRepository;
    @Autowired
    ClassifierWeighingRepository classifierWeighingRepository;
    @Autowired
    ClassifierTypeRepository classifierTypeRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    OneiIndexRepository oneiIndexRepository;
    @Autowired
    WeighingTypeRepository weighingTypeRepository;
    @Autowired
    VarietyTypeRepository varietyTypeRepository;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    MarketCurrencyRepository marketCurrencyRepository;
    @Autowired
    EstablishmentRepository establishmentRepository;
    @Autowired
    VarietyEstablishmentRepository varietyEstablishmentRepository;
    @Autowired
    VarietyCharactSpecificRepository varietyCharactSpecificRepository;
    @Autowired
    SpecificationRepository specificationRepository;

    @Override
    public Classifier findOneByDescription(String description) {
        Classifier classifier = classifierRepository.findByDescriptionEquals(description);
        return classifier;
    }
    @Transactional
    @Override
    public List<Classifier> getAll() {
        List<Classifier> allByParentNull = classifierRepository.findAllByParentNull(Sort.by(Sort.Direction.ASC, "code"));
        allByParentNull.stream().forEach(el -> {
            el.getClassifierWeighings().size();
            el.getClassifierType().getId();
            el.setChildrens(new LinkedList<>());
            el.getMarketCurrencies().size();
            el.getVarietyCharactSpecifics().size();
        });
        return allByParentNull;
    }

    @Transactional
    @Override
    public List<Classifier> classifierById(Long id) {
        Classifier classifier = classifierRepository.findById(id).orElseThrow(
                () -> new EntityNotFound(Classifier.class.getSimpleName(),
                        id, HttpStatus.NOT_FOUND));
        classifier.getChildrens().size();
        classifier.getLevel();
        classifier.getMarketCurrencies().size();
        classifier.getClassifierWeighings().size();
        classifier.getClassifierType().getId();
        classifier.setVarietyCharactSpecificsActives(classifier.varietyCharactSpecificsActive());
        classifier.getChildrens().stream().forEach(els -> {
            els.getClassifierWeighings().size();
            els.setChildrens(new LinkedList<>());
            els.getLevel();
            els.getMarketCurrencies().size();
            els.getClassifierType().getId();
            els.setVarietyCharactSpecificsActives(els.varietyCharactSpecificsActive());
            ;
        });
        classifier.getVarietyCharactSpecifics().size();
        classifier.getLevel();
        classifier.getMarketCurrencies().size();
        classifier.getClassifierType().getId();

        List<Classifier> c = new LinkedList<>();
        c.add(classifier);
        return c;
    }

    @Override
    public Classifier findById(Long id) {
        Classifier classifier = classifierRepository.findById(id).get();
        return classifier;
    }

    @Transactional
    @Override
    public Long deleteClassifier(Long id) {
        classifierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound(Classifier.class.getSimpleName(),
                        id, HttpStatus.NOT_FOUND));

        classifierRepository.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public Classifier manageClassifier(ClassifierInput input) {
        if (!Objects.isNull(input.getAction())) {
            return addClassifier(input);
        }
        return editClassifier(input);
    }

    private Classifier addClassifier(@NotNull ClassifierInput input) {

        Classifier parent = classifierRepository.findById(input.getParent()).orElse(null);
        ClassifierType classifierType = classifierTypeRepository.findById(input.getIdclassifierType()).orElse(null);
        Classifier classifier = new Classifier(input.getDescription(), input.getCode());
        classifier.setParent(parent);
        classifier.setClassifierType(classifierType);

        if (classifierType.getCodeClassifier().equals("6")) {
            List<VarietyCharactSpecific> varietyCharactSpecific = getVarietyCharactSpecific(input.getSpecifics(), classifier);
            VarietyType varietyType = varietyTypeRepository.findById(input.getVarietyType()).orElse(null);
            classifier.setVarietyType(varietyType);
            classifier.setMarketCurrencies(getMarketCurrenciesList(input.getMarketCurrencies()));
            classifier.setVarietyCharactSpecifics(varietyCharactSpecific);

        } else {
            if (!Objects.isNull(input.getWeights())) {
                List<ClassifierWeighing> weighings =
                        input.getWeights()
                                .stream()
                                .map(cwi ->
                                        new ClassifierWeighing(
                                                cwi.getValue(),
                                                currencyRepository.findById(cwi.getCurrency()).get(),
                                                weighingTypeRepository.findById(cwi.getWeighingType()).get())
                                )
                                .collect(Collectors.toList());
                classifier.setClassifierWeighings(weighings);
                classifier = this.updateWeighings(classifier);
            }
        }
        classifier.getLevel();
        classifier.setChildrens(new LinkedList<>());
        classifier.setVarietyCharactSpecificsActives(classifier.getVarietyCharactSpecifics());
        classifier = classifierRepository.save(classifier);
        return classifier;
    }

    private Classifier editClassifier(@NotNull ClassifierInput input) {
        Classifier classifier = classifierRepository.findById(input.getId()).orElse(null);

        classifier.getLevel();
        classifier.getChildrens().stream().forEach(els -> {
            els.getClassifierWeighings().size();
            els.getMarketCurrencies().size();
            els.getVarietyCharactSpecifics().size();
        });
        classifier.getMarketCurrencies().size();
        classifier.getClassifierWeighings().size();
        classifier.getVarietyCharactSpecifics().size();
        classifier.getClassifierType().getId();

        classifier.setCode(input.getCode());
        classifier.setDescription(input.getDescription());
        if (input.getIdclassifierType() < 6L) {
            List<ClassifierWeighing> weighings = input.getWeights().stream().map(cwi -> {
                if (Objects.isNull(cwi.getId())) {
                    return new ClassifierWeighing(
                            cwi.getValue(),
                            currencyRepository.findById(cwi.getCurrency()).get(),
                            weighingTypeRepository.findById(cwi.getWeighingType()).get());
                } else {
                    ClassifierWeighing classifierWeighing = classifierWeighingRepository.findById(cwi.getId()).get();
                    classifierWeighing.setValue(cwi.getValue());
                    classifierWeighing.setCurrency(currencyRepository.findById(cwi.getCurrency()).get());
                    classifierWeighing.setWeighingType(weighingTypeRepository.findById(cwi.getWeighingType()).get());
                    return classifierWeighing;
                }
            }).collect(Collectors.toList());
            //this.updateWeighingList(weighings, classifier);
            classifier.setClassifierWeighings(weighings);
            classifier = this.updateWeighings(classifier);
            classifier = classifierRepository.save(classifier);

        } else {
            Long varietyId = input.getVarietyType();
            VarietyType varietyType = varietyTypeRepository.findById(varietyId).orElseThrow(() -> new EntityNotFound(VarietyType.class.getSimpleName(),
                    varietyId, HttpStatus.NOT_FOUND));
            classifier.setVarietyType(varietyType);
            classifier.setMarketCurrencies(getMarketCurrenciesList(input.getMarketCurrencies()));
            if (!Objects.isNull(input.getSpecifics())) {
                getVarietyCharactSpecific(input.getSpecifics(), classifier);
            }
            VarietyCharactSpecificActiveOrNot(input.getVarietyCharactSpecifics());
            List<VarietyCharactSpecific> allByClassifierAndActive = varietyCharactSpecificRepository.findAllByClassifierAndActiveTrue(classifier);
            classifier.setVarietyCharactSpecificsActives(allByClassifierAndActive);
        }
//        classifier = classifierRepository.save(classifier);
        return classifier;
    }

    private List<MarketCurrency> getMarketCurrenciesList(List<Long> ids) {
        return ids.stream().map(el -> marketCurrencyRepository.findById(el).orElseThrow(
                () -> new EntityNotFound(MarketCurrency.class.getSimpleName(),
                        el, HttpStatus.NOT_FOUND)
        )).collect(Collectors.toList());
    }

    private void VarietyCharactSpecificActiveOrNot(List<varietyCharactSpecificsInput> varietyCharactSpecifics) {
        varietyCharactSpecifics.stream().forEach(el -> {
            VarietyCharactSpecific varietyCharactSpecific = varietyCharactSpecificRepository.findById(el.getId()).get();
            varietyCharactSpecific.setActive(el.getActive());
            varietyCharactSpecificRepository.save(varietyCharactSpecific);
        });
    }

    private List<VarietyCharactSpecific> getVarietyCharactSpecific(List<Long> idSpecifiaction, Classifier classifier) {
        return idSpecifiaction.stream().map(el -> {
            Specification specification = specificationRepository.findById(el).orElseThrow(() -> new EntityNotFound(Specification.class.getSimpleName(),
                    el, HttpStatus.NOT_FOUND));

            VarietyCharactSpecific varietyCharactSpecific = new VarietyCharactSpecific(classifier, specification);
            varietyCharactSpecificRepository.save(varietyCharactSpecific);
            return varietyCharactSpecific;
        }).collect(Collectors.toList());
    }

    private Classifier updateWeighings(@NotNull Classifier classifier) {
        classifier.getClassifierWeighings().forEach(classifierWeighing -> classifierWeighing.setClassifier(classifier));
        return classifier;
    }

    private void updateWeighingList(@NotNull List<ClassifierWeighing> weighings, Classifier classifier) {
        List<Long> weighingsList = weighings
                .stream()
                .filter(elm -> elm.getId() != null).map(ClassifierWeighing::getId).collect(Collectors.toList());

        if (weighingsList.isEmpty()) {
            classifierWeighingRepository.deleteByClassifier_Id(classifier.getId());
        } else if (weighingsList.size() <= 6) {
            classifierWeighingRepository.findAll().forEach(el -> {
                if (!weighingsList.contains(el.getId())) {
                    classifierWeighingRepository.deleteById(el.getId());
                }
            });
        }
    }

    // Obtener y paginar las Variedades!!!

    @Override
    public List<Classifier> findAllByClassifierTypeEquals(Long id_Classifier_Type) {
        ClassifierType classifierType = classifierTypeRepository.findById(id_Classifier_Type).get();
        List<Classifier> classifiers = classifierRepository.findAllByClassifierTypeEquals(classifierType);
        classifiers.stream().forEach(elms -> {
            elms.getVarietyCharactSpecifics().size();
        });
        return classifiers;
    }

    @Override
    public List<Classifier> findAllByClassifierTypeEquals(Long id_Classifier_Type, int paging, int size) {
        ClassifierType classifierType = classifierTypeRepository.findById(id_Classifier_Type).get();
        if (size != -1) {
            Pageable pageable = PageRequest.of(paging, size);
            Page<Classifier> page = classifierRepository.findAllByClassifierTypeEquals(classifierType, pageable);
            List<Classifier> classifiers = page.getContent();
            return classifiers;
        } else {
            return findAllByClassifierTypeEquals(id_Classifier_Type);
        }
    }

    @Override
    public int totalClassifierXClassifierType(Long id) {
        ClassifierType classifierType = classifierTypeRepository.findById(id).get();
        List<Classifier> classifiers = classifierRepository.findAllByClassifierTypeEquals(classifierType);
        return classifiers.size();
    }

    @Override
    @Transactional
    public List<Classifier> classifierByMarket(Long id_market, Long id_Establishment, int size, int page) {
        if (id_Establishment != -1) {
            Establishment establishment = establishmentRepository.findById(id_Establishment).get();
            List<VarietyEstablishment> varietyEstablishmentList = varietyEstablishmentRepository.findAllByEstablishment(establishment);

            List<Long> classifierComparatorId = new ArrayList<Long>();
            varietyEstablishmentList.stream()
                    .map(it -> classifierComparatorId.add(it.getClassifier().getId()))
                    .collect(Collectors.toList());

            List<Integer> idList = classifierRepository.classifierByMarketExcludingClassifier(id_market, classifierComparatorId, size, page);

            return idList.stream()
                    .map(el -> classifierRepository.findById(el.longValue()).get())
                    .collect(Collectors.toList());
        } else {

            List<Integer> idList = classifierRepository.classifierByMarket(id_market, size, page);
            return idList.stream()
                    .map(el -> classifierRepository.findById(el.longValue()).get())
                    .collect(Collectors.toList());
        }
    }

    public int totalClassifierByMarket(Long id_market, Long id_Establishment) {
        if (id_Establishment != -1) {
            Establishment establishment = establishmentRepository.findById(id_Establishment).get();
            List<VarietyEstablishment> varietyEstablishmentList = varietyEstablishmentRepository.findAllByEstablishment(establishment);

            List<Long> classifierComparatorId = new ArrayList<Long>();
            varietyEstablishmentList.stream()
                    .map(it ->
                         classifierComparatorId.add(it.getClassifier().getId())
                    )
                    .collect(Collectors.toList());

            return classifierRepository.totalClassifierByMarketExcludingClassifier(id_market, classifierComparatorId);
        } else {
            return classifierRepository.totalClassifierByMarket(id_market);
        }

    }

    @Override
    public List<Classifier> filters(String descripcion, int page, int size) {
        BooleanExpression predicate = createPredicate(descripcion);
        Pageable pageable = PageRequest.of(page, size);
        Page<Classifier> variedadesPage = classifierRepository.findAll(predicate, pageable);
        List<Classifier> variedadesList = variedadesPage.getContent();
        return variedadesList;
    }

    @Override
    public List<Classifier> filters(String descripcion, int page, int size, int tipo) {
        BooleanExpression predicate = createPredicate(descripcion, tipo);
        Pageable pageable = PageRequest.of(page, size);
        Page<Classifier> clasificadoresPage = classifierRepository.findAll(predicate, pageable);
        List<Classifier> clasificadoresList = clasificadoresPage.getContent();
        return clasificadoresList;
    }

    @Override
    public List<Classifier> variedadesFiltradasByMarket(Long id_market, Long id_Establishment, int size, int page, String descripcion) {
        if (id_Establishment != -1) {
            Establishment establishment = establishmentRepository.findById(id_Establishment).get();
            List<VarietyEstablishment> varietyEstablishmentList = varietyEstablishmentRepository.findAllByEstablishment(establishment);

            List<Long> classifierComparatorId = new ArrayList<Long>();
            varietyEstablishmentList.stream()
                    .map(it -> classifierComparatorId.add(it.getClassifier().getId()))
                    .collect(Collectors.toList());

            List<Integer> idList = classifierRepository.filterByDescriptionOfClassifierByMarketExcludingClassifier(id_market, classifierComparatorId, size, page, descripcion);

            return idList.stream()
                    .map(el -> classifierRepository.findById(el.longValue()).get())
                    .collect(Collectors.toList());
        } else {

            List<Integer> idList = classifierRepository.filterByDescriptionOfClassifierByMarket(id_market, size, page, descripcion);
            return idList.stream()
                    .map(el -> classifierRepository.findById(el.longValue()).get())
                    .collect(Collectors.toList());
        }
        //return null;
    }

    @Override
    public Integer totalvariedadesFiltradasByMarket(Long id_market, Long id_Establishment, String descripcion) {
        if (id_Establishment != -1) {
            Establishment establishment = establishmentRepository.findById(id_Establishment).get();
            List<VarietyEstablishment> varietyEstablishmentList = varietyEstablishmentRepository.findAllByEstablishment(establishment);

            List<Long> classifierComparatorId = new ArrayList<Long>();
            varietyEstablishmentList.stream()
                    .map(it -> classifierComparatorId.add(it.getClassifier().getId()))
                    .collect(Collectors.toList());

            return classifierRepository.totalFilterByDescriptionOfClassifierByMarketExcludingClassifier(id_market, classifierComparatorId, descripcion);
        } else {
            return classifierRepository.totalFilterByDescriptionOfClassifierByMarket(id_market, descripcion);
        }
    }

    private BooleanExpression createPredicate(String descripcion) {

        QClassifier qe = QClassifier.classifier;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.active.isTrue());
        if (!descripcion.equals("")) {
            booleanExpressions.add(qe.description.toLowerCase().contains(descripcion.toLowerCase()));
            booleanExpressions.add(qe.classifierType.id.eq(Long.valueOf(6)));
        }
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    private BooleanExpression createPredicate(String descripcion, int tipo) {

        QClassifier qe = QClassifier.classifier;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.active.isTrue());
        if (!descripcion.equals("")) {
            booleanExpressions.add(qe.description.toLowerCase().contains(descripcion.toLowerCase()));
            booleanExpressions.add(qe.classifierType.id.eq(Long.valueOf(tipo)));
        }
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }
}
