package cu.uci.cegel.onei.sigipipc.kernel.graphql.scalars;

import cu.uci.cegel.onei.sigipipc.kernel.annotations.Scalar;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.stereotype.Component;

@Scalar(name = "Date")
@Component
public class Date extends GraphQLScalarType {
    public Date() {
        super("Date", "Date scalar", ExtendedScalars.Date.getCoercing());
    }
}
