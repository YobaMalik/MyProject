package com.example.demo.TensionCalcASME.ITensionASME;

public interface Interpolation {
    default double interpolation(double t1,double t2,double ft1, double ft2, double tn){
        double value=0;
        double b=(t1*ft2-t2*ft1)/(t1-t2);
        double a=(ft1-b)/t1;
        value=tn*a+b;
        return value;
    }
}
