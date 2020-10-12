package cu.uci.cegel.onei.sigipbase.infrastructure.util;

public abstract class UtilValidacion {

    /**
     * @param id numero que se desea validar
     * @return Devuelve verdadero si el parámetro number es mayor que 0 o falso si el es null o menor igual que 0
     */
    public static boolean isAValidIdentification(Long id) {
        return (id != null && id > 0L);
    }

}
