package com.almybaties.parsing;

import java.util.Properties;

/**
 * Property Parse : handle properties with variables
 * @author  adonai
 */
public class AlPropertyParser {

    /**
     * class full qualify name
     */
    private static final String KEY_PREFIX = "com.almybaties.parsing.AlPropertyParser.";

    /**
     * The special property key that indicate whether enable a default value on placeholder.
     * <p>
     *   The default value is {@code false} (indicate disable a default value on placeholder)
     *   If you specify the {@code true}, you can specify key and default value on placeholder (e.g. {@code ${db.username:postgres}}).
     * </p>
     * @since 3.4.2
     */
    public static final String KEY_ENABLE_DEFAULT_VALUE = KEY_PREFIX + "enable-default-value";

    /**
     * The special property key that specify a separator for key and default value on placeholder.
     * <p>
     *   The default separator is {@code ":"}.
     * </p>
     * @since 3.4.2
     */
    public static final String KEY_DEFAULT_VALUE_SEPARATOR = KEY_PREFIX + "default-value-separator";

    //enable default value
    private static final String ENABLE_DEFAULT_VALUE = "false";
    //the separator
    private static final String DEFAULT_VALUE_SEPARATOR = ":";

    private AlPropertyParser(){
        //prevent instantiation
    }


    public static String parse(String string , Properties variables){
        VariableTokenHandler  handler = new VariableTokenHandler(variables);
        AlGenericTokenParser parser = new AlGenericTokenParser("${","}",handler);
        return parser.parse(string);
    }

    private static class VariableTokenHandler implements AlTokenHandler{

        private final Properties variables;
        private final boolean enableDefaultValue;
        private final String defaultValueSeparator;

        private VariableTokenHandler(Properties variables){
            this.variables = variables;
            this.enableDefaultValue = Boolean.parseBoolean(getPropertyValue(KEY_ENABLE_DEFAULT_VALUE,ENABLE_DEFAULT_VALUE));
            this.defaultValueSeparator = getPropertyValue(KEY_DEFAULT_VALUE_SEPARATOR,DEFAULT_VALUE_SEPARATOR);
        }

        private String getPropertyValue(String key,String defaultValue){
            return (variables == null)? defaultValue: variables.getProperty(key,defaultValue);
        }

        @Override
        public String handleToken(String content) {


            return null;
        }
    }


}
