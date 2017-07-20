package com.csv;
import java.util.*;

public class DataSet
{
	
	private LinkedHashMap<String,Float> aggregateInfo;
	


	private List<DataRow> resultSet=new ArrayList();
	
	private LinkedHashMap<String,Float> groupByInfo;

	public LinkedHashMap<String, Float> getGroupByInfo() {
		return groupByInfo;
	}

	public void setGroupByInfo(LinkedHashMap<String, Float> groupByInfo) {
		this.groupByInfo = groupByInfo;
	}

	public List<DataRow> getResultSet() 
	{
		return resultSet;
	}

	public void setResultSet(List<DataRow> resultSet) 
	{
		this.resultSet = resultSet;
	}

	public LinkedHashMap<String, Float> getAggregateInfo() {
		return aggregateInfo;
	}

	public void setAggregateInfo(LinkedHashMap<String, Float> aggregateInfo) {
		this.aggregateInfo = aggregateInfo;
	}
	

	
}
