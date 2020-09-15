package com.example.demo.TensionCalcRD;

import com.example.demo.Interface.IGradeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

@Component
public class DAOSigmaGOST implements IGradeMap {
    private Set<String> gostList=new TreeSet<>();
    private final String url;
    private final String user;
    private final String pass;
    private final String[] tables={"GOST_SIGMA_CMS","GOST_SIGMA_HCAS","GOST_SIGMA_HRS"};

    public DAOSigmaGOST(@Value("${spring.datasource.url}") String url,
                        @Value("${spring.datasource.username}") String user,
                        @Value("${spring.datasource.password}") String pass) throws SQLException {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.getSpecMap();
    }


    @Override
    public void getSpecMap() throws SQLException {
        for(String table:tables){
            try(Connection connection= DriverManager.getConnection(url,user,pass);
                Statement statement=connection.createStatement())
            {
                String request="SELECT DISTINCT TYPE_GRADE FROM "+table;
                ResultSet set=statement.executeQuery(request);
                while (set.next()){
                    String res=set.getString("TYPE_GRADE");
                    gostList.add(res);
                }
            }
        }
    }

    @Override
    public Set<String> getGradeMap(String spec)  {
        return gostList;
    }

    @Override
    public double getTensionValue(String spec, String grade, String table, double desTemp, double[] array) throws SQLException {
        return 0;
    }

    public Set<String> getVal() {
        return gostList;
    }
}
