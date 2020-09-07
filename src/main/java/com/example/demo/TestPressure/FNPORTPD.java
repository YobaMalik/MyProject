package com.example.demo.TestPressure;

import com.example.demo.TestPressure.Abstract.TestPressureClass;
import com.example.demo.TestPressure.Abstract.ModelTestPressure;

public class FNPORTPD extends TestPressureClass {
    private double toughness;

    public FNPORTPD(ModelTestPressure model){
        this.setTestPressure(testPressure(model.getSigmaT(),model.getSigma20(),
                model.getPressure(),model.getElementType()));
    }

    protected double testPressure(double sigmaT, double sigma20, double pressure, String ratio) {
        // TODO Auto-generated method stub
        double testPressure=0;
        if (ratio.equals("водогрейный котел") || ratio.equals("паровой котел") || ratio.equals("пароперегреватель")) {
            testPressure=pressure<=0.5? Math.max(1.5 * pressure, 0.2):Math.max(1.25 * pressure, 0.3);
        }

        if ( ratio.equals("электрокотел")) {
            testPressure=1.25*pressure*sigma20/sigmaT;
        }

        if (ratio.equals("литой") | ratio.equals("кованный")) {
            testPressure=1.5*pressure*sigma20/sigmaT;
        }

        if (ratio.equals("не металлический сосуд")) {
            testPressure=1.5*pressure*sigma20/sigmaT;
        }

        return testPressure;
    }

}
