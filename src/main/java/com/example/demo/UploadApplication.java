package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UploadApplication  {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UploadApplication.class, args);
		//	Process prc=Runtime.getRuntime().exec(new String[]{"shutdown","now"});
		//	System.exit(0);
	/*	PipeCalculation pipe=new PipeCalculation(10,120,50);
		System.out.println(pipe.getPipeThickness());
		System.out.println(pipe.getPipePressure());

	 */
	/*

	(double desPressure, double strValue,double outDiam,double thickness, double radius,String elbowType)
	ElbowForm pipe=new ElbowForm();

	AbstractTensionCalc test1=new ElementsVisitor().visit(pipe);
	System.out.print(test1.getClass());

	 */
	//тест
	}
}

//chdir C:\Users\Yoba\IdeaProjects\Upload v18 + react\src\main\resources\static
//npm run dev
//npm run build
