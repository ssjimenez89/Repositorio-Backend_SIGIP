package cu.uci.cegel.onei.sigipipc.services.impl;

import cu.uci.cegel.onei.sigipipc.persistence.Planning;
import cu.uci.cegel.onei.sigipipc.persistence.PlanningDate;
import cu.uci.cegel.onei.sigipipc.repository.PlanningDateRepository;
import cu.uci.cegel.onei.sigipipc.repository.PlanningRepository;
import cu.uci.cegel.onei.sigipipc.services.PlanningDateService;
import cu.uci.cegel.onei.sigipipc.trazas.TipoOperacion;
import cu.uci.cegel.onei.sigipipc.trazas.TiposTraza;
import cu.uci.cegel.onei.sigipipc.trazas.notifier.Notifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlanningDateServiceImpl implements PlanningDateService {

    @Autowired
    private PlanningDateRepository planningDateRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private Notifier notifier;

    private static boolean bisciesto(int anno) {
        return (anno % 4 == 0) && ((anno % 100 != 0) || (anno % 400 == 0));
    }

    private static Map diasXmes(int anno) {

        Map<Integer, Integer> salida = new HashMap<Integer, Integer>();
        //        mes,dia
        salida.put(0, 31);
        if (bisciesto(anno)) {
            salida.put(1, 29);
        } else {
            salida.put(1, 28);
        }
        salida.put(2, 31);
        salida.put(3, 30);
        salida.put(4, 31);
        salida.put(5, 30);
        salida.put(6, 31);
        salida.put(7, 31);
        salida.put(8, 30);
        salida.put(9, 31);
        salida.put(10, 30);
        salida.put(11, 31);
        return salida;
    }

    private static Map abrevDiasSemana() {

        Map<Integer, String> salida = new HashMap<Integer, String>();
        //        dia_semana,dia
        salida.put(1, "D");
        salida.put(2, "L");
        salida.put(3, "Ma");
        salida.put(4, "Mi");
        salida.put(5, "J");
        salida.put(6, "V");
        salida.put(7, "S");

        return salida;
    }

    private static LocalDate getFechaFormateada(Calendar fecha) {
        return LocalDate.of(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH) + 1, fecha.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    @Transactional
    public int generarPlanificacionFecha(int anno) {
        ArrayList<PlanningDate> salida = new ArrayList<>();
        for (int i = 0; i < diasXmes(anno).size(); i++) {
            ArrayList<PlanningDate> mensual = captacionesXMes(anno, i);
            salida.addAll(mensual);
        }
        planningDateRepository.saveAll(salida);
        notifier.publish(TiposTraza.FUNCIONAL,"Se ha generado la planificación del año "+ anno, TipoOperacion.GENERIC_REGISTRAR);
        return anno;
    }

    @Override
    public PlanningDate obtenerPlanificacionById(long id) {
        return planningDateRepository.findById(id).get();
    }

    @Override
    public List<Integer> obtenerAnnosPlanificados() {
        return planningDateRepository.obtenerAnnosPlanificados()
                .stream()
                .map(Double::intValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<Integer> generarAnnos(int cantidadAnnosFuturos) {
        List<Integer> salida = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        int anno = hoy.getYear();
        salida.add(anno);
        for (int i = 1; i <= cantidadAnnosFuturos; i++) {
            salida.add(anno + i);
        }
        List<Integer> annosPlanificados = obtenerAnnosPlanificados();
        salida.removeAll(annosPlanificados);
        return salida;
    }

    @Override
    public List<PlanningDate> findAll() {
        return (List<PlanningDate>) planningDateRepository.findAll();
    }

    @Override
    public List<PlanningDate> findAllPaging(int page, int size) {
        return null;
    }

    @Override
    public int totalPlanningDate() {
        return 0;
    }

    @Override
    public List<Planning> diasPlanificados(LocalDate desde, LocalDate hasta) {

        return planningDateRepository.diasPlanificados(desde, hasta);
    }

    private ArrayList<PlanningDate> captacionesXMes(int anno, int mes) {

        ArrayList<PlanningDate> salida = new ArrayList<>();
        Map diasXmes = diasXmes(anno);
        //banderas
        boolean primer_miercoles_encontrado = false;
        boolean hay_miercoles_1ra_semana = false;
        final int semana_final_captacion = 4;

        for (int i = 1; i <= (Integer) diasXmes.get(mes); i++) {

            PlanningDate obj = new PlanningDate();
            Calendar dia = new GregorianCalendar(anno, mes, i);

            int dia_semana = dia.get(Calendar.DAY_OF_WEEK);
            int semana_mes = dia.get(Calendar.WEEK_OF_MONTH);

            if (primer_miercoles_encontrado) {

                switch (dia_semana) {
                    case 2://Lunes
                        if (hay_miercoles_1ra_semana) {
                            if (semana_mes <= semana_final_captacion) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.MONDAY)).concat("-").concat(String.valueOf(semana_mes));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }

                        } else {
                            if (semana_mes <= semana_final_captacion + 1) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.MONDAY)).concat("-").concat(String.valueOf(semana_mes - 1));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        }
                        break;
                    case 3://Martes
                        if (hay_miercoles_1ra_semana) {
                            if (semana_mes <= semana_final_captacion) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.TUESDAY)).concat("-").concat(String.valueOf(semana_mes));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        } else {
                            if (semana_mes <= semana_final_captacion + 1) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.TUESDAY)).concat("-").concat(String.valueOf(semana_mes - 1));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        }
                        break;
                    case 4://Miercoles
                        if (hay_miercoles_1ra_semana) {
                            if (semana_mes <= semana_final_captacion) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.WEDNESDAY)).concat("-").concat(String.valueOf(semana_mes));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        } else {
                            if (semana_mes <= semana_final_captacion + 1) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.WEDNESDAY)).concat("-").concat(String.valueOf(semana_mes - 1));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        }
                        break;
                    case 5://Jueves
                        if (hay_miercoles_1ra_semana) {
                            if (semana_mes <= semana_final_captacion) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.THURSDAY)).concat("-").concat(String.valueOf(semana_mes));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        } else {
                            if (semana_mes <= semana_final_captacion + 1) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.THURSDAY)).concat("-").concat(String.valueOf(semana_mes - 1));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        }
                        break;
                    case 6://Viernes
                        if (hay_miercoles_1ra_semana) {
                            if (semana_mes <= semana_final_captacion) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.FRIDAY)).concat("-").concat(String.valueOf(semana_mes));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        } else {
                            if (semana_mes <= semana_final_captacion + 1) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.FRIDAY)).concat("-").concat(String.valueOf(semana_mes - 1));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        }
                        break;
                    case 7://Sabado
                        if (hay_miercoles_1ra_semana) {
                            if (semana_mes <= semana_final_captacion) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.SATURDAY)).concat("-").concat(String.valueOf(semana_mes));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        } else {
                            if (semana_mes <= semana_final_captacion + 1) {
                                String dia_string = ((String) abrevDiasSemana().get(Calendar.SATURDAY)).concat("-").concat(String.valueOf(semana_mes - 1));
                                Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                                obj.setPlanning(byDayEquals);
                                obj.setDate(getFechaFormateada(dia));
                                salida.add(obj);
                            }
                        }
                        break;
                }
            } else {
                //Cuando aun no tengo al miercoles solo me interesa si viene miercoles para iniciar el calendario
                if (dia_semana == Calendar.WEDNESDAY) {
                    primer_miercoles_encontrado = true;
                    if (semana_mes == 1) {
                        hay_miercoles_1ra_semana = true;
                    }
                    String dia_string = ((String) abrevDiasSemana().get(Calendar.WEDNESDAY)).concat("-").concat(String.valueOf(1));
                    Planning byDayEquals = planningRepository.findByDayEquals(dia_string);
                    obj.setPlanning(byDayEquals);
                    obj.setDate(getFechaFormateada(dia));
                    salida.add(obj);
                }
            }
        }
        return salida;
    }

}
