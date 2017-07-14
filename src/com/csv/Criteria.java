package com.csv;


public class Criteria {

	String conditionColumnName,relationalOperator,conditionValue;
	
	public String getConditionColumnName() {
		return conditionColumnName;
	}
	public void setConditionColumnName(String conditionColumnName) {
		this.conditionColumnName = conditionColumnName;
	}
	public String getRelationalOperator() {
		return relationalOperator;
	}
	public void setRelationalOperator(String relationalOperator) {
		this.relationalOperator = relationalOperator;
	}
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
	
	
}
