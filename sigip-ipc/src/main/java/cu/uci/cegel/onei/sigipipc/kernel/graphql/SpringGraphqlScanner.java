package cu.uci.cegel.onei.sigipipc.kernel.graphql;

import com.coxautodev.graphql.tools.GraphQLResolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Resolver;
import cu.uci.cegel.onei.sigipipc.kernel.annotations.Scalar;
import graphql.schema.GraphQLScalarType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class SpringGraphqlScanner {

    private List<GraphQLResolver<Void>> resolvers = new LinkedList<>();
    private List<GraphQLScalarType> scalars = new LinkedList<>();
    private List<String> graphqlFiles = new LinkedList<>();

    @Value("${graphql.files}")
    private String pathResources;
    @Value("${project.package.name}")
    String scanPackage;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;
    @Autowired
    private ResourceLoader resourceLoader;

    public void init() {
        this.findAnnotatedClasses(this.scanPackage);
        this.getResourceFolderFiles();
    }

    public List<GraphQLResolver<Void>> getResolvers() {
        return resolvers;
    }

    public GraphQLScalarType[] getScalars() {
        return scalars.stream().toArray(GraphQLScalarType[]::new);
    }

    public String getGraphqlSchema() {
        return this.graphqlFiles.stream().collect(Collectors.joining(""));
    }

    private void findAnnotatedClasses(String scanPackage) {
        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();
        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            printMetadata(beanDef);
        }
    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Resolver.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(Scalar.class));
        return provider;
    }

    private void printMetadata(BeanDefinition beanDef) {
        try {
            Class<?> cl = Class.forName(beanDef.getBeanClassName());
            if (beanDef.getBeanClassName().contains("resolvers")) {
                Object newInstance = cl.newInstance();
                beanFactory.autowireBean(newInstance);
                Resolver findable = cl.getAnnotation(Resolver.class);
                resolvers.add((GraphQLResolver<Void>) newInstance);
                log.info("Clase: {}, con metadato name: {} se ha registrado como GraphqlResolver",
                        cl.getSimpleName(), findable.name());
            } else {
                Scalar findable = cl.getAnnotation(Scalar.class);
                log.info("Clase: {}, con metadato name: {} se ha registrado como GraphqlScalar",
                        cl.getSimpleName(), findable.name());
                scalars.add((GraphQLScalarType) cl.newInstance());
            }


        } catch (Exception e) {
            log.error("Got exception: " + e.getMessage());
        }
    }

    private void getResourceFolderFiles() {
        Resource[] resources = this.loadResources("classpath*:" + this.pathResources + "/**/*.graphqls");
        List<String> collect = Stream.of(resources).map(el -> {
            String schemaFile = "";
            try {
                schemaFile = StreamUtils.
                        copyToString(el.getInputStream(), Charset.defaultCharset());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return schemaFile;
        }).collect(Collectors.toList());
        this.graphqlFiles.addAll(collect);
    }

    private Resource[] loadResources(String pattern) {
        Resource[] resources = {};
        try {
            resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }
}

