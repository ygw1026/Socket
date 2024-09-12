package com.nhnacademy.server.main.method.response.impl;

import com.nhnacademy.server.main.method.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class PortResponse implements Response {
    private final static String METHOD = "port";

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String execute(String value) {
        StringBuilder sb = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("lsof -n -i -P");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null){
                if(line.contains("(LISTEN)")){
                    String[] tokens = line.trim().split("\\s+");
                    String protocal = tokens[7];
                    String port = tokens[8];
                    String result = String.format("%s %s %s", protocal, port, System.lineSeparator());
                    log.debug("result:{}",result);
                    if(StringUtils.isEmpty(value)){
                        sb.append(result);
                    }else if( StringUtils.isNotEmpty(value) && port.contains(value)){
                        sb.append(result);
                    }
                }//end if
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
