package com.example.demo.Form.RDForm;

import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public class CrossForm extends TeeForm {
    private double lengthBetweenBranches;

    @Override
    public void visitor(ElementsVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void chains() {
    }

    public double getLengthBetweenBranches() {
        return lengthBetweenBranches;
    }

    public void setLengthBetweenBranches(double lengthBetweenBranches) {
        this.lengthBetweenBranches = lengthBetweenBranches;
    }

}