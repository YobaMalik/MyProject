package com.example.demo.TensionCalcRD.Elements;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.Form.RDForm.ReduceForm;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;

import java.util.HashMap;
import java.util.Map;

public class ReducerCalculation extends AbstractTensionCalc {
    private double branchThickness;

    private double degree;
    private double outBDiam;
    private double outSDiam;
    private Map<String,Double> map=new HashMap<>();


    public  ReducerCalculation(ReduceForm pipeForm){
        map.put("eBThickness", pipeForm.geteBThickness());
        map.put("eSThickness", pipeForm.geteSThickness());

        double reducerType= pipeForm.getReducerType().equals("Концентрический")?2:1;
        degree=Math.atan((pipeForm.getOutBDiam()- pipeForm.getOutSDiam())/
                reducerType/pipeForm.getLength());
        outBDiam=pipeForm.getOutBDiam();
        outSDiam=pipeForm.getOutSDiam();

        setElemThickness(calcThickness(pipeForm));
        setElemPressure(calcPressure(pipeForm));
    }

    @Override
    public double getBranchThickness() {
        return branchThickness;
    }

    @Override
    protected double calcThickness(PipeElementForm pipeForm) {
        // desPressure,  strValue,  outDiam
        branchThickness=pipeForm.getDesPress()* outSDiam/
                (2* pipeForm.getWeldRate()* pipeForm.getAllowableStress()*Math.cos(degree)
                        + pipeForm.getDesPress());

        return pipeForm.getDesPress()* outBDiam/
                (2* pipeForm.getWeldRate()* pipeForm.getAllowableStress()*Math.cos(degree)
                        + pipeForm.getDesPress());
    }

    @Override
    protected double calcPressure(PipeElementForm pipeForm) {

        // strValue,  outDiam,  eThickness
        return 2* pipeForm.getWeldRate()* pipeForm.getAllowableStress()*Math.cos(degree)
                *(map.get("eSThickness")- pipeForm.getAddThickness())/
                (outBDiam-map.get("eBThickness")+pipeForm.getAddThickness());
    }

}
