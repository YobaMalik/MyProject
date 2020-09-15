/*function getGrade() {
    var el = document.getElementById('specTest')
    var spec = el.options[el.selectedIndex].text;
    $.ajax({
        url: "getCharNum",
        data: { zulul: spec },
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            var list = document.getElementById('gradeTest');
            var z = 0;

            $.each(data, function (index, value) {
                console.log(index, value);
                list.options[z] = new Option(index, value);
                z++;
            });

        }
    })
}
*/
function getGOSTTension() {
   var el = document.getElementById('specASME')
   var spec = el.options[el.selectedIndex].text;

   var elm = document.getElementById('gradeASME')
   var grade = elm.options[elm.selectedIndex].text;

   var temp=document.getElementById('desTempGOST').value;


   var res=document.getElementById('gostTension');

    fetch("asmeTension", {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
           spec:spec,
           grade:grade,
           temp:temp
        })
    }).then(function (response) {
              response.json().then(function (data) {
              res.innerHTML="RETARDET JAVASCRIPT... Tension is " + data["outTensiton"] + " MPa";
              });
       });

}


function getGrade(str,url) {
   var el = document.getElementById('spec'+str)
   var spec = el.options[el.selectedIndex].text;
   var list = document.getElementById('grade'+str);
   list.innerHTML=null;
    fetch(url, {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
           spec:spec
        })
    }).then(function (response) {
              response.json().then(function (data) {

              for(var key in data) {
              list.append(new Option(data[key]));
                // console.log(data[key]);
                 }

              });
       });

}
function testPipe() {
    var desPress = document.getElementById("pressPipe").value;
    var desTemp = document.getElementById("desTempPipe").value;
    var allStress = document.getElementById("stressPipe").value;
    var outDiam = document.getElementById("outDiamPipe").value;
    var thickness = document.getElementById("thicknessPipe").value;
    var res = document.getElementById("resultPressPipe");
    var res1 = document.getElementById("resultThickPipe");

    fetch("pipeStressCalc/pipe", {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            desPress: desPress,
            desTemp: desTemp,
            allowableStress: allStress,
            outDiam: outDiam,
            thickness: thickness
        })
    }).then(function (response) {
              response.json().then(function (data) {
                 res.innerHTML = "расчетная толщина " + parseFloat(data["elemThickness"]).toFixed(2) + " мм";
                 res1.innerHTML = "расчетное давление " + parseFloat(data["elemPressure"]).toFixed(2)  + "МПа";
                  });
              }
    );

}

function testElbow() {
    var desPress = document.getElementById("pressElbow").value;
    var desTemp = document.getElementById("desTempElbow").value;
    var allStress = document.getElementById("stressElbow").value;
    var outDiam = document.getElementById("outDiamElbow").value;
    var thickness = document.getElementById("thicknessElbow").value;
    var res = document.getElementById("resultPressElbow");
    var res1 = document.getElementById("resultThickElbow");
   // var radius=document.getElementById("radiusElbow");
    var radius=outDiam*1.5;
    var e=document.getElementById("elbowType");
    var elbowType=e.options[e.selectedIndex].value

    fetch("pipeStressCalc/elbow", {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            desPress: desPress,
            desTemp: desTemp,
            allowableStress: allStress,
            outDiam: outDiam,
            thickness: thickness,
            radius:radius,
            elbowType:elbowType
        })
    }).then(function (response) {
              response.json().then(function (data) {
                 res.innerHTML = "расчетная толщина " + parseFloat(data["elemThickness"]).toFixed(2) + " мм";
                 res1.innerHTML = "расчетное давление " + parseFloat(data["elemPressure"]).toFixed(2)  + "МПа";
                  });
              }
    );

}


function testTee() {
    var desPress = document.getElementById("pressTee").value;
    var desTemp = document.getElementById("desTempTee").value;
    var allStress = document.getElementById("stressTee").value;
    var outDiam = document.getElementById("outDiamTee").value;
    var thickness = document.getElementById("thicknessTee").value;
    var branchDiam=document.getElementById("branchDiam").value;
    var branchThickness=document.getElementById("branchThickness").value;


    var res = document.getElementById("resultPressTeem");
    var res1 = document.getElementById("resultThickTeem");
    var res3 = document.getElementById("resultPressTee");
    var res4 = document.getElementById("resultThickTee");



    fetch("pipeStressCalc/tee", {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            desPress: desPress,
            desTemp: desTemp,
            allowableStress: allStress,
            outDiam: outDiam,
            thickness: thickness,
            branchDiam:branchDiam,
            branchThickness:branchThickness
        })
    }).then(function (response) {
              response.json().then(function (data) {
                 res.innerHTML = "расчетная толщина " + parseFloat(data["elemThickness"]).toFixed(2) + " мм";
                 res1.innerHTML = "расчетное давление " + parseFloat(data["elemPressure"]).toFixed(2)  + "МПа";
                 res3.innerHTML = "расчетная толщина ответ " + parseFloat(data["branchCalcThickness"]).toFixed(2)  + "мм";
                 res4.innerHTML = "расчетное давление ответ" + parseFloat(data["branchPress"]).toFixed(2)  + "МПа";
                  });
              }
    );

}




function testRed() {
    var desPress = document.getElementById("pressRed").value;
    var desTemp = document.getElementById("desTempRed").value;
    var allStress = document.getElementById("stressRed").value;

    var outBDiam = document.getElementById("outBDiamRed").value;
    var eBThickness = document.getElementById("outBDiam").value;

    var outSDiam=document.getElementById("outSDiamRed").value;
    var eSThickness=document.getElementById("outSDiam").value;

    var length=document.getElementById("lenght").value;

    var e=document.getElementById("reducerType");
    var reducerType=e.options[e.selectedIndex].value;

    var res = document.getElementById("resultPressRedm");
    var res1 = document.getElementById("resultThickRedm");
    var res3 = document.getElementById("resultPressRed");
    var res4 = document.getElementById("resultThickRed");

    fetch("pipeStressCalc/reducer", {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            desPress: desPress,
            desTemp: desTemp,
            allowableStress: allStress,
            outBDiam: outBDiam,
            eBThickness: eBThickness,
            outSDiam: outSDiam,
            eSThickness: eSThickness,
            length: length,
            reducerType: reducerType
        })
    }).then(function (response) {
              response.json().then(function (data) {
                 res.innerHTML = "расчетная толщина " + parseFloat(data["elemThickness"]).toFixed(2) + " мм";
                 res1.innerHTML = "расчетное давление " + parseFloat(data["elemPressure"]).toFixed(2)  + "МПа";
                 res3.innerHTML = "расчетная толщина lil " + parseFloat(data["branchThickness"]).toFixed(2)  + "mm";
                 res4.innerHTML = "а тут пусто ежжи "   + "мм";
                  });
              }
    );

}

function testTPTC() {
    var aggrState = document.getElementById("aggrStateTPTC").value;
    var operPress = document.getElementById("operPressTPTC").value;
    var maxDn = document.getElementById("DNTPTC").value;
    var desTemp = document.getElementById("desTempTPTC").value;


    var res = document.getElementById("TPTCresult");

    var resThickness;
    fetch("TPTC032", {
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            aggrState: aggrState,
            operPress: operPress,
            maxDn: maxDn,
            desTemp: desTemp
        })
    }).then(function (response) {
              response.json().then(function (data) {
                 res.innerHTML = "Категория " + parseFloat(data["category"]);

                  });
              }
    );

}