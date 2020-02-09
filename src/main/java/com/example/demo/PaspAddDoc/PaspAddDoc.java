package com.example.demo.PaspAddDoc;

import com.example.demo.pasports.CategoryCalc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class PaspAddDoc extends  CategoryCalc{
    private SvOmont test1=new SvOmont();
    public void testsom(String example, ConcurrentHashMap<String, ByteArrayOutputStream> SOMList) throws IOException {
        test1.setDesingPress(this.getDesignTemp());
        test1.setLinelist(this.getPipeline());
        test1.setdesingTemp(this.getDesignTemp());
        test1.setFluidCode(this.getFluidcode());
        test1.setPipingName(this.getpipename());
        test1.setDWGlist(this.getDWGs());
        test1.setKatGOST(this.getgroupGOST()+" "+this.getkatGOST());
        test1.setWeldIfno(this.getweldInfo());
        test1.setNameTit(this.getnameTitul());
        test1.setheatTreatment(this.getheatTreatment());
        test1.createSOM(example,this.getaFile(),SOMList);

    }
}
