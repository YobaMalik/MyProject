package com.example.demo.TestPressure;

import com.example.demo.TestPressure.Abstract.TestPressureClass;
import com.example.demo.TestPressure.Abstract.ModelTestPressure;

public class CTOCA03 extends TestPressureClass {

    public CTOCA03(ModelTestPressure model){
        this.setTestPressure(testPressure(model.getSigmaT(),model.getSigma20(),
                model.getPressure(),model.getElementType()));

    }

    protected double testPressure(double sigmaT, double sigma20, double pressure, String ratio) {
        // TODO Auto-generated method stub
        return !ratio.equals("литой") ?1.25*pressure*sigma20/sigmaT:1.5*pressure*sigma20/sigmaT;

    }
}
