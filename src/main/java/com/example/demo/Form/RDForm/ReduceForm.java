package com.example.demo.Form.RDForm;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public class ReduceForm extends PipeElementForm {
    private double eBThickness;
    private double eSThickness;
    private double outBDiam;
    private double outSDiam;
    private String reducerType;
    private double length;

    @Override
    public void visitor(ElementsVisitor visitor) {

    }

    @Override
    public void chains() {

    }
    public double geteBThickness() {
        return eBThickness;
    }

    public void seteBThickness(double eBThickness) {
        this.eBThickness = eBThickness;
    }

    public double geteSThickness() {
        return eSThickness;
    }

    public void seteSThickness(double eSThickness) {
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

    public String getReducerType() {
        return reducerType;
    }

    public void setReducerType(String reducerType) {
        this.reducerType = reducerType;
    }

    public double getLength() {
        return length;
    }

    public void setLenght(double lenght) {
        this.length = lenght;
    }

}
