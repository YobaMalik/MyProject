package com.example.demo.TensionCalcASME;

public class TempInterval {
    private double minTemp;
    private double maxTemp;

    public TempInterval(double desTemp, double[] array) {
        this.getTempInterval(desTemp,array);
    }


    public double getMinTemp() {
        return minTemp;
    }


    public double getMaxTemp() {
        return maxTemp;
    }

    protected static final double[] ARRAY_T ={40, 65, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375,
            400, 425, 450, 475, 500, 525
    };
    protected static final double[] ARRAY_B ={40, 100, 150, 200, 250, 300, 325, 350, 375, 400, 425, 450, 475,
            500, 525
    };

    protected static final double[] ARRAY_HRS ={150, 250, 300, 350, 400, 420, 440, 450, 460, 480, 500, 510, 520, 530,
            540, 550, 560, 570, 580, 590, 600, 610, 620
    };

    protected static final double[] ARRAY_HCAS ={150, 250, 300, 350, 400, 450, 500, 520, 530, 540, 550, 560, 570, 580, 590, 600,
            610, 620, 630, 640, 650, 660, 670, 680, 690, 700
    };

    protected static final double[] ARRAY_CMS ={100, 200, 250, 275, 300, 320, 340, 350, 360, 380, 400, 410, 420, 430, 440, 450,
            460, 470, 480, 490, 500, 510
    };

    private void getTempInterval(double temp,double[] array){
        if(temp<array[0]) {
            this.minTemp=array[0];
        }

        if(temp>array[array.length-1]){
            this.maxTemp=array[array.length-1];
        }

        for (int i=0;i<array.length-1;i++){
            if(array[i]==temp) {
                this.minTemp=array[i];
            }
            if(array[i]<temp&&array[i+1]>temp) {
              this.minTemp=array[i];
              this.maxTemp=array[i+1];
            }

        }

    }


}
