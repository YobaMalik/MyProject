package com.example.demo.Form.RDForm;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;

public class TeeForm extends PipeElementForm {
    private double branchThickness;
    private double branchDiam;
    private double branchAddThickness=1;
    private double strengthReductionRate=1;

    public double getBranchThickness() {
        return branchThickness;
    }

    public void setBranchThickness(double branchThickness) {
        this.branchThickness = branchThickness;
    }

    public double getBranchDiam() {
        return branchDiam;
    }

    public void setBranchDiam(double branchDiam) {
        this.branchDiam = branchDiam;
    }

    public double getBranchAddThickness() {
        return branchAddThickness;
    }

    public void setBranchAddThickness(double branchAddThickness) {
        this.branchAddThickness = branchAddThickness;
    }

    public double getStrengthReductionRate() {
        return strengthReductionRate;
    }

    public void setStrengthReductionRate(double strengthReductionRate) {
        this.strengthReductionRate = strengthReductionRate;
    }

    @Override
    public void visitor(ElementsVisitor visitor) {

    }

    @Override
    public void chains() {

    }

}
