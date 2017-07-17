package com.testcsv;

import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.csv.DataRow;
import com.csv.DataSet;

import com.csv.QueryStatement;

public class QueryProcessTestCase 
{
	static QueryStatement queryStatement;

	@BeforeClass
	public static void initialize()
	{
		queryStatement=new QueryStatement();
	}
	

	public void allColumnsAllRows()throws Exception
	{
		String queryString="select * from D:/Emp.csv";
		assertNotNull(queryStatement.executeQuery(queryString));
		System.out.println(queryString);
		displayRecords(queryStatement.executeQuery(queryString));
	}
	
	@Test
	public void specificColumnsAllRows()throws Exception
	{
		String queryString="select Name,City,Salary from D:/Emp.csv";
		assertNotNull(queryStatement.executeQuery(queryString));
		System.out.println(queryString);
		displayRecords(queryStatement.executeQuery(queryString));
	
	}
	
	@Test
	public void allColumnsSpecificRows()throws Exception
	{
		String queryString="select * from D:/Emp.csv where Name = Anand";
		DataSet dataSet=queryStatement.executeQuery(queryString);
		assertNotNull(dataSet.getResultSet().size());
		System.out.println(queryString);
		displayRecords(dataSet);
		
		String queryString1="select * from  D:/Emp.csv where Salary>=35000";
		DataSet dataSet1=queryStatement.executeQuery(queryString1);
		assertNotNull(dataSet1.getResultSet().get(0));
		System.out.println(queryString1);
		displayRecords(dataSet1);
		
		String queryString2="select * from D:/Emp.csv where Salary>31000 or City = Bangalore and Name=Vinod";
		DataSet dataSet2=queryStatement.executeQuery(queryString2);
		assertNotNull(dataSet2.getResultSet().get(0));
		System.out.println(queryString2);
		displayRecords(dataSet2);
	}
	
	@Test
	public void orderByClause()throws Exception
	{
		String queryString2="select * from D:/Emp.csv order by Salary";
		DataSet dataSet2=queryStatement.executeQuery(queryString2);
		assertNotNull(dataSet2.getResultSet().get(0));
		System.out.println(queryString2);
		displayRecords(dataSet2);
	}
	
	public void displayRecords(DataSet dataSet)
	{
		for(DataRow rowData:dataSet.getResultSet())
		{
			Set<Integer> rowIndex=rowData.keySet();
			
			for(int index:rowIndex)
			{
				System.out.print(rowData.get(index)+"\t");
			}
			
			System.out.println();
		}
	}

}
