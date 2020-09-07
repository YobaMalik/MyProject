package com.example.demo.Form.Abstract;

import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public abstract class PipeElementForm {
    private double desPress;
    private double desTemp;
    private double allowableStress;
    private double outDiam;
    private double thickness;
    private double weldRate=1;
    private double addThickness=1;

    public abstract void visitor(ElementsVisitor visitor);
    public abstract void chains();

    public double getDesPress() {
        return desPress;
    }

    public void setDesPress(double desPress) {
        this.desPress = desPress;
    }

    public double getDesTemp() {
        return desTemp;
    }

    public void setDesTemp(double desTemp) {
        this.desTemp = desTemp;
    }

    public double getAllowableStress() {
        return allowableStress;
    }

    public void setAllowableStress(double allowableStress) {
        this.allowableStress = allowableStress;
    }

    public double getOutDiam() {
        return outDiam;
    }

    public void setOutDiam(double outDiam) {
        this.outDiam = outDiam;
    }

    public double getThickness() {
        return thickness;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public double getWeldRate() {
        return weldRate;
    }

    public void setWeldRate(double weldRate) {
        this.weldRate = weldRate;
    }

    public double getAddThickness() {
        return addThickness;
    }

    public void setAddThickness(double c2,double c11,double c12) {
        double c1=c11+c12;
        this.addThickness=c1+c2;
    }
}
