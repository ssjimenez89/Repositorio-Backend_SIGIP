package cu.uci.cegel.onei.sigipipc.services;

import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.persistence.PlanningDate;

import java.time.LocalDate;
import java.util.List;


public interface PlanningDateService {

    int generarPlanificacionFecha(int anno);

    PlanningDate obtenerPlanificacionById(long id);

    List<Integer> obtenerAnnosPlanificados();

    List<Integer> generarAnnos(int cantidadAnnosFuturos);

    List<PlanningDate> findAll();

    List<PlanningDate> findAllPaging(int page, int size);

    int totalPlanningDate();

    List<Planning> diasPlanificados(LocalDate desde, LocalDate hasta);


}
