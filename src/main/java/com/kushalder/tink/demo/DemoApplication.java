package com.kushalder.tink.demo;

import com.kushalder.tink.demo.service.Aead;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args){SpringApplication.run(DemoApplication.class, args);}

}
