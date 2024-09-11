package com.nhnacademy.server.main.method.parser;

public class MethodParser {
    public static MethodAndValue parse(String message){
        String messages[] = message.split(" ");
        if(messages.length>=2){
            return new MethodAndValue(messages[0],messages[1]);
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