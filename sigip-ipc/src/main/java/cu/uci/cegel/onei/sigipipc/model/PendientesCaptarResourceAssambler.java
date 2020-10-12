package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.*;
import cu.uci.cegel.onei.sigipipc.repository.CatchmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PendientesCaptarResourceAssambler {
    @Autowired
    CaracteristicaEspecificacionResourceAssambler caracteristicaEspecificacionResourceAssambler;

    @Autowired
    CatchmentRepository catchmentRepository;


    public PendietesCaptarResource toResource(VarietyEstablishment varietyEstablishment, Long total, Boolean incluirCaracteristicas) {


        Classifier variedad = varietyEstablishment.getClassifier();
        Establishment establish = varietyEstablishment.getEstablishment();

//        String day = ((List<Planning>) establish.getPlannings()).stream().filter(planning -> {
//            return planning.getDates().stream().filter(planningDate -> {
//                LocalDate testDate = planningDate.getDate();
//                return !(testDate.isBefore(start) || testDate.isAfter(end));
//            }).collect(Collectors.toList()).size() > 0;
//        }).collect(Collectors.toList()).get(0).getDay();
        String dia = varietyEstablishment.getPlanning().getDay();
        List<CaracteristicaEspecificacionResource> specs = new LinkedList<>();
        if (incluirCaracteristicas) {
            varietyEstablishment.getVarietyCharactSpecifics().size();
            caracteristicaEspecificacionResourceAssambler.toResource(varietyEstablishment.getVarietyCharactSpecifics());
        }
        return PendietesCaptarResource.builder()
                .varEstabId(varietyEstablishment.getId())
                .establecimiento(establish.getName())
                .mercado(establish.getMarket().getMarket().getDescription())
                .variedadDescription(variedad != null ? variedad.getDescription() : "-")
                .variedadId(variedad != null ? variedad.getId() : -1)
                .specs(specs)
                .total(total)
                .dia(dia)
                .moneda(establish.getMarket().getCurrency().getDescription())
                .build();
    }

    //el estabId es para que si es -1 osea todas las variedades no traer las carac
    public List<PendietesCaptarResource> toResource(List<VarietyEstablishment> list, Long total, Long estabId) {
        return list.stream().map(varietyEstablishment -> toResource(varietyEstablishment, total, estabId > -1))
                .collect(Collectors.toList());
    }


}
