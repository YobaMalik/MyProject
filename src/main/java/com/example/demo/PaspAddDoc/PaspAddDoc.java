package com.example.demo.PaspAddDoc;

import com.example.demo.Pasport.ExtractPasportData;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class PaspAddDoc extends ExtractPasportData {


    public PaspAddDoc(InputStream inStr) throws IOException {
        super(inStr);
    }

    public void testsom(String example, Map<String, ByteArrayOutputStream> somList) throws IOException {
            SvOmont certificate = new SvOmont();
            this.defVariables();
            certificate.setDesingPress(this.getDesignTemp());
            certificate.setLinelist(this.getPipeline());
            certificate.setDesingTemp(this.getDesignTemp());
            certificate.setFluidCode(this.getFluidCode());
            certificate.setPipingName(this.getpipename());
            certificate.setDWGlist(this.getDWGs());
            certificate.setKatGOST(this.getGroupGOST() + " " + this.getkatGOST());
            certificate.setWeldIfno(this.getweldInfo());
            certificate.setNameTit(this.getNameTitul());
            certificate.setHeatTreatment(this.getheatTreatment());
            certificate.createSOM(example, somList);
    }
}
