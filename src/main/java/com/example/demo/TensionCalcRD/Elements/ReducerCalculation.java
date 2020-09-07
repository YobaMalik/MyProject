package com.example.demo.TensionCalcRD.Elements;

import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.Form.RDForm.ReduceForm;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;

import java.util.HashMap;
import java.util.Map;

public class ReducerCalculation extends AbstractTensionCalc {

    private double degree;
    private double sThickness;
    private Map<String,Double> map=new HashMap<>();


    public  ReducerCalculation(ReduceForm pipeForm){

        map.put("eBThickness", pipeForm.getBThickness());
        map.put("eSThickness", pipeForm.getSThickness());
        degree=Math.atan((pipeForm.getOutBDiam()- pipeForm.getOutSDiam())/
                pipeForm.getReducerType()/pipeForm.getLenght());

        setElemThickness(calcThickness(pipeForm));
        sThickness=calcThickness(pipeForm);

        setElemPressure(calcPressure(pipeForm));

    }

    @Override
    public double getBranchThickness() {
        return sThickness;
    }

    @Override
    protected double calcThickness(PipeElementForm pipeForm) {
        // desPressure,  strValue,  outDiam
        return pipeForm.getDesPress()* pipeForm.getOutDiam()/
                (2* pipeForm.getWeldRate()* pipeForm.getAllowableStress()*Math.cos(degree)
                        + pipeForm.getDesPress());
    }

    @Override
    protected double calcPressure(PipeElementForm pipeForm) {
        // strValue,  outDiam,  eThickness
        return 2* pipeForm.getWeldRate()* pipeForm.getAllowableStress()*Math.cos(degree)
                *(map.get("eSThickness")- pipeForm.getAddThickness())/
                (pipeForm.getOutDiam()-map.get("eBThickness")+pipeForm.getAddThickness());
    }

}
