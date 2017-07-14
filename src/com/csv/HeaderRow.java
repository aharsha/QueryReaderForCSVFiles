package com.csv;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
public class HeaderRow 
{
	
	LinkedHashMap<String,Integer> firstRow=new LinkedHashMap<>();	
	
	public LinkedHashMap<String, Integer> getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(LinkedHashMap<String, Integer> firstRow) {
		this.firstRow = firstRow;
	}

	public Map.Entry<String,Integer> getHeadersWithIndex(LinkedHashMap<String,Integer> headerRow)
	{
		Set s=headerRow.entrySet();
		
	Iterator iterator=	s.iterator();
	Map.Entry<String ,Integer> headerKeyValue=(Map.Entry<String, Integer>)iterator.next();
	
	
		
		return headerKeyValue;
	}
	
	void disp(LinkedHashMap<String,Integer> headerRow)
	{
		Map.Entry<String,Integer> me=getHeadersWithIndex(headerRow);
		System.out.println(me.getKey());
		System.out.println(me.getValue());
		
	}
}
