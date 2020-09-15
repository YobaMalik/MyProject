package com.example.demo.TensionCalcRD.Elements;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.Form.RDForm.TeeForm;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;

public class TeeCalculation extends AbstractTensionCalc {

    private double strengthReductionRate;
    private double branchThickness;
    private double branchDiam;
    private double branchAddThickness;

    private double branchCalcThickness;
    private double branchPress;

    public  TeeCalculation (TeeForm teeForm){
        strengthReductionRate = teeForm.getStrengthReductionRate();
        branchThickness = teeForm.getBranchThickness();
        branchDiam = teeForm.getBranchDiam();
        branchAddThickness = teeForm.getBranchAddThickness();

        setElemThickness(calcThickness(teeForm));
        setElemPressure(calcPressure(teeForm));

    }

    @Override
    public double getBranchThickness() {
        return branchThickness;
    }

    @Override
    protected double calcThickness(PipeElementForm pipeForm) {
        //double desPressure, double strValue, double outDiam
        branchCalcThickness=(pipeForm.getDesPress()*branchDiam)/
                (2*pipeForm.getWeldRate()*pipeForm.getAllowableStress()+
                        pipeForm.getDesPress());
        return (pipeForm.getDesPress()*pipeForm.getOutDiam())/
                (2*Math.min(pipeForm.getWeldRate(),strengthReductionRate)*pipeForm.getAllowableStress()+
                        pipeForm.getDesPress());

    }

    @Override
    protected double calcPressure(PipeElementForm pipeForm) {
        //double strValue, double outDiam, double eThickness
        double press1 = 2*pipeForm.getAllowableStress()*Math.min(pipeForm.getWeldRate(),strengthReductionRate)*
                (pipeForm.getThickness()-pipeForm.getAddThickness())/
                (pipeForm.getOutDiam()-(pipeForm.getThickness()-pipeForm.getAddThickness()));

        branchPress = 2*pipeForm.getWeldRate()*pipeForm.getAllowableStress()*(branchThickness-branchAddThickness)/
                (branchDiam-(branchThickness-branchAddThickness));
     //   return Math.min(press1,press2);
        return press1;

    }

    public double getBranchPress() {
        return branchPress;
    }

    public double getBranchCalcThickness() {
        return branchCalcThickness;
    }

    public void setBranchCalcThickness(double branchCalcThickness) {
        this.branchCalcThickness = branchCalcThickness;
    }
}