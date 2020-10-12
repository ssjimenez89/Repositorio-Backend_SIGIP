package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.model.CaptacionResource;
import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResource;
import cu.uci.cegel.onei.sigipipc.model.CaracteristicaEspecificacionResourceAssambler;
import cu.uci.cegel.onei.sigipipc.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CaptacionResourceAssambler {
    @Autowired
    CaracteristicaEspecificacionResourceAssambler caracteristicaEspecificacionResourceAssambler;
    @Autowired
    BasicStringAndIdResourceAssambler basicStringAndIdResourceAssambler;

    public CaptacionResource toResource(Catchment captacion, Long total) {
        return buildCommon(captacion, false)
                .total(total)
                .build();
    }

    public List<CaptacionResource> toResource(List<Catchment> list, Long total) {
        return list.stream().map(cap -> toResource(cap, total)).collect(Collectors.toList());
    }

    private CaptacionResource.CaptacionResourceBuilder buildCommon(Catchment captacion, Boolean incluirCaracteristicas) {

        Classifier variedad = captacion.getClassifier();
        Establishment esta = captacion.getEstablishment();
        List<CaracteristicaEspecificacionResource> specs = new LinkedList<>();
        if (incluirCaracteristicas) {
            captacion.getDVarCaracEspec().size();
            specs = caracteristicaEspecificacionResourceAssambler.toResource(captacion.getDVarCaracEspec());
        }
        //adicionar aki las propiedades nuevas que se desee obtener
        return CaptacionResource.builder()
                .id(captacion.getId())
                .imputed(captacion.getImputed())
                .precio(captacion.getPrice())
                .user(captacion.getUsers())
                .incidenceId(captacion.getIncidence().getId())
                .incidenceName(captacion.getIncidence().getDescription())
                .dpa(esta.getDpa())
                .currency(captacion.getCurrency())
                .establishmentId(esta.getId())
                .establishmentName(esta.getName())
                .incidencia(captacion.getIncidence())
                .observacion(captacion.getObservation())
                .marketDescription(esta.getMarket().getMarket().getDescription())
                .specs(specs)
                .fecha(captacion.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .varietyId(variedad.getId())
                .varEstabId(captacion.getIdVariedad())
                .varietyName(variedad.getDescription())
                .cant(basicStringAndIdResourceAssambler.toResource(captacion.getCantidad().getDescription(),captacion.getCantidad().getId()))
                .unidadMedida(basicStringAndIdResourceAssambler.toResource(captacion.getUnidadMedida().getDescription(),captacion.getUnidadMedida().getId()));

    }

    public CaptacionResource toResource(Catchment captacion) {
        return buildCommon(captacion, true)
                .tipologiaName(captacion.getEstablishment().getTypology().getDescription())
                .relativo(captacion.getRelative())
                .total(1L)
                .build();


    }

    public CatchmentViewResourcePage toResource(Page<Catchment> catchments, long totalItems) {
        List<CaptacionResource> collect = catchments.stream()
                .map(this::toResource)
                .collect(Collectors.toList());

        return CatchmentViewResourcePage.builder()
                .resources(collect)
                .totalItems(totalItems)
                .build();
    }

    public List<CaptacionResource> toResource(List<Catchment> catchments) {
        return catchments.stream().map(this::toResource).collect(Collectors.toList());
    }

    public CatchmentViewResourcePage toResource(List<CaptacionResource> resources, long totalItems) {
        return CatchmentViewResourcePage.builder()
                .resources(resources)
                .totalItems(totalItems)
                .build();
    }
}

