package cu.uci.cegel.onei.sigipbase.infrastructure.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class UtilFecha {

    /**
     * @param fecha   fecha de tipo string
     * @param formato El formato con que se deseas formatera la fecha a partir de la constante DateTimeFormatter
     * @return Devuelve la fecha como LocalDateTime
     */
    public static LocalDate convertir(String fecha, DateTimeFormatter formato) {
        return LocalDate.parse(fecha, formato);
    }

    /**
     * @param fecha   fecha de tipo LocalDate
     * @param formato El formato con que se deseas formatera la fecha a partir de la constante DateTimeFormatter
     * @return Devuelve la fecha como String
     */
    public static String convertir(LocalDate fecha, DateTimeFormatter formato) {
        return fecha.format(formato);
    }

    /**
     * @param fecha fecha de tipo Date a convertir a LocalDateTime
     * @return Devuelve la fecha convertida a LocalDateTime
     */
    public static LocalDateTime convertirToLocalDateTime(Date fecha) {
        return LocalDateTime.ofInstant(fecha.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate convertirToLocalDate(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @param fecha fecha de tipo LocalDateTime a convertir a Date
     * @return Devuelve la fecha convertida a Date
     */
    public static Date convertir(LocalDateTime fecha) {
        return Date.from(fecha.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertir(LocalDate fecha) {
        return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime convertirToLocalDateTime(String fecha, DateTimeFormatter formato) {
        return LocalDateTime.parse(fecha, formato);
    }

    public static boolean isValid(String fecha, DateTimeFormatter formato) {
        try {
            convertir(fecha, formato);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    public static String convertirLocalDateTimeHora(LocalDateTime fecha, DateTimeFormatter formato) {
        return fecha.format(formato);
    }
}
