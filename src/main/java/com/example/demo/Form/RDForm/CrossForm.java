package com.example.demo.Form.RDForm;

import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public class CrossForm extends TeeForm {
    private double lengthBetweenBranches;

    @Override
    public void visitor(ElementsVisitor visitor) {
        // TODO Auto-generated method stub
        visitor.visit(this);
    }

    @Override
    public void chains() {
        // TODO Auto-generated method stub

    }

    public double getLengthBetweenBranches() {
        return lengthBetweenBranches;
    }

    public void setLengthBetweenBranches(double lengthBetweenBranches) {
        this.lengthBetweenBranches = lengthBetweenBranches;
    }

}