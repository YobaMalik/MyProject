package com.example.demo.TensionCalcRD.AbstractClass;

import com.example.demo.Form.RDForm.*;
import com.example.demo.Interface.Visitor;
import com.example.demo.TensionCalcRD.Elements.*;

public class ElementsVisitor implements Visitor {

    @Override
    public ElbowCalculation visit(ElbowForm elbow) {
        return new ElbowCalculation(elbow);
    }

    @Override
    public PipeCalculation visit(PipeForm pipe) {
        return new PipeCalculation(pipe);
    }

    @Override
    public ReducerCalculation visit(ReduceForm reducer) {
        return new ReducerCalculation(reducer);
    }

    @Override
    public TeeCalculation visit(TeeForm tee) {
        return new TeeCalculation(tee);
    }
    @Override
    public CrossCalculation visit(CrossForm cross) {
        return new CrossCalculation(cross);
    }

}
