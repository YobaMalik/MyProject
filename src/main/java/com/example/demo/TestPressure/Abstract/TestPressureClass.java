package com.example.demo.TestPressure.Abstract;

public abstract class TestPressureClass {
    private double testPressure;

    protected abstract double testPressure(double sigmaT,double sigma20, double pressure, String ratio );

    public void setTestPressure(double testPressure) {
        this.testPressure=testPressure;
    }

    public double getTestPressure() {
        return testPressure;
    }

}
