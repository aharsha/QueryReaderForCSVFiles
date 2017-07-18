package com.csv;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.*;
public class QueryStatement {
	public DataSet executeQuery(String queryString) 
	{
		if (isValidQueryString(queryString)) 
		{
			Query query=null;
			try {
				query = new Query(queryString);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return new FetchCsvData(query).getCsvData();
		} 
		
		return null;
		
	}

	private boolean isValidQueryString(String queryString) 
	{
		if(queryString.contains("select") && queryString.contains("from") || (queryString.contains("where") ||queryString.contains("order by")|| queryString.contains("group by")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


}
