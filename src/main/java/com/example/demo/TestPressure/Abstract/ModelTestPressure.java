package com.example.demo.TestPressure.Abstract;

public class ModelTestPressure {
    private double sigma20;
    private double sigmaT;
    private double pressure;
    private double toughness;
    private String elementType;

    public double getSigma20() {
        return sigma20;
    }

    public void setSigma20(double sigma20) {
        this.sigma20=sigma20;
    }

    public double getSigmaT() {
        return sigmaT;
    }

    public void setSigmaT(double sigmaT) {
        this.sigmaT=sigmaT;
    }
    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure=pressure;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType=elementType;
    }

    public double getToughness() {
        return toughness;
    }

    public void setToughness(double toughness) {
        this.toughness=toughness;
    }

}
