package cu.uci.cegel.onei.sigipipc.kernel.graphql.scalars;

import cu.uci.cegel.onei.sigipipc.kernel.annotations.Scalar;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Scalar(name = "Email")
@Component
public class Email extends GraphQLScalarType {
    public Email() {
        super("Email", "A custom scalar that handles emails", new Coercing() {
            @Override
            public Object serialize(Object dataFetcherResult) {
                return serializeEmail(dataFetcherResult);
            }

            @Override
            public Object parseValue(Object input) {
                return parseEmailFromVariable(input);
            }

            @Override
            public Object parseLiteral(Object input) {
                return parseEmailFromAstLiteral(input);
            }
        });
    }


    private static boolean looksLikeAnEmailAddress(String possibleEmailValue) {
        // ps.  I am not trying to replicate RFC-3696 clearly
        return Pattern.matches("[A-Za-z0-9]@[.*]", possibleEmailValue);
    }

    private static Object serializeEmail(Object dataFetcherResult) {
        String possibleEmailValue = String.valueOf(dataFetcherResult);
        if (looksLikeAnEmailAddress(possibleEmailValue)) {
            return possibleEmailValue;
        } else {
            throw new CoercingSerializeException("Unable to serialize " + possibleEmailValue + " as an email address");
        }
    }

    private static Object parseEmailFromVariable(Object input) {
        if (input instanceof String) {
            String possibleEmailValue = input.toString();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseValueException("Unable to parse variable value " + input + " as an email address");
    }

    private static Object parseEmailFromAstLiteral(Object input) {
        if (input instanceof StringValue) {
            String possibleEmailValue = ((StringValue) input).getValue();
            if (looksLikeAnEmailAddress(possibleEmailValue)) {
                return possibleEmailValue;
            }
        }
        throw new CoercingParseLiteralException(
                "Value is not any email address : '" + input + "'"
        );
    }
}
