package com.negocio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ 
	"com.negocio.impl",
	"com.negocio.entity",
	"com.negocio.controller",
	"com.negocio.reglas",
	"com.negocio.repository",
	"com.negocio.dto",
	"com.negocio.excepcion",
	"com.negocio.util"} )
public class CallCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallCenterApplication.class, args);
	}
}
