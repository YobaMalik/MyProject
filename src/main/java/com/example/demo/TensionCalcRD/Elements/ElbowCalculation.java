package com.example.demo.TensionCalcRD.Elements;

import com.example.demo.Form.RDForm.ElbowForm;
import com.example.demo.Form.Abstract.PipeElementForm;
import com.example.demo.Interface.ConvertString;
import com.example.demo.TensionCalcASME.ITensionASME.Interpolation;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;

public class ElbowCalculation extends AbstractTensionCalc implements ConvertString, Interpolation {
    private double ki;

    public ElbowCalculation(ElbowForm elbowForm){
        ki=ki(elbowForm);
        setElemThickness(super.calcThickness(elbowForm)*ki);
        setElemPressure(calcPressure(elbowForm));
    }


    private double ki(ElbowForm elbowForm){
        double outDiam = elbowForm.getOutDiam();
        double radius = elbowForm.getRadius()==0?elbowForm.getOutDiam()*1.5:elbowForm.getRadius();
        String elbowType = elbowForm.getElbowType();
        double weldRate= elbowForm.getWeldRate();
        double ki=1;

        assert elbowType != null;
        if(elbowType.equals("Гнутый")){
            if (radius/outDiam>=2.0) ki=1.0;
            if (radius/outDiam<=1.0) ki=1.3;
            if (radius/outDiam<2.0&& radius/outDiam>1.0) ki=interpolation(2.0,1.0,
                    1.0,1.3,radius/outDiam);
        }

        if(elbowType.equals("Штампосварной со швом на нейтральной линией")) {
            ki=Math.max(1/weldRate,(4*radius/(outDiam-getElemThickness())-1)/
                    (4*radius/(outDiam-getElemThickness())-2)
            );
        }
//resident sleeper elbows
        if(elbowType.equals("Штампосварной со швом на плоскости кривизны отвода")) {
            ki=(1/weldRate)*(4*radius/(outDiam-getElemThickness())-1)/
                    (4*radius/(outDiam-getElemThickness())-2);
        }

        if(elbowType.equals("Cекторный")){
            //bevel angle less than 22,5 degree
            ki=(4*radius/(outDiam-getElemThickness())-1)/
                    (4*radius/(outDiam-getElemThickness())-2);
        }


        return ki;
    }

    @Override
    public double getBranchThickness() {
        return 0;
    }

    @Override
    protected double calcPressure(PipeElementForm elbowForm) {
        return 2* elbowForm.getWeldRate()* elbowForm.getAllowableStress()*(elbowForm.getThickness()-
                elbowForm.getAddThickness())/
                        (elbowForm.getOutDiam()*ki-(elbowForm.getThickness()- elbowForm.getAddThickness()));
    }

}
