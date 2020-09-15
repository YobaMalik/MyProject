package com.example.demo.TensionCalcRD.Elements;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.Form.RDForm.CrossForm;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;

public class CrossCalculation extends AbstractTensionCalc {
    //calc accordint to RD 10-249-98 and GOST 32388-2013

    private double branchCalcTickness;
    private double strengthReductionRate;
    private double branchThickness;
    private double branchDiam;
    private double branchAddThickness=1;

    public  CrossCalculation(CrossForm cross) {

        if (checkCross(cross))
        {
            getStrengthReductionRate(cross);
            branchThickness = cross.getBranchThickness();
            branchDiam = cross.getBranchDiam();
            branchAddThickness = cross.getBranchAddThickness();
            setElemPressure(calcPressure(cross));
            setElemThickness(calcThickness(cross));
            branchCalcTickness=super.calcThickness(cross);
        }

    }

    private void getStrengthReductionRate(CrossForm cross) {
        double z=cross.getBranchDiam()/Math.sqrt((cross.getOutDiam()-cross.getThickness())
                *(cross.getThickness()-cross.getAddThickness()));
        double fiMin=(cross.getLengthBetweenBranches()-cross.getBranchDiam())/
                cross.getLengthBetweenBranches();
        this.strengthReductionRate= (2*(1-fiMin)+z*fiMin)/
                (2*(1+z)-(2+z)*fiMin);
    }



    private boolean checkCross(CrossForm cross) {
        boolean b=cross.getBranchDiam()>=100?cross.getLengthBetweenBranches()>=cross.getBranchDiam()+100:
                cross.getLengthBetweenBranches()>=cross.getBranchDiam()+50;
        System.out.println();
        return b && 2 * Math.sqrt((cross.getOutDiam() - cross.getThickness())
                * (cross.getThickness() - cross.getAddThickness())) <
                cross.getLengthBetweenBranches();

    }

    @Override
    public double getBranchThickness() {
        return branchCalcTickness;
    }

    @Override
    protected double calcThickness(PipeElementForm pipeForm) {
        //double desPressure, double strValue, double outDiam
        return (pipeForm.getDesPress()*pipeForm.getOutDiam())/
                (2*Math.min(pipeForm.getWeldRate(),strengthReductionRate)*pipeForm.getAllowableStress()+
                        pipeForm.getDesPress());
    }

    @Override
    protected double calcPressure(PipeElementForm pipeForm) {
        //double strValue, double outDiam, double eThickness
        double press1 = 2*pipeForm.getAllowableStress()*
                Math.min(pipeForm.getWeldRate(),strengthReductionRate)*
                (pipeForm.getThickness()-pipeForm.getAddThickness())/
                (pipeForm.getOutDiam()-(pipeForm.getThickness()-pipeForm.getAddThickness()));

        double press2 = 2*pipeForm.getWeldRate()*pipeForm.getAllowableStress()
                *(branchThickness-branchAddThickness)/
                (branchDiam-(branchThickness-branchAddThickness));
        return Math.min(press1,press2);
    }

}