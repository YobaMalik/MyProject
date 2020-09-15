package com.example.demo.CalculationThickness;

import com.example.demo.Interface.ConvertString;

import java.text.ParseException;


public class PipeCalculation implements ConvertString {
    public  double getPipePressure(String desPressure,String desTemp, String strValue,String outDiam, String eThickness) throws ParseException {
        double addThickness=1.0;
        double weldRate=1.0;
        double calcPressure=(2*this.conv(strValue)*weldRate*(this.conv(eThickness)-addThickness))/
                (this.conv(outDiam)-(this.conv(eThickness)-addThickness));
        return calcPressure;
    }

    public  double getPipeThickness(String desPressure,String desTemp, String strValue,String outDiam, String eThickness) throws ParseException {
        double weldRate=1.0;
        double calcThickness=(this.conv(desPressure)*this.conv(outDiam))/
                (2*weldRate*this.conv(strValue)+this.conv(desPressure));
        return calcThickness;
    }

    private double interpolation(double value) {

        return 0;
    }
    public  double getElbowThickness(String desPressure,String desTemp, String strValue,String outDiam, String eThickness) throws ParseException {

        double addThickness=1.0;
        double weldRate=1.0;
        double coefKi;
        double radCurva=1.5*this.conv(outDiam);

        if(radCurva>1.0&&radCurva<2.0) {
            coefKi=this.interpolation(radCurva);
        } else {
            coefKi=(radCurva<=1.0)?1.3:(radCurva>=2.0)?1.0:-100;
        }

        double sR=(this.conv(desPressure)*this.conv(outDiam))/
                (2*weldRate*this.conv(strValue)+this.conv(desPressure));


        return 0;
    }

    public  double getElbowPressure(String desPressure,String desTemp, String strValue,String outDiam, String eThickness) {
        return 0;

    }
}
