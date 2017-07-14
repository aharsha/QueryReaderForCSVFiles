package com.csv;
import java.io.BufferedReader;
import java.io.FileReader;


public class FetchCsvFile {

	
	public DataSet executeQuery(Query query) throws Exception {
		
		//based on query appropriate fetch method will call
		//all fetch methods will store data in DataSet Object
		//fetch methods not yet defined 
		
		DataSet dataSet = new DataSet();

		return dataSet;
	}

	public HeaderRow getHeaderRow(Query query) throws Exception
	{
		BufferedReader bufferreader=new BufferedReader(new FileReader(query.getFilepath()));
		HeaderRow headerrow=new HeaderRow();
		
		if(bufferreader!=null)
		{
			String rowdata=bufferreader.readLine();
			String rowvalues[]=rowdata.split(",");
			int colindex=0;
			for(String rowvalue:rowvalues)
			{
				headerrow.firstRow.put(rowvalue,colindex);
				colindex++;
			}
			
			System.out.println(headerrow.firstRow);
		}
		
		return headerrow;
	}
	
	
	public DataRow getAllRows(Query query) throws Exception
	{
		DataRow dataRow=null;
		BufferedReader bufferedReader=new BufferedReader(new FileReader(query.getFilepath()));
		HeaderRow headerRow=getHeaderRow(query);
		int rowIndex=-1;
		while(bufferedReader!=null)
		{
			String lineData=bufferedReader.readLine();
			rowIndex++;
			if(rowIndex==0)
			{
				continue;
			}
			
			String rowValues[]=lineData.split(",");
			int columnIndex=0;
			
			for(String rowValue:rowValues)
			{
				
				dataRow=new DataRow();
				dataRow.rowData.put(columnIndex,rowValue);
			//	System.out.print(headerRow.getFirstRow().get());
				//System.out.println(columnIndex);
				System.out.print(dataRow.rowData.get(columnIndex)+"\t \t");
				columnIndex++;
				//System.out.print(dataRow.rowData.get(columnIndex)+"\t");
				//System.out.println(dataRow.rowData.get(1));
				//System.out.println(dataRow.rowData.get(2));
				//System.out.println(dataRow.rowData.get(3));
				//System.out.println(headerRow.getFirstRow().get(rowValue));
			}
			
			//System.out.println(headerRow.firstRow);
			System.out.println();
		}
		
		return dataRow;
	}
	
	
	
	
}
