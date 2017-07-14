package com.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QueryParser {

	Query query = new Query();;
	Criteria criteria;

	

	private boolean isValidQueryString(String qrystring) {
		if (qrystring.contains("select") && qrystring.contains("from") || (qrystring.contains("where")
				|| qrystring.contains("order by") || qrystring.contains("group by") || qrystring.contains(""))) {
			return true;
		} else {
			return false;
		}
	}

	public DataSet queryProcessing(HeaderRow headerrow, Query query) {
		DataSet ds = new DataSet();

		return ds;
	}

	public Query parseQuery(String queryString) {
		String queryBit = null, conditionQuery = null;
		if (isValidQueryString(queryString)) {

			if (queryString.contains("order by")) {

				queryBit = queryString.split("order by")[0].trim();
				query.setFilepath(queryBit.split("from")[1].trim());

				query.setOrderByColumn(queryString.split("order by")[1].trim());

				queryBit = queryBit.split("from")[0].trim();
				String columnString = queryBit.split("select")[1].trim();
				this.parseColumn(columnString);
				query.setHasOrderBy(true);

			}
			if (queryString.contains("group by")) {
				queryBit = queryString.split("group by")[0].trim();
				query.setGroupByColumn(queryString.split("group by")[1].trim());

				if (queryBit.contains("where")) {
					conditionQuery = queryBit.split("where")[1].trim();
					String condition = conditionQuery.split("and|or")[0].trim();

					this.relationalExpressionProcessing(condition);

					queryBit = queryBit.split("where")[0].trim();
				}

				query.setFilepath(queryBit.split("from")[1].trim());
				queryBit = queryBit.split("from")[0].trim();

				this.parseColumn(queryBit.split("select")[1].trim());
				query.setHasGroupBy(true);
			} else if (queryString.contains("where")) {
				queryBit = queryString.split("where")[0];

				conditionQuery = queryString.split("where")[1];

				conditionQuery = conditionQuery.trim();
				conditionQuery = conditionQuery.split("order by|group by")[0];
				query.setFilepath(queryBit.split("from")[1].trim());
				this.relationalExpressionProcessing(conditionQuery);
				String findColumn = queryString.split("from")[0].trim();
				this.parseColumn(findColumn.split("select")[1].trim());
				query.setHasWhere(true);
			} else {
				queryBit = queryString.split("from")[0].trim();
				query.setFilepath(queryBit.split("from")[1].trim());

				this.parseColumn(queryBit.split("select")[1].trim());
			}

		} else {
			System.out.println("Query String is not in Proper Format");
		}

		return query;
	}

	private void parseColumn(String selectColumns) {

		if (selectColumns.trim().contains("*") && selectColumns.length() == 1) {
			query.setHasAllColumns(true);

		}
		if (selectColumns.trim().contains(",")) {

			query.setSelectColumns(selectColumns.split(","));

		}
	}

	private void relationalExpressionProcessing(String relationalQuery) {

		List<Criteria> criteriaList = new ArrayList<Criteria>();

		String conditionParts[] = relationalQuery.split("and|or");

		for (String c : conditionParts) {

			String oper[] = { "<", ">", ">=", "<=", "=", "!=" };
			for (String operator : oper) {
				if (c.contains(operator)) {
					criteria = new Criteria();
					criteria.setConditionColumnName(c.split(operator)[0].trim());
					criteria.setConditionValue(c.split(operator)[1].trim());
					criteria.setRelationalOperator(operator);
					criteriaList.add(criteria);

					break;

				} // if

			} // inner for

		} // outer for
		query.setCriteriaList(criteriaList);
	}

}
