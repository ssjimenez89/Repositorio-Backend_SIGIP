package cu.uci.cegel.onei.sigipipc.services.CaptacionPredicado;

import com.querydsl.core.types.dsl.BooleanExpression;
import cu.uci.cegel.onei.sigipipc.model.PageResource;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.repository.DPARepository;
import cu.uci.cegel.onei.sigipipc.repository.MarketCurrencyRepository;
import cu.uci.cegel.onei.sigipipc.repository.MarketRepository;
import cu.uci.cegel.onei.sigipipc.repository.TypologyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CaptacionPredicado {

    @Autowired
    DPARepository dpaRepository;
    @Autowired
    TypologyRepository typologyRepository;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    MarketCurrencyRepository marketCurrencyRepository;

    public BooleanExpression generatePredicate(Long marketId, Long typologyId, String establishment, Long dpaId, Long varietyId, LocalDate fechaDigitada, String year, String mes, String variedadName) {
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

    public BooleanExpression predMismaCapMesAnterior(Long idVar, Long idEsta, LocalDate capActual, Float precio) {
        QCatchment qe = QCatchment.catchment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.establishment.id.eq(idEsta));
        booleanExpressions.add(qe.date.month().eq(capActual.getMonthValue() - 1));
        booleanExpressions.add(qe.date.year().eq(capActual.getYear()));
        booleanExpressions.add(qe.dVarCaracEspec.any().classifier.id.eq(idVar));
        booleanExpressions.add(qe.price.ne(precio));


        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicateFaltaOcacional(Long idVariedad, Long idEstableciemiento, Date startDate, Date endDate) {
        QCatchment qe = QCatchment.catchment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.idVariedad.eq(idVariedad));
        booleanExpressions.add(qe.establishment.id.eq(idEstableciemiento));
        booleanExpressions.add(qe.observation.id.eq(5L));
        booleanExpressions.add(qe.date.goe(startDate));
        booleanExpressions.add(qe.date.lt(endDate));
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predImputarCaptacion(PageResource pageResource) {
        QCatchment qCatchment = QCatchment.catchment;
        List<BooleanExpression> expressions = new ArrayList<>();
        expressions.add(qCatchment.price.eq(Float.valueOf(0)));
        expressions.add(qCatchment.observation.id.in(TipoObservacion.FALTA_ESTACIONAL.getTipo(), TipoObservacion.FALTA_OCASIONAL.getTipo()).or(qCatchment.incidence.id.eq(2L)));

        if (pageResource.getEstablishment() != null && !pageResource.getEstablishment().equals(""))
            expressions.add(qCatchment.establishment.name.toLowerCase().contains(pageResource.getEstablishment().toLowerCase()));
//            expressions.add(qCatchment.establishment.name.eq(pageResource.getEstablishment()));

        if (pageResource.getDpaId() != -1)
            expressions.add(qCatchment.establishment.dpa.id.eq(pageResource.getDpaId()));

        if (pageResource.getVarietyId() != -1)
            expressions.add(qCatchment.idVariedad.eq(pageResource.getVarietyId()));

        if (pageResource.getTypologyId() != -1)
            expressions.add(qCatchment.establishment.typology.id.eq(pageResource.getTypologyId()));

        Optional<BooleanExpression> predicate = expressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicadoCatchmentOutRange(PageResource pageResource) {
        QCatchment qCatchment = QCatchment.catchment;
        List<BooleanExpression> expressions = new ArrayList<>();
        expressions.add(qCatchment.fueraRango.isTrue());
        if (pageResource.getSemanaCaptacion() != -1) {
            //expressions.add(qCatchment.establishment.plannings.any().week.eq(pageResource.getSemanaCaptacion())); no exite la relacion
        }

        Optional<BooleanExpression> predicate = expressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicateAllCatchmentsLike(Catchment c) {
        QCatchment qe = QCatchment.catchment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>();
        booleanExpressions.add(qe.establishment.id.eq(c.getEstablishment().getId()));
        booleanExpressions.add(qe.date.eq(c.getDate()));
        Optional<BooleanExpression> predicate = booleanExpressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression expressionCalculoRelativoVar_Est(Date fechaIni, Date fechaFin) {
        QCatchment qCatchment = QCatchment.catchment;
        List<BooleanExpression> expressionList = new ArrayList<>();
        expressionList.add(qCatchment.fueraRango.eq(false));
        expressionList.add(qCatchment.price.ne(Float.valueOf(0)));
        expressionList.add(qCatchment.date.between(fechaIni, fechaFin));

        Optional<BooleanExpression> predicate = expressionList.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicateCalculoNacion() {
        QCatchment qCatchment = QCatchment.catchment;
        List<BooleanExpression> expressionList = new ArrayList<>();
        expressionList.add(qCatchment.dVarCaracEspec.any().classifier.varietyType.id.eq(3L));

        Optional<BooleanExpression> predicate = expressionList.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicateCaptacionesDelMes(Month month) {
        QCatchment qCatchment = QCatchment.catchment;
        List<BooleanExpression> expressions = new ArrayList<>();
        if (month != null)
            expressions.add(qCatchment.date.month().eq(month.getValue()));
        expressions.add(qCatchment.date.year().eq(LocalDate.now().getYear()));
        expressions.add(qCatchment.dVarCaracEspec.any().classifier.classifierType.id.eq(5L));
//        Revisar si son las variedades centralizadas en la nacion que son del tipo 3
//        expressionList.add(qCatchment.dVarCaracEspec.any().classifier.varietyType.id.eq(3L));
        Optional<BooleanExpression> predicate = expressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicateIndice(Long idvariedad, String mes, boolean idMun, boolean idProv) {
        QIndiceArticulo qIndiceArticulo = QIndiceArticulo.indiceArticulo;
        List<BooleanExpression> expressions = new ArrayList<>();
        if (mes != null) {
            expressions.add(qIndiceArticulo.fecha.month().eq(Integer.valueOf(mes)));
        } else {
            expressions.add(qIndiceArticulo.fecha.month().eq(LocalDate.now().getMonth().minus(1).getValue()));
        }
        if (idvariedad != null)
            expressions.add(qIndiceArticulo.idvariedad.eq(idvariedad));

        if (idMun)
            expressions.add(qIndiceArticulo.idmunicipio.isNotNull());

        if (idProv)
            expressions.add(qIndiceArticulo.idprovincia.isNotNull());

        if (!idMun && !idProv) {
            expressions.add(qIndiceArticulo.idmunicipio.isNull());
            expressions.add(qIndiceArticulo.idprovincia.isNull());
        }

        Optional<BooleanExpression> predicate = expressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }

    public BooleanExpression predicateCaptacionesRelativoTrue() {
        QCatchment qCatchment = QCatchment.catchment;
        List<BooleanExpression> expressions = new ArrayList<>();
        expressions.add(qCatchment.relative.isNotNull());
        Optional<BooleanExpression> predicate = expressions.stream().reduce(BooleanExpression::and);
        return predicate.orElse(null);
    }
}
