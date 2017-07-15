package com.csv;
import java.util.*;
public class DataRow 
{
	LinkedHashMap<Integer,String> cellData=new LinkedHashMap();

	public LinkedHashMap<Integer, String> getCellData() {
		return cellData;
	}

	public void setCellData(LinkedHashMap<Integer, String> cellData) {
		this.cellData = cellData;
	}

	
}



