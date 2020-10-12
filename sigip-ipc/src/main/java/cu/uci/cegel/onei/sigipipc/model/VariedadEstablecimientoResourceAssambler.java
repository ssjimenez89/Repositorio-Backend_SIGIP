package cu.uci.cegel.onei.sigipipc.model;

import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyEstablishment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VariedadEstablecimientoResourceAssambler {

    public static VariedadEstablecimientoResource toResource(VarietyEstablishment el, List<Long> idsVarEstab, List<String> dayPlannings){

        return VariedadEstablecimientoResource.builder()
                .listIdVarEstab(idsVarEstab)
                .varietyEstablishment(el)
                .active(el.isActive())
                .establecimientoDescripcion(el.getEstablishment().getName())
                .stateDescripcion(el.getState().getDescripcion())
                .mercadoDescripcion(el.getEstablishment().getMarket().getMarket().getDescription())
                .monedaDescripcion(el.getEstablishment().getMarket().getCurrency().getDescription())
                .dpaDescripcion(el.getEstablishment().getDpa().getCode() + '-' + el.getEstablishment().getDpa().getDescription())
                .variedadDescripcion(el.getClassifier().getDescription())
                .unidadMedidaDescripcion(el.getUnidadMedida().getDescription())
                .cantidadDescripcion(el.getCantidad().getDescription())
                .dayPlanning1(dayPlannings.get(0))
                .dayPlanning2(dayPlannings.get(1))
                .dayPlanning3(dayPlannings.get(2))
                .dayPlanning4(dayPlannings.get(3)).build();
    }

    public static VariedadEstablecimientoResource toResourceById(VarietyEstablishment el, List<Long> idsVarEstab, List<String> planningDay, List<Long> planningId){

        return VariedadEstablecimientoResource.builder()
                .listIdVarEstab(idsVarEstab)
                .varietyEstablishment(el)
                .active(el.isActive())
                .establecimientoDescripcion(el.getEstablishment().getName())
                .stateDescripcion(el.getState().getDescripcion())
                .mercadoDescripcion(el.getEstablishment().getMarket().getMarket().getDescription())
                .monedaDescripcion(el.getEstablishment().getMarket().getCurrency().getDescription())
                .dpaDescripcion(el.getEstablishment().getDpa().getCode() + '-' + el.getEstablishment().getDpa().getDescription())
                .variedadDescripcion(el.getClassifier().getDescription())
                .unidadMedidaDescripcion(el.getUnidadMedida().getDescription())
                .cantidadDescripcion(el.getCantidad().getDescription())
                .dayPlanning1Id1(planningId.get(0))
                .dayPlanning1(planningDay.get(0))
                .dayPlanning1Id2(planningId.get(1))
                .dayPlanning2(planningDay.get(1))
                .dayPlanning1Id3(planningId.get(2))
                .dayPlanning3(planningDay.get(2))
                .dayPlanning1Id4(planningId.get(3))
                .dayPlanning4(planningDay.get(3)).build();
    }
}
