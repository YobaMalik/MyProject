package com.example.demo.Interface;

import com.example.demo.Form.RDForm.*;
import com.example.demo.TensionCalcRD.Elements.*;

public interface Visitor {

    ElbowCalculation visit(ElbowForm elbow);
    PipeCalculation visit(PipeForm pipe);
    ReducerCalculation visit(ReduceForm reducer);
    TeeCalculation visit(TeeForm tee);
    CrossCalculation visit(CrossForm cross);


}
