package com.csv;

import java.util.Scanner;

public class QueryReader {

	public static void main(String[] args) {
		FetchCsvFile csvFile = new FetchCsvFile();
		Query query;
		QueryParser queryParser;
		String queryString;
		Scanner scanner;
		
		
		
		queryParser = new QueryParser();
		scanner = new Scanner(System.in);
		
		
		
		System.out.println("Enter Your Query");
		queryString = scanner.next();
		

		try {
			query = queryParser.parseQuery(queryString);
			DataSet dataSet = csvFile.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}

	}

}
