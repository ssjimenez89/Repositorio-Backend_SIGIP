package cu.uci.cegel.onei.sigipipc.repository;

import cu.uci.cegel.onei.sigipipc.persistence.Classifier;
import cu.uci.cegel.onei.sigipipc.persistence.ClassifierType;
import cu.uci.cegel.onei.sigipipc.persistence.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Repository
public interface ClassifierRepository extends PagingAndSortingRepository<Classifier, Long>, QuerydslPredicateExecutor<Classifier> {
    Classifier findByDescriptionEquals(String description);

    //    List<Classifier> findByClassifierTypeEqualsAndCode(int tipo_class);
    List<Classifier> findAllByClassifierTypeEquals(ClassifierType tipoClassifier);

    Page<Classifier> findAllByClassifierTypeEquals(ClassifierType tipoClassifier, Pageable page);

    @Query(value = "SELECT distinct nclasificador.id_nclasificador FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad\n" +
            "FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) and nclasificador.id_nclasificador not in ?2  order by id_nclasificador ASC limit ?3 offset ?4 ", nativeQuery = true  )
    List<Integer> classifierByMarketExcludingClassifier( @Param("marketId") Long marketId, @Param("classifierComparatorId") List<Long> classifierComparatorId, @Param("size") int size,  @Param("page") int page);

    @Query(value = "SELECT distinct nclasificador.id_nclasificador FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) order by id_nclasificador ASC limit ?2 offset ?3 ", nativeQuery = true  )
    List<Integer> classifierByMarket( @Param("marketId") Long marketId, @Param("size") int size,  @Param("page") int page);

    @Query(value = "SELECT count (distinct nclasificador.id_nclasificador) FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad\n" +
            "FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) and nclasificador.id_nclasificador not in ?2", nativeQuery = true  )
    Integer totalClassifierByMarketExcludingClassifier( @Param("marketId") Long marketId, @Param("classifierComparatorId") List<Long> classifierComparatorId);

    @Query(value = "SELECT count (distinct nclasificador.id_nclasificador) FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad\n" +
            "FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true ", nativeQuery = true  )
    Integer totalClassifierByMarket( @Param("marketId") Long marketId);

    Integer findAllByActive(Boolean b);
//    List<Classifier> findAllByClassifierTypeAndMarketsEquals(ClassifierType tipoClassifier, Market market);
//
//    Page<Classifier> findAllByClassifierTypeAndMarketsEquals(ClassifierType tipoClassifier, Market market, Pageable page);

    List<Classifier> findAllByParentEquals(Classifier id);

    List<Classifier> findAllByParentNull(Sort sort);

    Integer countAllByDescriptionStartsWith(String description);

    @Query(value = "SELECT distinct nclasificador.id_nclasificador FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and nclasificador.descripcion ILIKE CONCAT ('%',?5,'%') and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad\n" +
            "FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) and nclasificador.id_nclasificador not in ?2  order by id_nclasificador ASC limit ?3 offset ?4 ", nativeQuery = true  )
    List<Integer> filterByDescriptionOfClassifierByMarketExcludingClassifier( @Param("marketId") Long marketId, @Param("classifierComparatorId") List<Long> classifierComparatorId, @Param("size") int size,  @Param("page") int page, @Param("descripcion") String descripcion);

    @Query(value = "SELECT distinct nclasificador.id_nclasificador FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and nclasificador.descripcion ILIKE CONCAT ('%',?4,'%') and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) order by id_nclasificador ASC limit ?2 offset ?3 ", nativeQuery = true  )
    List<Integer> filterByDescriptionOfClassifierByMarket( @Param("marketId") Long marketId, @Param("size") int size,  @Param("page") int page, @Param("descripcion") String descripcion);

    @Query(value = "SELECT count (distinct nclasificador.id_nclasificador) FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and nclasificador.descripcion ILIKE CONCAT ('%',?3,'%') and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad\n" +
            "FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) and nclasificador.id_nclasificador not in ?2", nativeQuery = true  )
    Integer totalFilterByDescriptionOfClassifierByMarketExcludingClassifier( @Param("marketId") Long marketId, @Param("classifierComparatorId") List<Long> classifierComparatorId, @Param("descripcion") String descripcion);

    @Query(value = "SELECT count (distinct nclasificador.id_nclasificador) FROM public.nclasificador join public.nclasificador_mercado_moneda  on nclasificador_mercado_moneda.id_nclasificador = nclasificador.id_nclasificador join public.nmercado_moneda on nclasificador_mercado_moneda.id_nmercado_moneda = nmercado_moneda.id_nmercado_moneda WHERE nclasificador.id_tipoclasificador = 6 and  nclasificador.id_nclasificador in (SELECT dvar_caract_espec.id_nvariedad\n" +
            "FROM \n" +
            "  public.dvar_caract_espec\n" +
            "  where \n" +
            "  dvar_caract_espec.activo = true\n" +
            "  group by dvar_caract_espec.id_nvariedad) and nmercado_moneda.id_nmercado_moneda = ?1 and nclasificador.activo = true and nclasificador.descripcion ILIKE CONCAT ('%',?2,'%')", nativeQuery = true  )
    Integer totalFilterByDescriptionOfClassifierByMarket( @Param("marketId") Long marketId, @Param("descripcion") String descripcion);

}
