package com.example.demo.MyController;

import com.example.demo.TensionCalcASME.SigmaASME;
import com.example.demo.TensionCalcASME.SpecForm;

import com.example.demo.TensionCalcASME.SpecGradeMap;
import com.example.demo.TensionCalcRD.GetGradeMap;
import com.example.demo.TestPressure.Abstract.ModelTestPressure;
import com.example.demo.TestPressure.CalcTestPressure;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

@Controller
public class SpecGradeController {

    @Inject SpecGradeMap specMap;
    @Inject GetGradeMap mapRD;

    @PostMapping("/asmeTension")
    public String getValueASME(SpecForm specForm, Model model) throws IOException {
        System.out.println(specForm.getSpec());
        System.out.println(specForm.getGrade());
        SpecForm resForm=new SpecForm();
        resForm.setOutTensiton(new SigmaASME().getTension(specForm.getSpec(),specForm.getGrade(),specForm.getTemp()));
        model.addAttribute("outTension", resForm);
        return "SpecGradeParams";

    }

    @PostMapping("/test1")
    public String getValueRD(SpecForm specForm, Model model) throws IOException {
        SpecForm resForm=new SpecForm();
        model.addAttribute("outTension", resForm);
        return "SpecGradeParams";
    }

    @PostMapping("/testPressureInfo")
    public String getInfoToTestPressureCalculation(ModelTestPressure form) throws IOException {
        new CalcTestPressure(form);
        return "calcTime";
    }

    @PostMapping("/calcTension")
    public String getTension(SpecForm specForm, Model model){
         specForm= (SpecForm) model.getAttribute("specForm");
        return "calcTime";
    }


    @RequestMapping(value = "/stressCalculation", method = RequestMethod.GET)
    public String uploadMultiFileHandler(Model model) throws Exception {
        model.addAttribute("specForm",new SpecForm());
        model.addAttribute("mapList", specMap);
        model.addAttribute("simgaRD",mapRD);
        model.addAttribute("pipe");
       // model.addAttribute("pipeCalculation",new PipeForm());
        return "StressCalc";
    }

    @RequestMapping(value = "/getCharNum", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> getCharNum(@RequestParam("zulul")  String spec) throws IOException {
        return this.specMap.getVal().get(spec);
    }
}
