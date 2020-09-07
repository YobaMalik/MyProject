package com.example.demo.TensionCalcRD;

import com.example.demo.TensionCalcASME.SigmaASME;

import java.util.HashMap;
import java.util.Map;

public class SigmaRD extends SigmaASME {

    public Map<String,double[]> arrayST=new HashMap<>();

    private final String HRS="SigmaHRS";
    private final String HCAS="SigmaHCAS";
    private final String CMS="SigmaCMS";


    private final double arrayHRS[]={150, 250, 300, 350, 400, 420, 440, 450, 460, 480, 500, 510, 520, 530,
            540, 550, 560, 570, 580, 590, 600, 610, 620
    };

    private final double arrayHCAS[]={150, 250, 300, 350, 400, 450, 500, 520, 530, 540, 550, 560, 570, 580, 590, 600,
            610, 620, 630, 640, 650, 660, 670, 680, 690, 700
    };

    private final double arrayCMS[]={100, 200, 250, 275, 300, 320, 340, 350, 360, 380, 400, 410, 420, 430, 440, 450,
            460, 470, 480, 490, 500, 510
    };

    public  SigmaRD(){
          this.arrayST.put(HRS,arrayHRS);
          this.arrayST.put(HCAS,arrayHCAS);
          this.arrayST.put(CMS,arrayCMS);
    }
}
