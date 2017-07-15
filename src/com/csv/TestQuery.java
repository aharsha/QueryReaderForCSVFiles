package com.csv;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Generated;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestQuery {

	static QueryParser queryParser;
	static Query query;
	static HeaderRow headerRow;
	static FetchCsvFile fetchCsv;
	static String queryString;
	static DataSet dataSet;
	static List<Integer> columnIndexes;
	static LinkedHashMap<String, Integer> headerInfo;
	static LinkedHashMap<String, Integer> expectedHeaders, actualHeaders;

	@BeforeClass
	public static void intialise() throws Exception {

		queryString = "select empid,empname from  d://employee.csv where empid=101 and empname='raju' order by empname";
		fetchCsv = new FetchCsvFile();
		queryParser = new QueryParser();

		query = queryParser.parseQuery(queryString);
		headerRow = fetchCsv.getHeaderRow(query);
		headerInfo = headerRow.getFirstRow();
		dataSet = fetchCsv.getAllRows(query);
		columnIndexes = fetchCsv.columnIndexs(query);
		// dataSet = fetchCsv.getAllRowsExperiment(query);

		expectedHeaders = new LinkedHashMap<String, Integer>();

		expectedHeaders.put("empid", 0);
		expectedHeaders.put("empname", 1);
		expectedHeaders.put("empsal", 2);
		expectedHeaders.put("empdept", 4);

		actualHeaders = new LinkedHashMap<String, Integer>();
		actualHeaders = headerRow.getFirstRow();
	}

	@Test
	public void testCsvFileName() {
		assertEquals("File Path is correct", "d://cn.csv", query.getFilepath());

	}

	@Test
	public void hasOrderBy() {
		assertEquals("havingOrder By ", true, query.isHasOrderBy());

	}

	@Test
	public void testHeaders() throws Exception {
		assertEquals("File Headers are correct", expectedHeaders, actualHeaders);

	}

	@Test
	public void displayQuery() {

		System.out.println("Query String  = " + queryString);
		System.out.println("OrderByColumn nam e = " + query.getOrderByColumn());

		System.out.println("groupByColumn Name = " + query.getGroupByColumn());
		List<Criteria> list = query.getCriteriaList();
		for (Criteria l : list) {

			System.out.println("Conditions column name  = " + l.getConditionColumnName());
			System.out.println("Conditions column value = " + l.getConditionValue());
			System.out.println("Conditions operator = " + l.getRelationalOperator());
		}
		System.out.println();
		System.out.println("Fila name  = " + query.getFilepath());
		String temp[] = query.getSelectColumns();
		for (String s : temp) {
			System.out.println("select Column names  = " + s);

		}

	}

	// @Test
	// public void displayAllColumns() {
	//
	// List<DataRow> rowList = dataSet.getRowData();
	//
	//
	// int x = 0;
	//
	// for (DataRow row : rowList) {
	//
	// LinkedHashMap<Integer, String> cellWithIndex = row.getCellData();
	// int key = 0;
	// for (; (cellWithIndex.get(key) != null);) {
	//
	// System.out.print(" " + cellWithIndex.get(key));
	// key++;
	// }
	// System.out.println();
	// }
	//
	// }
	//
	//
	@Test
	public void displaySpecificColumns() {

		List<DataRow> rowList = dataSet.getRowData();

		for (DataRow row : rowList) {

			LinkedHashMap<Integer, String> cellWithIndex = row.getCellData();
for(int columnIndex:columnIndexes)
{
			System.out.print("  " + cellWithIndex.get(columnIndex));
}
System.out.println();
		}
	}

	

}// class
