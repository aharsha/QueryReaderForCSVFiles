package com.csv;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Query {
	

	private String filePath;
	private String orderByColumn,groupByColumn;
	private SelectColumns columNames;
	private List<Criteria> restrictions;
	private boolean hasGroupBy=false,hasOrderBy=false,hasWhere=false,hasAllColumn=false,hasColumn=false,hasSimpleQuery,hasAggregate=false;
	private List<String> logicalOperator;
	private HeaderRow headerRow;
	
	public HeaderRow getHeaderRow() 
	{
		return headerRow;
	}

	public List<String> getLogicalOperator() {
		return logicalOperator;
	}

	public SelectColumns getColumNames() {
		return columNames;
	}

	public void setColumNames(SelectColumns columnNames) {
		this.columNames = columnNames;
	}

	public List<Criteria> getListrelexpr() {
		return restrictions;
	}

	public void setListrelexpr(List<Criteria> listrelexpr) {
		this.restrictions = listrelexpr;
	}

	public String getOrderByColumn() 
	{
		return orderByColumn;
	}

	public Query(String queryString)throws Exception
	{
		columNames=new SelectColumns();
		restrictions=new ArrayList();
		logicalOperator=new ArrayList<String>();
		this.parseQuery(queryString);
		headerRow=this.setHeaderRow();
	}
	
	public Query parseQuery(String queryString)
	{
		String baseQuery=null,conditionQuery=null,selectcol=null;
		
		if(queryString.contains("order by"))
		{
			baseQuery=queryString.split("order by")[0].trim();
			orderByColumn=queryString.split("order by")[1].trim();
			if(baseQuery.contains("where"))
			{
				conditionQuery=baseQuery.split("where")[1].trim();
				this.relationalExpressionProcessing(conditionQuery);
				baseQuery=baseQuery.split("where")[0].trim();
				hasWhere=true;
			}
			filePath=baseQuery.split("from")[1].trim();
			baseQuery=baseQuery.split("from")[0].trim();
			selectcol=baseQuery.split("select")[1].trim();
			this.fieldsProcessing(selectcol);
			hasOrderBy=true;
		}
		else if(queryString.contains("group by"))
		{
			baseQuery=queryString.split("group by")[0].trim();
			groupByColumn=queryString.split("group by")[1].trim();
			if(baseQuery.contains("where"))
			{
				conditionQuery=baseQuery.split("where")[1].trim();
				this.relationalExpressionProcessing(conditionQuery);
				baseQuery=baseQuery.split("where")[0].trim();
				hasWhere=true;
			}
			filePath=baseQuery.split("from")[1].trim();
			baseQuery=baseQuery.split("from")[0].trim();
			selectcol=baseQuery.split("select")[1].trim();
			this.fieldsProcessing(selectcol);
			hasGroupBy=true;
		}
		else if(queryString.contains("where"))
		{
			baseQuery=queryString.split("where")[0];
			conditionQuery=queryString.split("where")[1];
			conditionQuery=conditionQuery.trim();
			filePath=baseQuery.split("from")[1].trim();
			baseQuery=baseQuery.split("from")[0].trim();
			this.relationalExpressionProcessing(conditionQuery);
			selectcol=baseQuery.split("select")[1].trim();
			this.fieldsProcessing(selectcol);
			hasWhere=true;
		}
		else
		{
			baseQuery=queryString.split("from")[0].trim();
			filePath=queryString.split("from")[1].trim();
			selectcol=baseQuery.split("select")[1].trim();
			this.fieldsProcessing(selectcol);
			hasSimpleQuery=true;
		}
		
		return this;	
	}
	
	private void relationalExpressionProcessing(String conditionQuery)
	{
		String oper[]={">=","<=",">","<","!=","="};
		
		String relationalQueries[]=conditionQuery.split("and|or");
		
		for(String relationQuery:relationalQueries)
		{	
			relationQuery=relationQuery.trim();
			for(String operator:oper)
			{
				if(relationQuery.contains(operator))
				{
					Criteria criteria=new Criteria();
					criteria.setColumn(relationQuery.split(operator)[0].trim());
					criteria.setValue(relationQuery.split(operator)[1].trim());
					criteria.setOperator(operator);
					restrictions.add(criteria);
					break;
				}
			}
		}
		
		if(restrictions.size()>1)
			this.logicalOperatorStore(conditionQuery);
	}
	
	private void logicalOperatorStore(String conditionQuery)
	{
		String conditionQueryData[]=conditionQuery.split(" ");
		
		for(String queryData:conditionQueryData)
		{
			queryData=queryData.trim();
			if(queryData.equals("and")||queryData.equals("or"))
			{
				logicalOperator.add(queryData);
			}
		}
	}
	
	public String getFilePath() {
		return filePath;
	}

	public String getGroupByColumn() {
		return groupByColumn;
	}

	public boolean isHasGroupBy() {
		return hasGroupBy;
	}

	public boolean isHasOrderBy() {
		return hasOrderBy;
	}

	public boolean isHasWhere() {
		return hasWhere;
	}

	public boolean isHasAllColumn() {
		return hasAllColumn;
	}

	public boolean isHasColumn() {
		return hasColumn;
	}

	public boolean isHasSimpleQuery() {
		return hasSimpleQuery;
	}

	public boolean isHasAggregate() {
		return hasAggregate;
	}

	private void fieldsProcessing(String selectColumn)
	{
		if(selectColumn.trim().contains("*") && selectColumn.trim().length()==1)
		{
			hasAllColumn=true;
		}
		else
		{
			String columnList[]=selectColumn.trim().split(",");
			
			for(String column:columnList)
			{
				columNames.getColumns().add(column.trim());
			}
			
			if(selectColumn.contains("sum") || selectColumn.contains("count")||selectColumn.contains("count(*)"))
			{
				hasAggregate=true;
			}
		}
	}
	
	public HeaderRow setHeaderRow() throws Exception
	{
		BufferedReader bufferedReader=new BufferedReader(new FileReader(getFilePath()));
		HeaderRow headerRow=new HeaderRow();
		
		if(bufferedReader!=null)
		{
			String rowData=bufferedReader.readLine();
			String rowValues[]=rowData.split(",");
			int columnIndex=0;
			for(String rowvalue:rowValues)
			{
				headerRow.put(rowvalue,columnIndex);
				columnIndex++;
			}
		}
		
		return headerRow;
	}


}
