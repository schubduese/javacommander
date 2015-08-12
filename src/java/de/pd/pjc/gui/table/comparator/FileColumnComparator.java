/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.table.comparator;

import java.util.Comparator;

import de.pd.pjc.bean.FileElement;

public class FileColumnComparator implements Comparator<Object> {

	@Override
	public int compare(Object pO1, Object pO2) {
		
		System.out.println(pO1.getClass().getName());
		
		FileElement fileElement1 = (FileElement) pO1;
		FileElement fileElement2 = (FileElement) pO2;
		
		if (fileElement1.isTopElement())
			return -1;

		if (fileElement1.isDirectory() && !fileElement2.isDirectory()) {
			return 0;
		}
		
		return pO1.toString().compareTo(pO2.toString());
	}

}


/*
 * $Log$
 */