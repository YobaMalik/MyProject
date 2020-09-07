package com.example.demo.Form.RDForm;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public class ReduceForm extends PipeElementForm {
    private double eBThickness;
    private double eSThickness;
    private double outBDiam;
    private double outSDiam;
    private double reducerType;
    private double lenght;

    @Override
    public void visitor(ElementsVisitor visitor) {

    }

    @Override
    public void chains() {

    }
    public double getBThickness() {
        return eBThickness;
    }

    public void setBThickness(double eBThickness) {
        this.eBThickness = eBThickness;
    }

    public double getSThickness() {
        return eSThickness;
    }

    public void setSThickness(double eSThickness) {
        this.eSThickness = eSThickness;
    }

    public double getOutBDiam() {
        return outBDiam;
    }

    public void setOutBDiam(double outBDiam) {
        this.outBDiam = outBDiam;
    }

    public double getOutSDiam() {
        return outSDiam;
    }

    public void setOutSDiam(double outSDiam) {
        this.outSDiam = outSDiam;
    }

    public double getReducerType() {
        return reducerType;
    }

    public void setReducerType(String reducerType) {
        this.reducerType = reducerType.equals("Концентрический")?2:1;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

}
