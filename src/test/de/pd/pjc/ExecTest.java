package de.pd.pjc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
	        // Execute a command with an argument that contains a space
	        String[] commands = new String[]{"xeyes"};
//	        commands = new String[]{"grep", "hello world", "c:\\Documents and Settings\\f.txt"};
//	        Process process = Runtime.getRuntime().exec(commands, null, new File("/home"));
	        
	        ProcessBuilder builder = new ProcessBuilder("mplayer.sh");
	        builder.directory(new File("/tmp"));
	        builder.start();
	        
	        
//	        String line = "";
//	        BufferedReader input =
//	           new BufferedReader
//	             (new InputStreamReader(process.getInputStream()));
//	         while ((line = input.readLine()) != null) {
//	           System.out.println(line);
//	           }
//	         input.close();
	         
	         File testFile = new File("/home/petros/mbox");
	         System.out.println(testFile.getParent());
	        
	    } catch (IOException e) {
	   	 e.printStackTrace();
	    }

	}

}
