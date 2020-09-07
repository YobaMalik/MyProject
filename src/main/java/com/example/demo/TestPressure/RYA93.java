package com.example.demo.TestPressure;

import com.example.demo.TestPressure.Abstract.TestPressureClass;
import com.example.demo.TestPressure.Abstract.ModelTestPressure;

public class RYA93 extends TestPressureClass {

    public RYA93(ModelTestPressure model){
        this.setTestPressure(testPressure(model.getSigmaT(),model.getSigma20(),
                model.getPressure(),model.getElementType()));
    }

    protected double testPressure(double sigmaT, double sigma20, double pressure, String ratio) {
        // TODO Auto-generated method stub
        return Math.max(1.25 * pressure * sigma20 / sigmaT, 0.1);
    }

}
