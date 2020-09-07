package com.example.demo.TestPressure;

import com.example.demo.TestPressure.Abstract.ModelTestPressure;

public class CalcTestPressure {
    public CalcTestPressure(ModelTestPressure model){
        CTOCA03 t1=new CTOCA03(model);
        FNPORTPD t2=new FNPORTPD(model);
        RYA93 t3=new RYA93(model);
        UTHE93 t4=new UTHE93(model);
        System.out.println(model.getElementType());
        System.out.println(t1.getTestPressure());
        System.out.println(t2.getTestPressure());
        System.out.println(t3.getTestPressure());
        System.out.println(t4.getTestPressure());

    }
}
