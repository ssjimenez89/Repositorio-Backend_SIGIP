package cu.uci.cegel.onei.sigipipc;

import com.coxautodev.graphql.tools.SchemaParser;
import cu.uci.cegel.onei.sigipipc.kernel.graphql.SpringGraphqlScanner;
import cu.uci.cegel.security.annotations.EnableOauth2;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

//@EnableOauth2
@SpringBootApplication
@SuppressWarnings("uncheked")
public class SigipIpcApplication {

    @Autowired
    SpringGraphqlScanner springGraphqlScanner;

    public static void main(String[] args) {
        SpringApplication.run(SigipIpcApplication.class, args);
    }

    @Bean
    public GraphQLSchema graphQLSchema() {
        springGraphqlScanner.init();
        return SchemaParser.newParser()
                .resolvers(springGraphqlScanner.getResolvers())
                .scalars(springGraphqlScanner.getScalars())
                .schemaString(springGraphqlScanner.getGraphqlSchema())
                .build()
                .makeExecutableSchema();
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}