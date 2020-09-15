package com.example.demo.MyController;

import com.example.demo.Form.RDForm.*;
import com.example.demo.TensionCalcRD.AbstractClass.AbstractTensionCalc;
import com.example.demo.TensionCalcRD.AbstractClass.ElementsVisitor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ThicknessCalculationController {

    @PostMapping(path="/pipeStressCalc/pipe", headers = {"Content-type=application/json"})
    @ResponseBody
    public AbstractTensionCalc calcPipe(@RequestBody PipeForm elemForm){
        System.out.println(elemForm.getDesPress());
        return new ElementsVisitor().visit(elemForm);
    }

    @PostMapping(path="/pipeStressCalc/elbow", headers = {"Content-type=application/json"})
    @ResponseBody
    public AbstractTensionCalc calcElbow(@RequestBody ElbowForm elemForm){
        return new ElementsVisitor().visit(elemForm);
    }

    @PostMapping(path="/pipeStressCalc/tee", headers = {"Content-type=application/json"})
    @ResponseBody
    public AbstractTensionCalc calcTee( @RequestBody TeeForm elemForm){
        return new ElementsVisitor().visit(elemForm);
    }

    @PostMapping(path="/pipeStressCalc/cross", headers = {"Content-type=application/json"})
    @ResponseBody
    public AbstractTensionCalc calcCross( @RequestBody CrossForm elemForm){
        return new ElementsVisitor().visit(elemForm);
    }

    @PostMapping(path="/pipeStressCalc/reducer", headers = {"Content-type=application/json"})
    @ResponseBody
    public AbstractTensionCalc calcReducer(@RequestBody ReduceForm elemForm){
        return new ElementsVisitor().visit(elemForm);
    }

}
