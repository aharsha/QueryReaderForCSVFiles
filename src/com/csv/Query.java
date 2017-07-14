package com.csv;

import java.util.ArrayList;
import java.util.List;

public class Query {

	private String queryString,orderByColumn, groupByColumn, filepath, aggregateFunction, selectColumns[],logicalOperators[];


	private boolean hasOrderBy, hasGroupBy, hasWhere, hasAllColumns;

	private List<Criteria> criteriaList = new ArrayList<Criteria>();
	
	
	

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public List<Criteria> getCriteriaList() {
		return criteriaList;
	}

	public String getAggregateFunction() {
		return aggregateFunction;
	}

	public void setAggregateFunction(String aggregateFunction) {
		this.aggregateFunction = aggregateFunction;
	}

	public String[] getLogicalOperators() {
		return logicalOperators;
	}

	public void setLogicalOperators(String[] logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	public void setCriteriaList(List<Criteria> criteriaList) {
		this.criteriaList = criteriaList;
	}

	public boolean isHasWhere() {
		return hasWhere;
	}

	public void setHasWhere(boolean hasWhere) {
		this.hasWhere = hasWhere;
	}

	public boolean isHasGroupBy() {
		return hasGroupBy;
	}

	public void setHasGroupBy(boolean hasGroupBy)

	{
		this.hasGroupBy = hasGroupBy;

	}

	public String[] getSelectColumns() {
		return selectColumns;
	}

	public void setSelectColumns(String[] selectColumns) {
		this.selectColumns = selectColumns;
	}

	public String getGroupByColumn() {
		return groupByColumn;
	}

	public void setGroupByColumn(String groupByColumn) {
		this.groupByColumn = groupByColumn;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public boolean isHasOrderBy() {
		return hasOrderBy;
	}

	public void setHasOrderBy(boolean hasOrderBy) {
		this.hasOrderBy = hasOrderBy;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public boolean isHasAllColumns() {
		return hasAllColumns;
	}

	public void setHasAllColumns(boolean hasAllColumns) {
		this.hasAllColumns = hasAllColumns;
	}

}
