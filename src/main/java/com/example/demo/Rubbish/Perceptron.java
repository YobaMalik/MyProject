package com.example.demo.Rubbish;

import java.util.Random;

public class Perceptron {
private double[] weights;
private double threshold;
int[] outputs;
public void study(double[][] inputs,int[] outputs, double threshold, double lrate,double epoch) {
	//this.weight=input;
	

	this.threshold=threshold;
	int p = outputs.length;
	 int n = inputs[0].length;
this.outputs=outputs;
	 weights = new double[n];
	 Random r = new Random();
	 
	 for(int i=0;i<n;i++)
     {
		// System.out.println(r.nextDouble());
         weights[i] = r.nextDouble();
     }

	
	 for(int i=0;i<epoch;i++)
     {
         int totalError = 0;
         for(int j =0;j<p;j++)
         {
     
             int error = outputs[j] - Output(inputs[j]);
             
             totalError +=error;
            
             for(int k = 0;k<n;k++)
             {
                 double delta = lrate * inputs[j][k] * error;
                 weights[k] += delta;
             }
             
             
         }
         if(totalError == 0)
             break;
     }
 }


public int Output(double[] input)
{
    double sum = 0.0;
    for(int i=0;i<input.length;i++)
    {
        sum += weights[i]*input[i];
    }
    if(sum>threshold)
    	return 1;
    else
        return 0;

}
}
