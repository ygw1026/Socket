package com.nhnacademy.server.method.parser;

import org.apache.commons.lang3.StringUtils;

public class MethodParser {
    
    public static MethodAndValue parse(String message) {

        if (StringUtils.isEmpty(message)) {
            return null;
        }

        String messages[] = message.split(" ");
        
        if(messages.length>0) {
            if (messages.length == 1) {
                return new MethodAndValue(messages[0], "");
            }
            String value = message.substring(messages[0].length());
            return new MethodAndValue(messages[0], value.trim());
        }
        return null;
    }

    public static class MethodAndValue{
        private final String method;
        private final String value;

        public MethodAndValue(String method, String value) {
            this.method = method;
            this.value = value;
        }

        public String getMethod() {
            return method;
        }

        public String getValue() {
            return value;
        }
    }
}
