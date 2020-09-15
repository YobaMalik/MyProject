package com.example.demo.TensionCalcRD.AbstractClass;

import com.example.demo.Form.Abstract.PipeElementForm;

public abstract class AbstractTensionCalc {
    private double elemThickness;
    private double elemPressure;


  //  public abstract void visitor(ElementsVisitor visitor);
    public abstract double getBranchThickness();
    public double getElemThickness() {
        return elemThickness;
    }

    protected void setElemThickness(double elemThickness) {
        this.elemThickness = elemThickness;
    }

    public double getElemPressure() {
        return elemPressure;
    }

    protected void setElemPressure(double elemPressure) {
        this.elemPressure = elemPressure;
    }

    protected double calcThickness(PipeElementForm pipeForm)  {
        return (pipeForm.getDesPress()* pipeForm.getOutDiam())/
                (2* pipeForm.getWeldRate()* pipeForm.getAllowableStress()+ pipeForm.getDesPress());
    }

    protected  double calcPressure(PipeElementForm pipeForm)  {
        return (2*pipeForm.getAllowableStress()*pipeForm.getWeldRate()*(pipeForm.getThickness()
                -pipeForm.getAddThickness())/
                (pipeForm.getOutDiam()-(pipeForm.getThickness()-pipeForm.getAddThickness())));
    }

}
