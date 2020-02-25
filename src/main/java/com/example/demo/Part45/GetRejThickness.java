package com.example.demo.Part45;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public interface GetRejThickness {
    default double getRejThick(String oDiam) {
        double rejThick=0;
        int outDiam=(int) Double.parseDouble(oDiam);
        if(outDiam<=25.0) rejThick=1.0;
        if(outDiam>25.0&& outDiam<=57.0) rejThick=1.5;
        if(outDiam>57.0&& outDiam<=114.0) rejThick=2.0;
        if(outDiam>114.0&& outDiam<=219.0) rejThick=2.5;
        if(outDiam>219.0&& outDiam<=325.0) rejThick=3.0;
        if(outDiam>325.0&& outDiam<=377.0) rejThick=3.5;
        if(outDiam>377.0&& outDiam<=426.0) rejThick=4.0;
        if(outDiam>426.0) rejThick=4.0;
        return rejThick;
    }

    default double c12Elbow(String eThickness, String rad,String outDiam) {
        double c12=0;
        c12=Double.parseDouble(eThickness)/
                (1+2*Double.parseDouble(rad)/Double.parseDouble(outDiam));
        return c12;
    }
    default List<String> GetDiamAndThik(String outDiam) {
        List<String> elemSize=new ArrayList<>();
        outDiam=outDiam.replace(" ", "");
        outDiam=outDiam.replace("х", "x");
        outDiam=outDiam.replace("/n","");
        outDiam=outDiam.replace("/","-");
        String[] splitDD=outDiam.split("-");
        for (int i=0;i<splitDD.length;i++) {
            String[] arr=splitDD[i].split("x");
            for (int j=0;j<arr.length;j++) {
                elemSize.add(arr[j]);
            }
        }
        return elemSize;
    }


}

