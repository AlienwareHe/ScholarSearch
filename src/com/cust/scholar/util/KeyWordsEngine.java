package com.cust.scholar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
public class KeyWordsEngine {
	public static List<String> keyWordList=new ArrayList<>();
	public static void load() throws FileNotFoundException, IOException{
		String rootPath=new File("").getAbsolutePath();
		File file=new File(rootPath+"/src/keywords.xlsx");
		XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet=workbook.getSheetAt(0);
		for(Row row:sheet){
			for(Cell cell:row){
				keyWordList.add(cell.getStringCellValue());
			}
		}
	}
	public static void main(String[] args) {
		try {
			load();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
