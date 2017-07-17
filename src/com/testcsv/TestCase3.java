package com.testcsv;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.csv.DataRow;
import com.csv.DataSet;

import com.csv.QueryStatement;
public class TestCase3 {

	static QueryStatement queryStatement;

	@BeforeClass
	public static void initialize()
	{
		queryStatement=new QueryStatement();
	}
	/*
	@Test
	public void selectAllWithoutWhereTestCase(){
		
		DataSet dataSet=queryStatement.executeQuery("select * from d:/emp.csv");
		assertNotNull(dataSet);
		display("selectAllWithoutWhereTestCase",dataSet);
		
	}

	@Test
	public void selectColumnsWithoutWhereTestCase(){
		
		DataSet dataSet=queryStatement.executeQuery("select Name,City,Salary from D:/Emp.csv");
		assertNotNull(dataSet);
		display("selectColumnsWithoutWhereTestCase",dataSet);
		
	}
	
	@Test
	public void withWhereGreaterThanTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select City,Name,Salary from d:/emp.csv where Salary>30000");
		assertNotNull(dataSet);
		display("withWhereGreaterThanTestCase",dataSet);
		
	}
	
	@Test
	public void withWhereLessThanTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select City,Name,Salary from d:/emp.csv where Salary<35000");
		assertNotNull(dataSet);
		display("withWhereLessThanTestCase",dataSet);
		
	}
	
	@Test
	public void withWhereLessThanOrEqualToTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select City,Name,Salary from d:/emp.csv where Salary<=35000");
		assertNotNull(dataSet);
		display("withWhereLessThanOrEqualToTestCase",dataSet);
		
	}

	@Test
	public void withWhereGreaterThanOrEqualToTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select city,name,salary from d:/emp.csv where salary>=35000");
		assertNotNull(dataSet);
		display("withWhereLessThanOrEqualToTestCase",dataSet);
		
	}
	
	@Test
	public void withWhereNotEqualToTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select city,name,salary from d:/emp.csv where salary>=35000");
		assertNotNull(dataSet);
		display("withWhereNotEqualToTestCase",dataSet);
		
	}
	*/
	@Test
	public void withWhereEqualAndNotEqualTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select City,Name,Salary from d:/emp.csv where Salary>=30000 and Salary<=38000");
		assertNotNull(dataSet);
		display("withWhereEqualAndNotEqualTestCase",dataSet);
		
	}
	/*
	@Test
	public void selectCountColumnsWithoutWhereTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select count(name) from d:/emp.csv");
		assertNotNull(dataSet);
		display("selectCountColumnsWithoutWhereTestCase",dataSet);
		
	}
	
	@Test
	public void selectSumColumnsWithoutWhereTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select sum(salary) from d:/emp.csv");
		assertNotNull(dataSet);
		display("selectSumColumnsWithoutWhereTestCase",dataSet);
		
	}
	
	@Test
	public void selectSumColumnsWithWhereTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select sum(salary) from d:/emp.csv where city=Bangalore");
		assertNotNull(dataSet);
		display("selectSumColumnsWithWhereTestCase",dataSet);
		
	}
	
	@Test
	public void selectColumnsWithoutWhereWithOrderByTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select city,name,salary from d:/emp.csv order by salary");
		assertNotNull(dataSet);
		display("selectColumnsWithoutWhereWithOrderByTestCase",dataSet);
		
	}
	
	@Test
	public void selectColumnsWithWhereWithOrderByTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select city,name,salary from d:/emp.csv where city=Bangalore order by salary");
		assertNotNull(dataSet);
		display("selectColumnsWithoutWhereWithOrderByTestCase",dataSet);
		
	}
	
	@Test
	public void selectColumnsWithoutWhereWithGroupByCountTestCase(){
		
		DataSet  dataSet=queryStatement.executeQuery("select city,count(*) from d:/emp.csv group by city");
		assertNotNull(dataSet);
		display("selectColumnsWithoutWhereWithOrderByTestCase",dataSet);
		
	}
	
	@Test
	public void selectColumnsWithoutWhereWithGroupBySumTestCase(){
		
		DataSet dataSet=queryStatement.executeQuery("select city,sum(salary) from d:/emp.csv group by city");
		assertNotNull(dataSet);
		display("selectColumnsWithoutWhereWithOrderByTestCase",dataSet);
		
	}
	
	*/
	private void display(String testCaseName,DataSet dataSet){
		System.out.println(testCaseName);
		System.out.println("================================================================");
		
		for(DataRow rowData:dataSet.getResultSet())
		{
			Set<Integer> rowIndex=rowData.keySet();
			
			for(int index:rowIndex)
			{
				System.out.print(rowData.get(index)+"\t");
			}
			
			System.out.println();
		}
	


		System.out.println(dataSet);
	}
	

}
