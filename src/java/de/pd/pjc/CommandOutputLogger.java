package de.pd.pjc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandOutputLogger implements Runnable {

	private Process mProcess;
	
	protected Log mLog = LogFactory.getLog(CommandOutputLogger.class.getName());
	
	public CommandOutputLogger(Process pProcess) {
		super();
		mProcess = pProcess;
	}

	public void run() {
		String line = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(mProcess
				.getInputStream()));
		try {
			while ((line = input.readLine()) != null) {
				mLog.debug(line);
			}
			input.close();
		} catch (IOException e) {
			mLog.warn(e.getMessage());
		}
		
	}

}
