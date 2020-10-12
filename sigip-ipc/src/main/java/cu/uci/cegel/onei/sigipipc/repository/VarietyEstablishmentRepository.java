package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Establishment;
import cu.uci.cegel.onei.sigipipc.persistence.VarietyEstablishment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface VarietyEstablishmentRepository extends PagingAndSortingRepository<VarietyEstablishment, Long>, QuerydslPredicateExecutor<VarietyEstablishment> {
    int countByActiveIsTrue();
    List<VarietyEstablishment> findAllByIdIn(Collection<Long> ages);


    @Query(value = "SELECT DISTINCT\n" +
            "dvar_estab.id_destablecimiento\n" +
            "FROM \n" +
            "dvar_estab\n" +
            "JOIN\n" +
            "destablecimiento on dvar_estab.id_destablecimiento = destablecimiento.id_destablecimiento\n" +
            "--caracterist espec\n" +
            "\n" +
            "--la planificacion asociada al establecimiento\n" +
            "JOIN\n" +
            "destablecimiento_planificacion on destablecimiento.id_destablecimiento = destablecimiento_planificacion.id_destablecimiento\n" +
            "JOIN\n" +
            "nplanificacion on destablecimiento_planificacion.id_nplanificacion = nplanificacion.id_nplanificacion\n" +
            "--la fecha real en la que capta el establecimiento\n" +
            "JOIN\n" +
            "dplanificacion_fecha on nplanificacion.id_nplanificacion = dplanificacion_fecha.id_nplanificacion\n" +
            "--para mas datos de la tabla (moneda y el mercado) ojo aqui con los tipos de monedas por mercado\n" +
            "JOIN\n" +
            "nmercado on destablecimiento.id_nmercado = nmercado.id_nmercado\n" +
            "JOIN\n" +
            "nmercado_moneda on nmercado.id_nmercado = nmercado_moneda.id_nmercado\n" +
            "JOIN\n" +
            "nmoneda on nmercado_moneda.id_nmoneda = nmoneda.id_nmoneda\n" +
            "--para mas datos de la tabla (nombre de la variedad)\n" +
            "\n" +
            "--excluir las que ya se captaron este mes\n" +
            "WHERE dvar_estab.id_dvar_estab NOT IN\n" +
            "(\n" +
            "SELECT \n" +
            "--estos son la variedades captadas\n" +
            "dcaptacion_dvar_estab.id_dvar_estab \n" +
            "FROM \n" +
            "dcaptacion_dvar_estab\n" +
            "--fecha real en la que se capto esa variedad\n" +
            "JOIN\n" +
            "dcaptacion on dcaptacion.id_dcaptacion = dcaptacion_dvar_estab.id_dcaptacion\n" +
            "--las que ya se han captado en este mes\n" +
            "WHERE dcaptacion.fecha BETWEEN ?2 AND ?3\n" +
            ")\n" +
            "--planificadas para captar en lo que va de mes\n" +
            "AND dplanificacion_fecha.fecha BETWEEN ?2 AND  ?3\n" +
            "\n" +
            "AND destablecimiento.id_destablecimiento = ?1", nativeQuery = true)
    List<Integer> getVariedadesPenditesCaptar(@Param("establishmentId") Long establishmentId, @Param("start") LocalDate start, @Param("end") LocalDate end);


    @Query(value = "SELECT DISTINCT\n" +
            "dvar_estab.id_destablecimiento\n" +
            "FROM \n" +
            "dvar_estab\n" +
            "JOIN\n" +
            "destablecimiento on dvar_estab.id_destablecimiento = destablecimiento.id_destablecimiento\n" +
            "--caracterist espec\n" +
            "\n" +
            "--la planificacion asociada al establecimiento\n" +
            "JOIN\n" +
            "destablecimiento_planificacion on destablecimiento.id_destablecimiento = destablecimiento_planificacion.id_destablecimiento\n" +
            "JOIN\n" +
            "nplanificacion on destablecimiento_planificacion.id_nplanificacion = nplanificacion.id_nplanificacion\n" +
            "--la fecha real en la que capta el establecimiento\n" +
            "JOIN\n" +
            "dplanificacion_fecha on nplanificacion.id_nplanificacion = dplanificacion_fecha.id_nplanificacion\n" +
            "--para mas datos de la tabla (moneda y el mercado) ojo aqui con los tipos de monedas por mercado\n" +
            "JOIN\n" +
            "nmercado on destablecimiento.id_nmercado = nmercado.id_nmercado\n" +
            "JOIN\n" +
            "nmercado_moneda on nmercado.id_nmercado = nmercado_moneda.id_nmercado\n" +
            "JOIN\n" +
            "nmoneda on nmercado_moneda.id_nmoneda = nmoneda.id_nmoneda\n" +
            "--para mas datos de la tabla (nombre de la variedad)\n" +
            "\n" +
            "--excluir las que ya se captaron este mes\n" +
            "WHERE dvar_estab.id_dvar_estab NOT IN\n" +
            "(\n" +
            "SELECT \n" +
            "--estos son la variedades captadas\n" +
            "dcaptacion_dvar_estab.id_dvar_estab \n" +
            "FROM \n" +
            "dcaptacion_dvar_estab\n" +
            "--fecha real en la que se capto esa variedad\n" +
            "JOIN\n" +
            "dcaptacion on dcaptacion.id_dcaptacion = dcaptacion_dvar_estab.id_dcaptacion\n" +
            "--las que ya se han captado en este mes\n" +
            "WHERE dcaptacion.fecha BETWEEN ?1 AND ?2\n" +
            ")\n" +
            "--planificadas para captar en lo que va de mes\n" +
            "AND dplanificacion_fecha.fecha BETWEEN ?1 AND  ?2", nativeQuery = true)
    List<Integer> getVariedadesPenditesCaptarAll(@Param("start") LocalDate start, @Param("end") LocalDate end);
//    @Query(value="SELECT ve FROM VarietyEstablishment")
//    List<Integer> getVariedadesPenditesCaptar2(@Param("establishmentId") Long establishmentId,@Param("start") LocalDate start,@Param("end") LocalDate end);

    @Query(value = "SELECT DISTINCT destablecimiento_planificacion.id_destablecimiento FROM \n" +
            "dplanificacion_fecha\n" +
            "JOIN\n" +
            "nplanificacion on nplanificacion.id_nplanificacion = dplanificacion_fecha.id_nplanificacion\n" +
            "JOIN\n" +
            "destablecimiento_planificacion on nplanificacion.id_nplanificacion = destablecimiento_planificacion.id_nplanificacion\n" +
            "WHERE dplanificacion_fecha.fecha BETWEEN ?1 and ?2", nativeQuery = true)
    List<Integer> getEstablecimientosPlanificadosPara(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(value = "SELECT DISTINCT dcaptacion.id_establecimiento from dcaptacion WHERE\n" +
            "dcaptacion.fecha BETWEEN ?1 and ?2", nativeQuery = true)
    List<Integer> establecimientosCaptados(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(value = "SELECT DISTINCT dcaptacion.id_variedad from dcaptacion WHERE\n" +
            "dcaptacion.fecha BETWEEN ?1 and ?2", nativeQuery = true)
    List<Integer> variedadesCaptadas(@Param("start") LocalDate start, @Param("end") LocalDate end);


    //    List<VarietyEstablishment> getVarietyEstablishmentByIdIn(List<Long> ids);
//    List<VarietyEstablishment> getVarietyEstablishmentsByIdIn(List<Long> ids);
    @Query("SELECT e FROM VarietyEstablishment e WHERE e.id IN :ids")
    List<VarietyEstablishment> obtenerVarietyEstablishmentsByIdIn(@Param("ids") List<Long> ids);


    @Query(value = "SELECT dvar_estab.id_dvar_estab FROM dvar_estab WHERE activo = true order by id_dvar_estab ASC limit ?1 offset ?2", nativeQuery = true)
    List<Integer> varietyEstabPaging(@Param("size") int size, @Param("page") int page);

    List<VarietyEstablishment> findAllByEstablishment(Establishment establishment);

    List<VarietyEstablishment> findAllByEstablishmentIdAndActiveTrue(Long id);


    @Query(value = "SELECT count(dvar_estab.id_dvar_estab) as cant FROM dvar_estab\n" +
            "where \n" +
            "dvar_estab.activo = true\n" +
            "and \n" +
            "dvar_estab.id_destablecimiento IN (SELECT DISTINCT\n" +
            "\tdestablecimiento_planificacion.id_destablecimiento \n" +
            "FROM\t\n" +
            "\t dplanificacion_fecha \t\n" +
            "\tJOIN \n" +
            "\t nplanificacion ON nplanificacion.id_nplanificacion = dplanificacion_fecha.id_nplanificacion \n" +
            "\t\n" +
            "\tJOIN \n" +
            "\t destablecimiento_planificacion ON nplanificacion.id_nplanificacion = destablecimiento_planificacion.id_nplanificacion \t \n" +
            "WHERE\n" +
            "\tdplanificacion_fecha.fecha BETWEEN ?1 \n" +
            "\tAND ?2  )\n" +
            "and \n" +
            "dvar_estab.id_dvar_estab not in \n" +
            "(SELECT dcaptacion.id_variedad from dcaptacion\n" +
            "WHERE dcaptacion.fecha BETWEEN ?1 and ?2\n" +
            "\n" +
            ")", nativeQuery = true)
    Integer CantvariedadesCaptadas(@Param("start") LocalDate start, @Param("end") LocalDate end);

    //Para Agrupar las Var-Estab por planning en el Mercado Agropecuario
    @Query(value = "SELECT * FROM dvar_estab WHERE dvar_estab.id_destablecimiento = ?1 and dvar_estab.id_nestado = ?2 and dvar_estab.activo = ?3 and dvar_estab.id_variedad = ?4 and dvar_estab.id_unidad_medida = ?5 and dvar_estab.id_cantidad = ?6 ORDER BY dvar_estab.id_planificacion ASC", nativeQuery = true)
    List<VarietyEstablishment> agruparVarEstabByPlanning(@Param("estabId") long estabId, @Param("estadoId") long estadoId, @Param("active") boolean active, @Param("variedadId") long variedadId, @Param("unidadmedidaId") long unidadmedidaId, @Param("cantidadId") long cantidadId);



}
