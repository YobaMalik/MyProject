package com.example.demo.TensionCalcASME;

import com.example.demo.Interface.IGradeMap;
import com.example.demo.TensionCalcASME.ITensionASME.Interpolation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

@Component
public class DAOSpecGradeMap implements IGradeMap, Interpolation {
    Set<String> resultSpecMap=new TreeSet<>();

    private final String dbPath;
    private final String pass;
    private final String user;


    public DAOSpecGradeMap(@Value("${spring.datasource.url}") String dbPath,
                           @Value("${spring.datasource.username}") String user,
                           @Value("${spring.datasource.password}") String pass) throws SQLException {

        this.dbPath = dbPath;
        this.pass = pass;
        this.user = user;
        this.getSpecMap();
    }

    @Override
    public void getSpecMap() throws SQLException {
        try( Connection connection= DriverManager.getConnection(dbPath,user,pass);
             Statement statement=connection.createStatement()){
            String listRequestB="SELECT DISTINCT SPEC_NO FROM ASME_SIGMA_B";
            String listRequestT="SELECT DISTINCT SPEC_NO FROM ASME_SIGMA_T";

            ResultSet res=statement.executeQuery(listRequestB);
            while(res.next()){
                String spec=res.getString("SPEC_NO");
                resultSpecMap.add(spec);
            }
            res.close();

            ResultSet res1=statement.executeQuery(listRequestT);
            while(res1.next()){
                String spec=res1.getString("SPEC_NO");
                resultSpecMap.add(spec);
            }
        }
    }

    @Override
    public Set<String> getGradeMap(String spec) throws SQLException {
        Set<String> resultList=new TreeSet<>();
        try( Connection connection= DriverManager.getConnection(dbPath,user,pass);
             Statement statement=connection.createStatement()
             ){
            String sqlRequestT="SELECT TYPE_GRADE FROM ASME_SIGMA_T WHERE SPEC_NO='"+spec+"'";
            ResultSet resultSetT=statement.executeQuery(sqlRequestT);
            while (resultSetT.next()){
                String res=resultSetT.getString("TYPE_GRADE");
                resultList.add(res);
            }

            String sqlRequestB="SELECT TYPE_GRADE FROM ASME_SIGMA_B WHERE SPEC_NO='"+spec+"'";
            ResultSet resultSetB=statement.executeQuery(sqlRequestB);
            while (resultSetB.next()){
                String res=resultSetB.getString("TYPE_GRADE");
                resultList.add(res);
            }
        }
        return resultList;
    }

    @Override
    public double getTensionValue(String spec, String grade,String table, double desTemp,double[] array) throws SQLException {
        double resultTension = 0;
        try(Connection connection= DriverManager.getConnection(dbPath,user,pass);
            Statement statement=connection.createStatement())
        {
            TempInterval tempInterval=new TempInterval(desTemp, array);

            if(tempInterval.getMaxTemp()==0){
                String request="SELECT TEMP_"+
                        Double.toString(tempInterval.getMinTemp()).replace(".0","")+
                " FROM ASME_SIGMA_"+table+" WHERE SPEC_NO="+"'"+spec+"' AND TYPE_GRADE="+"'"+grade+"'";
                ResultSet set= statement.executeQuery(request);
                while(set.next()){
                    resultTension=Double.parseDouble(set.getString("TEMP_"+
                            Double.toString(tempInterval.getMinTemp()).replace(".0","")));
                }
            }

            if(tempInterval.getMaxTemp()!=0){
                double min = 0;
                double max = 0;
                String requestMin="SELECT TEMP_"+
                        Double.toString(tempInterval.getMinTemp()).replace(".0","")+
                        " FROM ASME_SIGMA_"+table+" WHERE SPEC_NO="+"'"+spec+"' AND TYPE_GRADE="+"'"+grade+"'";
                ResultSet setmin= statement.executeQuery(requestMin);
                while(setmin.next()){
                    min=Double.parseDouble(setmin.getString("TEMP_"+
                            Double.toString(tempInterval.getMinTemp()).replace(".0","")));
                }

                String requestMax="SELECT TEMP_"+
                        Double.toString(tempInterval.getMaxTemp()).replace(".0","")+
                        " FROM ASME_SIGMA_"+table+" WHERE SPEC_NO="+"'"+spec+"' AND TYPE_GRADE="+"'"+grade+"'";
                ResultSet setMax= statement.executeQuery(requestMax);
                while(setMax.next()){
                    max=Double.parseDouble(setMax.getString("TEMP_"+
                            Double.toString(tempInterval.getMaxTemp()).replace(".0","")));
                }
                resultTension=this.interpolation(tempInterval.getMinTemp(),tempInterval.getMaxTemp(),min,max,desTemp);
            }
            //System.out.println(resultTension);
        }
        return (resultTension==0)?4000:resultTension;
    }

    public double getTension(String spec, String grade, double temp) throws  SQLException {
        return Math.min(this.getTensionValue(spec, grade,"T", temp,TempInterval.ARRAY_T) / 1.5,
                this.getTensionValue(spec, grade,"B", temp,TempInterval.ARRAY_B) / 2.4);

    }

    public Set<String> getVal() {
        return resultSpecMap;
    }
}
