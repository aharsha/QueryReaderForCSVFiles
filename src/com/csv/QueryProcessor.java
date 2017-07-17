package com.csv;

import java.util.Collections;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Set;

public class QueryProcessor {
	Query query;

	public QueryProcessor(Query query) {
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
			while ((row = bufferedReader.readLine()) != null) {
				int count = 0;
				rowData = new DataRow();

				String rowValues[] = row.trim().split(",");
				int columnCount = rowValues.length;

				if (!query.isHasAllColumn()) {
					headerRow = query.getHeaderRow();
					Set<String> fileColumnNames = headerRow.keySet();
					for (String queryColumnName : query.getColumNames().getColumns()) {
						for (String fileColumnName : fileColumnNames) {
							if (queryColumnName.equals(fileColumnName)) {
								rowData.put(headerRow.get(queryColumnName),
										rowValues[headerRow.get(queryColumnName)].trim());
							}
						}
					}
				} else {
					while (count < columnCount) {
						rowData.put(count, rowValues[count].trim());
						count++;
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
				SortData sortData = new SortData();
				sortData.setSortingIndex(query.getHeaderRow().get(query.getOrderByColumn()));
				Collections.sort(dataSet.getResultSet(), sortData);
			}

			if (query.isHasAggregate()) {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSet;
	}

	private boolean evaluateWhereCondition(Query query, String rowValues[]) {
		List<Criteria> criteariaList = query.getListrelexpr();
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
//		try {
//			Float f = Float.parseFloat(value);
//			return false;
//		} catch (Exception e) {
//			return true;
//		}
		
		NumberFormat formatter = NumberFormat.getInstance();
		  ParsePosition pos = new ParsePosition(0);
		  formatter.parse(value, pos);
		  return !(value.length() == pos.getIndex());
}

}