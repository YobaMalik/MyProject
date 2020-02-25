package com.example.demo.Rubbish;

//import org.apache.commons.io.FileUtils;

import com.example.demo.pasports.Filesearch;
import com.example.demo.pasports.RowfTable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DWGc {
	/*
	private void Filecopy(HashMap<String, String> Files) {
		Files.entrySet().forEach(e->{
			String newSrc="R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\8232_ЗАПСИБ\\3. Полипропилен\\3. Исполнительная документация\\6. Автокады по паспортам\\"
					+	e.getValue();
			File src=new File(e.getKey());
			if (!new File(newSrc).exists()){
				new File(newSrc).mkdir();
				System.out.println(newSrc);
			}
			File dst=new File(newSrc+"\\"+src.getName());
			try {
				FileUtils.copyFile(src,dst );
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
*/
	public void run(List<RowfTable<String>> resultTable) {
		HashMap<String, String> Files=new HashMap<>();
		ArrayList<String> Filenames=new ArrayList<>();
		String fPath = "R:\\Проекты\\ЭПБ (разреш. Ростехнадзора)\\8232_ЗАПСИБ\\3. Полипропилен\\3. Исполнительная документация\\5. Чертежи в автокаде";
		new Filesearch().findfileszulul(fPath, "dwg", Filenames);
		resultTable.forEach(e->{
			String line[]=e.getValue(0).split("-");
			String res=line[0]+"-"+line[1];
			Filenames.forEach(z->{
				if(z.contains(res)) {
					
					Files.put(z,e.getValue(26));
				}
			});
		});
	//	this.Filecopy(Files);
		
	}
}
