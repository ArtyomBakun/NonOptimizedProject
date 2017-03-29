package com.arba.common.controller;

import com.arba.common.leak.MemLeak;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/welcome")
public class MyController
{

    @RequestMapping(method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<String> printWelcome() {
        return new ResponseEntity<>("{\"hui\":\"hoj\"}" , HttpStatus.OK);
    }

    @RequestMapping(value = "/memleak/{times}", method = RequestMethod.GET, produces= MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String methodWithMemoryLeak(@PathVariable Long times) {
        try {
            Map map = System.getProperties();

            for(int i = 0 ; i < times; i++) {
                map.put(new MemLeak("key"), "value");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @RequestMapping(value = "/heavymethod/{dim}", method = RequestMethod.GET, produces= MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    String heavyMethod(@PathVariable Integer dim) {
        String response = "";
        double[] arr = new double[dim];
        Random r = new Random();
        for (int i = 0; i < dim; i++) {
            arr[i] = r.nextDouble();
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if(arr[i] < arr[j]){
                    double tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        for (int i = 0; i < dim; i++) {
            response += arr[i];
        }
        return "ok";
    }
}
