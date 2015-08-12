package de.pd.pjc;

public class BaseTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inStr = "*.mp3";
		String str1 = inStr.replaceAll("\\*.", "\\.*");
		
		System.out.println(inStr);
		System.out.println(str1);
	}

}
