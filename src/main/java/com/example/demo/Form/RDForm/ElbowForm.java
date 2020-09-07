package com.example.demo.Form.RDForm;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public class ElbowForm extends PipeElementForm {


    private double radius;
    private String elbowType;

    @Override
    public void visitor(ElementsVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void chains() {

    }

    public String getElbowType() {
        return elbowType;
    }

    public void setElbowType(String elbowType) {
        this.elbowType = elbowType;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
