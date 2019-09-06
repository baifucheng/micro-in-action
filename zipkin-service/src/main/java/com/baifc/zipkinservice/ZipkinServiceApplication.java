package com.baifc.zipkinservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinServiceApplication.class, args);
    }

}
