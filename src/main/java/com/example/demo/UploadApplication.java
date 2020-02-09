package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;



@SpringBootApplication
public class UploadApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UploadApplication.class, args);
	//	Process prc=Runtime.getRuntime().exec(new String[]{"shutdown","now"});
	//	System.exit(0);
	}

}
