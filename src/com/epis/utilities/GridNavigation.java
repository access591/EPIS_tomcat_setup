/**
  * File       : GridNavigation.java
  * Date       : 08/07/2007
  * Author     : AIMS 
  * Description: 
  * Copyright (2007) by the Navayuga Infotech, all rights reserved.
  */
package com.epis.utilities;

public class GridNavigation {
	Log log=new Log(GridNavigation.class);
	public int startIndex(String navigationButton, int currentIndex,
			int gridLength,int rowCount) {
		log.info("GridNavigation :startIndex() enter method");
		int from, to;
		//String nav = "1";
		from = currentIndex;
		if (navigationButton.equals("|<")) {
			to = 1;
			from = to;
		}
		if (navigationButton.equals(">")) {
			if (rowCount <= gridLength + 1)
				to = 1;
			
			else
				to = rowCount - gridLength + 1;
			to = from + gridLength;
			from = to;
		}
		if (navigationButton.equals("<")) {
			to = from - gridLength;
			if (to < 1)
				to = 1;
			from = to;
		}

		if (navigationButton.equals(">|")) {
			if (rowCount < gridLength + 1){
				to = 1;
			}
			else if(rowCount%gridLength !=0){
				to=rowCount-(rowCount%gridLength)+1;
			}
			else{
				to = rowCount - (gridLength-1);
			}
			from = to;
		}
		log.info("GridNavigation :startIndex() Leaving method");
		return from;
	}
}
