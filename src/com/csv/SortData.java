package com.csv;

import java.util.Comparator;


public class SortData implements Comparator<DataRow> 
{
	private int sortingIndex;
	
	public int getSortingIndex() 
	{
		return sortingIndex;
	}

	public void setSortingIndex(int sortingIndex) 
	{
		this.sortingIndex = sortingIndex;
	}

	@Override
	public int compare(DataRow arg0, DataRow arg1) 
	{
		return arg0.get(sortingIndex).compareTo(arg1.get(sortingIndex));
	}

}
