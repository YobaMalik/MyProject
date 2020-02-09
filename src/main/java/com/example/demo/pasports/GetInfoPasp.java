package com.example.demo.pasports;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class GetInfoPasp {
    private  ConcurrentLinkedQueue<RowfTable<String>> allTable1=new ConcurrentLinkedQueue<>();
	public static class Test123{
		String z="ZULUL";
		String a="Vi VOM";
		String zxc="UGANDAN BRUCE LEE";
	}

    private void createTable(ConcurrentLinkedQueue<RowfTable<String>> allTable1,
                             HashMap<String, ByteArrayOutputStream> FileInput) throws IOException {
        XSSFWorkbook newbk = new XSSFWorkbook();
        XSSFSheet newshit = newbk.createSheet("Result");
        Row newrow = newshit.createRow(0);
        newrow.createCell(0).setCellValue("Линия");
        newrow.createCell(1).setCellValue("Рабочая давление");
        newrow.createCell(2).setCellValue("рабочая температура");
        newrow.createCell(3).setCellValue("расчетное давление");
        newrow.createCell(4).setCellValue("расчетная температура");
        newrow.createCell(5).setCellValue("диаметр и толщина");
        newrow.createCell(6).setCellValue("отбрак");
        newrow.createCell(7).setCellValue("длина");
        newrow.createCell(8).setCellValue("наименование трубопровода");
        newrow.createCell(9).setCellValue("класс опасности по гост");
        newrow.createCell(10).setCellValue("наименование рабочей среды");
        newrow.createCell(11).setCellValue("пожаровзрывоопасность");
        newrow.createCell(12).setCellValue("группа рс по гост");
        newrow.createCell(13).setCellValue("кат по тр тс");
        newrow.createCell(14).setCellValue("кат по гост");
        newrow.createCell(15).setCellValue("скорость коррозии");
        newrow.createCell(16).setCellValue("название файла");
        newrow.createCell(17).setCellValue("рабочее давление раздел 2");
        newrow.createCell(18).setCellValue("расчетное давление раздел 2");
        newrow.createCell(19).setCellValue("рабочая температура раздел 2");
        newrow.createCell(20).setCellValue("расчетная температура раздел 2");
        newrow.createCell(21).setCellValue("чертежи");
        newrow.createCell(22).setCellValue("сварка");
        newrow.createCell(23).setCellValue("давление испытаний 2");
        newrow.createCell(24).setCellValue("герметичносьт");
        newrow.createCell(25).setCellValue("что-то");
        newrow.createCell(26).setCellValue("№ паспортца");
        newrow.createCell(27).setCellValue("макс диаметр паспорта");
        newrow.createCell(28).setCellValue("материал труб, требуется сверка с паспортом в случае пустого значения");

        ArrayList<String> filelist = new ArrayList<>();
        FileOutputStream newa = null;
        try {
            List<Callableclass> tasklist=new ArrayList<>();
            for(Map.Entry<String,ByteArrayOutputStream> entry:FileInput.entrySet()){
                InputStream in=new ByteArrayInputStream(entry.getValue().toByteArray());
                tasklist.add(new Callableclass(entry.getKey(),in, allTable1));
                in.close();
            }
            ExecutorService ntask = Executors.newFixedThreadPool(8);
            try {
                ntask.invokeAll(tasklist);
                ntask.shutdown();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            } finally{
                ntask.shutdown();
            }

                    newa= new FileOutputStream("C:\\Users\\Yoba\\Desktop\\свидетельства\\testres.xlsx");
           // newa= new FileOutputStream("/home/yoba/Рабочий стол/testResult/testres.xlsx");
           List<RowfTable<String>> resultTable=Lists.newArrayList(allTable1.iterator());

           resultTable.sort(new Comparator<RowfTable<String>>(){ //comparator for result arraylist

        	@Override
        	public int compare(RowfTable<String> arg0, RowfTable<String> arg1) {
        		if(arg0.getValue(26).compareTo(arg1.getValue(26))>0) {
        			return 1;
        		}
        		if( arg0.getValue(26).compareTo(arg1.getValue(26))<0) {
        			return -1;
        		}
        		return 0;
        }
        });
            //XSSFWorkbook newbk1 = new XSSFWorkbook();
           // XSSFSheet newshit1 = newbk1.createSheet("Result");
            int ij=1;
            Iterator<RowfTable<String>> iter=resultTable.iterator(); 
            while (iter.hasNext()){
                RowfTable<String> n1=iter.next();
                Row newr=newshit.createRow(ij);
                for (int i=0;i<n1.getSize();i++){
                    //System.out.println(n1.getValue(i));
                    if (!(n1.getValue(i)==null)){
                        newr.createCell(i).setCellValue(n1.getValue(i).toString());
                    }
                    else {
                        newr.createCell(i).setCellValue("");
                    }
                }
                ij++;
            }
            newbk.write(newa);
            newbk.close();
        } catch (IOException var16) {
            var16.printStackTrace();
        } finally {
            if (newa != null) {
                newa.close();
            }
        }
        for(String fls:filelist){
            if(new File(fls).exists()){
                new File(fls).delete();
            }
        }
    }

    public int pasp(){
        return this.allTable1.size();
    }
    public double run(HashMap<String,ByteArrayOutputStream> FileInput) throws IOException, ParseException, InvalidFormatException {

        long starttime = System.currentTimeMillis();
        this.createTable(allTable1,FileInput);
        System.out.println((double)System.currentTimeMillis() / 60000 - (double)starttime / 60000);
        return (double)System.currentTimeMillis() / 60000 - (double)starttime / 60000;
    }

}


