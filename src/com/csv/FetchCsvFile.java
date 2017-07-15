package com.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class FetchCsvFile {

	public DataSet executeQuery(Query query) throws Exception {

		// based on query appropriate fetch method will call
		// all fetch methods will store data in DataSet Object
		// fetch methods not yet defined

		DataSet dataSet = new DataSet();

		return dataSet;
	}

	public HeaderRow getHeaderRow(Query query) throws Exception {
		BufferedReader bufferreader = new BufferedReader(new FileReader(query.getFilepath()));
		HeaderRow headerrow = new HeaderRow();

		if (bufferreader != null) {
			String rowdata = bufferreader.readLine();
			String rowvalues[] = rowdata.split(",");
			int colindex = 0;
			for (String rowvalue : rowvalues) {
				headerrow.firstRow.put(rowvalue, colindex);
				colindex++;
			}

			System.out.println(headerrow.firstRow);
		}

		return headerrow;
	}

	public DataSet getAllRows(Query query) throws Exception {
		DataRow dataRow = null;
		DataSet dataSet = new DataSet();
		;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(query.getFilepath()));
		HeaderRow headerRow = getHeaderRow(query);
		int rowIndex = -1;
		String lineData;
		
		while ( (lineData= bufferedReader.readLine()) != null) {
			
			//System.out.println("lineData = "+lineData);
			rowIndex++;
			if (rowIndex == 0) {
				continue;
			}

			String cellValues[] = lineData.split(",");
			int columnIndex = 0;
			dataRow = new DataRow();
			for (String cellValue : cellValues) {

			

				dataRow.cellData.put(columnIndex, cellValue);


				//System.out.print(dataRow.rowData.get(columnIndex) + "\t \t");
				columnIndex++;

			}

			dataSet.rowData.add(dataRow);
			//System.out.println();
		}

		return dataSet;
	}

	public DataSet getAllRowsExperiment(Query query) throws Exception {
		DataRow dataRow = null;
		DataSet dataSet = new DataSet();
		;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(query.getFilepath()));
		HeaderRow headerRow = getHeaderRow(query);
		LinkedHashMap<String,Integer> headerInfo=headerRow.getFirstRow();
		int rowIndex = -1;
		String lineData;
		
		while ( (lineData= bufferedReader.readLine()) != null) {
			
			System.out.println("lineData = "+lineData);
			rowIndex++;
			if (rowIndex == 0) {
				continue;
			}

			String cellValues[] = lineData.split(",");
			int columnIndex = 0;
			dataRow = new DataRow();
			String columnNames[]=query.getSelectColumns();
			for(String columnName:columnNames)
			{
				
				int key=0; 
				
				for( String key1 : headerInfo.keySet() )
				{
				if((columnName.equals(key1)))
				{
					System.out.println("column Index  = "+headerInfo.get(key1));
				}
				}
				key++;
			}
			for (String cellValue : cellValues) {


			

				dataRow.cellData.put(columnIndex, cellValue);


				//System.out.print(dataRow.rowData.get(columnIndex) + "\t \t");
				columnIndex++;

			}

			dataSet.rowData.add(dataRow);
			//System.out.println();
		}

		return dataSet;
	}

	
	
	public List<Integer> columnIndexs(Query query)
	{
		HeaderRow headerRow;
		LinkedHashMap<String, Integer> headerInfo;
		
		String columnNames[] = query.getSelectColumns();
		
		List<Integer> selcttedColIndexes=new ArrayList();
		
		
		try {
			headerRow = getHeaderRow(query);
			headerInfo = headerRow.getFirstRow();
			
		for (String columnName : columnNames) {
			
		
			for (String key1 : headerInfo.keySet()) {
				if ((columnName.equals(key1))) {
					selcttedColIndexes.add(headerInfo.get(key1));
				}
		}
		
	}
	
} catch (Exception e) {
			
			e.printStackTrace();
		}
		return selcttedColIndexes;
	}//method

}//class

	

