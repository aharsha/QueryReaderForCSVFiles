package com.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.omg.CosNaming.IstringHelper;

public class FetchCsvData {
	Query query;

	public FetchCsvData(Query query) {
		this.query = query;
	}

	public DataSet getCsvData() {
		DataSet dataSet = new DataSet();
		HeaderRow headerRow;
		BufferedReader bufferedReader;
		DataRow rowData;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(query.getFilePath()));

			bufferedReader.readLine();

			String row;
			String rowValues[];
			while ((row = bufferedReader.readLine()) != null) {
				int rowCount = 0;
				rowData = new DataRow();

				 rowValues = row.trim().split(",");

				int columnCount = rowValues.length;
				if (!(query.isHasGroupBy())&&query.isHasAggregate()) {

					while (rowCount < columnCount) {
						rowData.put(rowCount, rowValues[rowCount].trim());
						rowCount++;
					}
				}
				
				if (query.isHasAggregate()&&query.isHasGroupBy()) {
					
					//LinkedHashMap<String,Float> aggregateInfo = new LinkedHashMap<String, Float>();
					int x;
					String aggregateFunctionName = null, aggregateFunctionColumnName = null;
					for (String selectedColumnName : query.getColumNames().getColumns()) {
						
						
						 if(selectedColumnName.contains("sum")||selectedColumnName.contains("max")||selectedColumnName.contains("min")||selectedColumnName.contains("avg")||selectedColumnName.contains("count")||selectedColumnName.contains("count(*)"))
					{
							 x = selectedColumnName.indexOf('(');
								x++;
								aggregateFunctionColumnName = selectedColumnName.substring(x, selectedColumnName.indexOf(')'));
								aggregateFunctionName = selectedColumnName.substring(0, selectedColumnName.indexOf("("));
								query.setAggregateFunctionName(aggregateFunctionName);
								query.setAggregateFunctionColumnName(aggregateFunctionColumnName);
					}
						 
						 
					}
				
					List<String> columnNamess=query.getColumNames().getColumns();
					
					columnNamess.add(aggregateFunctionColumnName);
					headerRow = query.getHeaderRow();
					Set<String> fileColumnNames = headerRow.keySet();
					for (String queryColumnName :columnNamess) {
						for (String fileColumnName : fileColumnNames) {

							if (queryColumnName.equalsIgnoreCase(fileColumnName)) {
								rowData.put(headerRow.get(queryColumnName),
										rowValues[headerRow.get(queryColumnName)].trim());
							}
						}
					}
				}

				if (!query.isHasAllColumn()) {
					headerRow = query.getHeaderRow();

					Set<String> fileColumnNames = headerRow.keySet();
					for (String queryColumnName : query.getColumNames().getColumns()) {
						for (String fileColumnName : fileColumnNames) {

							if (queryColumnName.equalsIgnoreCase(fileColumnName)) {
								rowData.put(headerRow.get(queryColumnName),
										rowValues[headerRow.get(queryColumnName)].trim());
							}
						}
					}
				}

				else if(query.isHasAllColumn()) {

					while (rowCount < columnCount) {
						rowData.put(rowCount, rowValues[rowCount].trim());
						rowCount++;
					}

				}
				if (query.isHasWhere()) {
					if (evaluateWhereCondition(query, rowValues)) {
						dataSet.getResultSet().add(rowData);
					}
				} else {
					dataSet.getResultSet().add(rowData);

				}
			}

			if (query.isHasOrderBy()) {
				SortRows sortRowData = new SortRows();
				sortRowData.setSortingIndex(query.getHeaderRow().get(query.getOrderByColumn()));
				Collections.sort(dataSet.getResultSet(), sortRowData);
			}

			if (!(query.isHasGroupBy())&&query.isHasAggregate()) {
				LinkedHashMap<String, Float> aggregateInfo = new LinkedHashMap<String, Float>();
				int x;
				String aggregateFunctionName = null, aggregateFunctionColumnName = null;
				for (String selectedColumnName : query.getColumNames().getColumns()) {
					
					
					 if(selectedColumnName.contains("sum")||selectedColumnName.contains("max")||selectedColumnName.contains("min")||selectedColumnName.contains("avg")||selectedColumnName.contains("count")||selectedColumnName.contains("count(*)"))
				{
						 x = selectedColumnName.indexOf('(');
							x++;
							aggregateFunctionColumnName = selectedColumnName.substring(x, selectedColumnName.indexOf(')'));
							aggregateFunctionName = selectedColumnName.substring(0, selectedColumnName.indexOf("("));
				}
				else
				{
					continue;
					
				}
					 
					
				
					
				} // for aggregateFunctionColumnName aggregateFunctionName

				Float sum = 0f, max = 0f, min = 0f, count = 0f;
				headerRow = query.getHeaderRow();
				Set<String> fileColumnNames = headerRow.keySet();
				for (String fileColumnName : fileColumnNames) {

					if (aggregateFunctionColumnName.equalsIgnoreCase(fileColumnName)) {

						if (aggregateFunctionName.equals("sum")) {
							sum = sumAggregate(dataSet, headerRow, aggregateFunctionColumnName);
							aggregateInfo.put(aggregateFunctionColumnName, sum);
							dataSet.setAggregateInfo(aggregateInfo);
						} // if sum

						if (aggregateFunctionName.equals("max")) {

							max = maxAggregate(dataSet, headerRow, aggregateFunctionColumnName);

							aggregateInfo.put(aggregateFunctionColumnName, max);
							dataSet.setAggregateInfo(aggregateInfo);
						} // if max

						if (aggregateFunctionName.equals("min")) {
							
							min=minAggregate(dataSet, headerRow, aggregateFunctionColumnName);
														aggregateInfo.put(aggregateFunctionColumnName, min);
							dataSet.setAggregateInfo(aggregateInfo);
						} // if min

						if (aggregateFunctionName.equals("count")) {
							
							count=countAggregate(dataSet, headerRow, aggregateFunctionColumnName);
							aggregateInfo.put(aggregateFunctionColumnName, count);
							dataSet.setAggregateInfo(aggregateInfo);

							
						} // if count

					} // if->aggregate functionName match with columnName
				}

			} // if->hasAggregate function

			if (query.isHasGroupBy()) {
				LinkedHashMap<String, Float> groupByInfo = new LinkedHashMap<String, Float>();
				
				headerRow=query.getHeaderRow();
				for(DataRow rowsData:dataSet.getResultSet())
				{
								
				
				String temp1=rowsData.get(headerRow.get(query.getGroupByColumn()));
				Float temp2=Float.parseFloat(rowsData.get(headerRow.get(query.getAggregateFunctionColumnName())));
					
					if(groupByInfo.containsKey(temp1))
					{
						Float temp3=groupByInfo.get(temp1);
						if(query.getAggregateFunctionName().equals("sum"))
						{
						groupByInfo.put(temp1,(temp2+temp3));	
						}
						
						else
							if(query.getAggregateFunctionName().equals("max"))
							{
								if(temp2>temp3){
							groupByInfo.put(temp1,temp2);	}
							}
						
							else
								if(query.getAggregateFunctionName().equals("min"))
								{
									if(temp2<temp3){
										groupByInfo.put(temp1,temp2);	}
								}
					}
					else 
					{
						groupByInfo.put(temp1,(temp2));	
					}
					
					dataSet.setGroupByInfo(groupByInfo);
				}
					}//if 
					
						
					
					
						
						

					
				
			
				headerRow = query.getHeaderRow();
				//float sum=sumAggregate(dataSet, headerRow, );
				
				System.out.println(query.getGroupByColumn());
				
			
			
		} // try

		catch (

		Exception e) {
			e.printStackTrace();
		}
		return dataSet;
	}

	private boolean evaluateWhereCondition(Query query, String rowValues[]) {
		List<Criteria> criteariaList = query.getCriteriaList();
		HeaderRow headerRow = query.getHeaderRow();
		List<String> logicalOperators = query.getLogicalOperator();

		boolean expression = evaluateCriteria(criteariaList.get(0), rowValues, headerRow);

		int logicalOperatorCount = logicalOperators.size();
		int count = 0;

		if (logicalOperatorCount > 0) {
			while (count < logicalOperatorCount) {
				if (logicalOperators.get(count).equals("and")) {
					expression = expression && evaluateCriteria(criteariaList.get(count + 1), rowValues, headerRow);
				} else {
					expression = expression || evaluateCriteria(criteariaList.get(count + 1), rowValues, headerRow);
				}

				count++;
			}
		}

		return expression;
	}

	private boolean evaluateCriteria(Criteria criteria, String rowValues[], HeaderRow headerRow) {

		boolean expression = false;

		String conditionColumnName = criteria.getColumn();
		String conditionOperator = criteria.getOperator();
		String conditionValue = criteria.getValue();

		String columnValue = rowValues[headerRow.get(conditionColumnName)].trim();
		boolean isString = evaluateDataType(columnValue);

		if (isString) {
			if (conditionOperator.equals("=")) {
				if (columnValue.equals(conditionValue)) {
					expression = true;
				}
			} else if (conditionOperator.equals("!=")) {
				if (!columnValue.equals(conditionValue)) {
					expression = true;
				}
			}
		} else {
			float parsedConditionValue = Float.parseFloat(conditionValue);
			float parsedRowDataValue = Float.parseFloat(rowValues[query.getHeaderRow().get(conditionColumnName)]);
			switch (conditionOperator) {
			case ">=":
				expression = parsedRowDataValue >= parsedConditionValue;
				break;
			case "<=":
				expression = parsedRowDataValue <= parsedConditionValue;
				break;
			case ">":
				expression = parsedRowDataValue > parsedConditionValue;
				break;
			case "<":
				expression = parsedRowDataValue < parsedConditionValue;
				break;
			case "=":
				expression = parsedRowDataValue == parsedConditionValue;
				break;
			case "!=":
				expression = parsedRowDataValue != parsedConditionValue;
				break;
			}
		}

		return expression;
	}

	private boolean evaluateDataType(String value) {
		// try {
		// Float f = Float.parseFloat(value);
		// return false;
		// } catch (Exception e) {
		// return true;
		// }

		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(value, pos);
		return !(value.length() == pos.getIndex());
	}

	float sumAggregate(DataSet dataSet, HeaderRow headerRow, String aggregateFunctionColumnName) {
		float sum = 0f;
		for (DataRow rows : dataSet.getResultSet()) {

			sum = sum + Float.parseFloat(rows.get(headerRow.get(aggregateFunctionColumnName)));
			// System.out.println(rows.get(headerRow.get("salary")));
			// System.out.println(rows.get());
			// System.out.println("summmmmmmmmmmmmmmmm"+sum);

		} // for->get Data from DataSet and read each row for aggregation

		return sum;

	}

	float maxAggregate(DataSet dataSet, HeaderRow headerRow, String aggregateFunctionColumnName) {
		float max = 0f;
		for (DataRow rows : dataSet.getResultSet()) {
			Float tempMax = Float.parseFloat(rows.get(headerRow.get(aggregateFunctionColumnName)));
			if (max < tempMax) {
				max = tempMax;
			}

		}
		return max;
	}
	
	
	Float minAggregate(DataSet dataSet, HeaderRow headerRow, String aggregateFunctionColumnName) {
		
		
		Float firstRowValue = 0f, tempMin = 0f,min=0f; 
		int rowCount = 0;
		for (DataRow rows : dataSet.getResultSet()) {
			if (rowCount == 0) {
				firstRowValue = Float
						.parseFloat(rows.get(headerRow.get(aggregateFunctionColumnName)));
				rowCount++;
			} else {
				tempMin = Float.parseFloat(rows.get(headerRow.get(aggregateFunctionColumnName)));
				// System.out.println(firstRowValue+"================="+tempMin);
			}
			if (firstRowValue < tempMin) {

				min = firstRowValue;
			}

		} // for->get Data from DataSet and read each row
			// for aggregation


		return min;
	}
	
	

	Float countAggregate(DataSet dataSet, HeaderRow headerRow, String aggregateFunctionColumnName) {
		
		String tempRowValue;
		Float rowCount = 0f,count=0f;
		for (DataRow rows : dataSet.getResultSet()) {

			tempRowValue = rows.get(headerRow.get(aggregateFunctionColumnName));
			if (tempRowValue != null) {
				rowCount++;
			}

		} // for->get Data from DataSet and read each row
			// for aggregation
		count = rowCount;			// for aggregation


		return count;
	}
	
	
	
	
	
	
}
