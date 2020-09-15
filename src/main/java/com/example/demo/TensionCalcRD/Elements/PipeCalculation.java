package com.example.demo.TensionCalcRD.Elements;

import com.example.demo.Form.RDForm.PipeForm;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;


public class PipeCalculation extends AbstractTensionCalc {


    public PipeCalculation(PipeForm pipeform){
        setElemThickness(calcThickness(pipeform));
        setElemPressure(calcPressure(pipeform));
    }

    public boolean checkPipeType(double outDiam, double g1, double g2,double strValue, double nubl){
        return !(getElemThickness() > Math.sqrt(outDiam * (0.375 * g1 + 0.546 * g2) / strValue / nubl));
    }


    @Override
    public double getBranchThickness() {
        return 0;
    }
}
