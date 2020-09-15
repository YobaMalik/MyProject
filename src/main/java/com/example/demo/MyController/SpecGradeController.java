package com.example.demo.MyController;

import com.example.demo.TensionCalcASME.SpecForm;
import com.example.demo.TensionCalcASME.DAOSpecGradeMap;
import com.example.demo.TensionCalcRD.DAOSigmaGOST;
import com.example.demo.TestPressure.Abstract.ModelTestPressure;
import com.example.demo.TestPressure.CalcTestPressure;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Set;

@Controller
public class SpecGradeController {

    @Inject DAOSpecGradeMap mapASME;
    @Inject DAOSigmaGOST sigmaGOST;

    @PostMapping("/asmeTension")
    @ResponseBody
    public SpecForm getValueASME(@RequestBody SpecForm specForm) throws  SQLException {
        specForm.setOutTensiton(mapASME.getTension(specForm.getSpec(),specForm.getGrade(),specForm.getTemp()));
        return specForm;
    }

    @PostMapping("/test1")
    public String getValueRD( Model model)  {
        SpecForm resForm=new SpecForm();
        model.addAttribute("outTension", resForm);
        return "SpecGradeParams";
    }

    @PostMapping("/testPressureInfo")
    public String getInfoToTestPressureCalculation(ModelTestPressure form)  {
        new CalcTestPressure(form);
        return "calcTime";
    }

    @PostMapping("/calcTension")
    public String getTension(Model model){
        SpecForm specForm = (SpecForm) model.getAttribute("specForm");
        return "calcTime";
    }


    @RequestMapping(value = "/stressCalculation", method = RequestMethod.GET)
    public String uploadMultiFileHandler(Model model) {
        model.addAttribute("specForm",new SpecForm());
        model.addAttribute("mapList", mapASME);
        model.addAttribute("simgaRD",sigmaGOST);
        model.addAttribute("pipe");
       // model.addAttribute("pipeCalculation",new PipeForm());
        return "StressCalc";
    }

    @PostMapping(value = "/getASMEGrade")
    @ResponseBody
    public Set<String> getASMEGrade(@RequestBody SpecForm spec) throws  SQLException {
        return  this.mapASME.getGradeMap(spec.getSpec());
    }
    @PostMapping(value = "/getGOSTGrade")
    @ResponseBody
    public Set<String> getGOSTGrade(@RequestBody SpecForm spec) throws  SQLException {
        return this.mapASME.getGradeMap(spec.getSpec());
    }
}
