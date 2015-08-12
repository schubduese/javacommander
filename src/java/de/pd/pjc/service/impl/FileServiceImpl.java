package de.pd.pjc.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import de.pd.pjc.CommandOutputLogger;
import de.pd.pjc.Constants;
import de.pd.pjc.FilePatternFilter;
import de.pd.pjc.Settings;
import de.pd.pjc.bean.DirSettingsBean;
import de.pd.pjc.bean.FileCountBean;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.MimeApplication;
import de.pd.pjc.bean.MimeSettingsBean;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.MainWindow;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.gui.table.TableSorter;
import de.pd.pjc.service.FileService;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.util.IconFactory;
import de.pd.pjc.util.PJCUtils;

public class FileServiceImpl implements FileService {

	final static Log mLog = LogFactory.getLog(FileService.class.getName());
	private Map<String, String> mMimeMap;
	private Map<String, String> mDirIconsMap;
	private String mTerminalCommand;
	private List<String> mRcModeCommands;
	
	
	public Collection<FileElement> getFiles(String pPath) throws FileNotFoundException {
		return getFiles(pPath, null, false);
	}
	
	public Collection<FileElement> getFiles(String pPath, FilePatternFilter pPattern) throws FileNotFoundException {
		return getFiles(pPath, pPattern, false);
	}
	
	/**
	 * Returns an ArrayList with directories and files
	 * 
	 * @param path
	 *           The path, where to get the list
	 * @return the directories and files
	 */
	public ArrayList<FileElement> getFiles(String pPath, FilePatternFilter pPattern, boolean pRecursive)
			throws FileNotFoundException {

		if (!new File(pPath).exists()) {
			throw new FileNotFoundException("Path " + pPath + " does not exist");
		}

		ArrayList<FileElement> dirsAndFiles = new ArrayList<FileElement>();

		ArrayList<FileElement> dirList = new ArrayList<FileElement>();
		ArrayList<FileElement> fileList = new ArrayList<FileElement>();
		
		File[] files = new File(pPath).listFiles(pPattern);
		if(files == null) {
			return new ArrayList<FileElement>();
		}
		for (int ii = 0; ii < files.length; ii++) {
			File file = files[ii];

			if (file.getName().startsWith(".") && !SessionBean.showHiddenFiles)
				continue;
			
			if(pRecursive && file.isDirectory()) {
				dirsAndFiles.addAll(getFiles(file.getAbsolutePath(), pPattern, pRecursive));
			}

			FileElement fileElement = new FileElement(file.getName(), StringUtils.getFilenameExtension(file.getName()), 
					file.length(), new Date(file
					.lastModified()));
			fileElement.setDirectory(file.isDirectory());
			fileElement.setAbsolutePath(file.getAbsolutePath());
			fileElement.setParentPath(file.getParent());
			fileElement.setHidden(file.isHidden());
			if (file.isDirectory()) {
				dirList.add(fileElement);
			} else {
				fileList.add(fileElement);
			}

			if (fileElement.isDirectory()) {
				ImageIcon normalIcon = IconFactory.getIconByName(mDirIconsMap.get("folder_normal"));
				if(fileElement.isHidden()) {
					normalIcon = IconFactory.getIconByName(mDirIconsMap.get("folder_normal_hidden"));
				}
				ImageIcon bigIcon = IconFactory.getIconByName(mDirIconsMap.get("folder_big"));
				ImageIcon xxlIcon = IconFactory.getIconByName("folder_xxl.png");
				fileElement.setNormalIcon(normalIcon);
				fileElement.setBigIcon(bigIcon);
				fileElement.setXXLIcon(xxlIcon);
			} else {
				ImageIcon normalIcon = IconFactory.getIconByFileExt(fileElement
						.getFileName(), IconFactory.ICON_SIZE_NORMAL);
				if (normalIcon == null) {
					normalIcon = IconFactory.getIconByName("mime.png", IconFactory.EXT_IMG_PATH_NORMAL);
				}
				ImageIcon bigIcon = IconFactory.getIconByFileExt(fileElement
						.getFileName(), IconFactory.ICON_SIZE_BIG);
				if (bigIcon == null) {
					bigIcon = IconFactory.getIconByName("mime.png", IconFactory.EXT_IMG_PATH_BIG);
				}
				fileElement.setNormalIcon(normalIcon);
				fileElement.setBigIcon(bigIcon);
			}

		}

		FileElement topElement = new FileElement("..", "", 0, null);
		topElement.setDirectory(true);
		topElement.setTopElement(true);
		topElement.setParentPath(pPath);
		topElement.setBigIcon(IconFactory.getIconByName(mDirIconsMap.get("folder_big")));
		topElement.setNormalIcon(IconFactory.getIconByName(mDirIconsMap.get("folder_normal")));
		// set the absolut path for the top element (= the pPath until the last
		// separator)
		String dirUp = getDirUp(pPath);
		topElement.setAbsolutePath(dirUp);

		if (!pPath.equals(File.separator) && !pRecursive) {
			dirsAndFiles.add(topElement);
		}
		if(!pRecursive) {
			dirsAndFiles.addAll(dirList);
		}
		dirsAndFiles.addAll(fileList);

		return dirsAndFiles;
	}

	/**
	 * Returns the top directory of a absolute path;
	 * 
	 * @param pPath
	 * @return
	 */
	public String getDirUp(String pPath) {
		
		if(PJCUtils.isRootPath(pPath)) {
			return pPath;
		}
		
		int lastSep = pPath.lastIndexOf(File.separator);
		String dirUp = pPath.substring(0, lastSep);
		
		if(lastSep == 0 && PJCUtils.isLinux()) {
			dirUp = "/";
		} else if(lastSep == 2 && PJCUtils.isWindows()) {
			dirUp = pPath.substring(0, 3);
		}
		
		return dirUp;
	}
	
	public static void main(String[] args) {
		System.out.println("hallo ab".replaceAll(" ", "\\\\ "));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.pd.pjc.service.FileService#getTopDirectory(java.lang.String)
	 */
	public String getTopDirectory(String pPath) {
		if (pPath == null) {
			return null;
		}
		int lastIndex = pPath.lastIndexOf(File.separator);
		if(lastIndex != 0) {
			lastIndex ++;
		}
		if(PJCUtils.isWindows() && lastIndex == 3) {
			return pPath;
		}
		return pPath.substring(lastIndex);
	}
	
	public String getDownDirectory(String pPath) {
		if (pPath == null) {
			return null;
		}
		int lastIndex = pPath.lastIndexOf(File.separator);
		if(lastIndex == 0) {
			return File.separator;
		}
		
		return pPath.substring(0, lastIndex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.pd.pjc.service.FileService#getFileMimeType(java.lang.String)
	 */
	public String getFileMimeType(String pFile) {

		String mimeType = "mime";

		String ext = StringUtils.getFilenameExtension(pFile);
		if (ext != null && mMimeMap.get(ext.toLowerCase()) != null ) {
			mimeType = mMimeMap.get(ext.toLowerCase());
		}

		return mimeType;
	}

	public Map<String, String> getMimeMap() {
		return mMimeMap;
	}

	public void setMimeMap(Map<String, String> pMimeMap) {
		mMimeMap = pMimeMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.pd.pjc.service.FileService#executeCommandByFileExt(java.lang.String)
	 */
	public void executeCommandByFileExt(FileElement pFile, String pDir) {
		executeCommandByFileExt(pFile, null, null, pDir);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.pd.pjc.service.FileService#executeCommandByFileExt(java.lang.String,
	 *      java.lang.String)
	 */
	public void executeCommandByFileExt(FileElement pFile, String pCmd, String pArgs,
			String pDir) {
		String fileName = pFile.getFileName();
		String extension = StringUtils.getFilenameExtension(fileName);

		if (extension == null && pCmd == null) {
			String msg = "File " + pFile + " has no extension";
			mLog.info(msg);
			MainWindow.errorLabel.setText(msg);
			return;
		}
		
		if(extension != null) {
			extension = extension.toLowerCase();
		}
		
		MimeSettingsBean mimeSettings = ServiceFactory.getXMLSettingsService()
				.getMimeSettings(extension);
		MimeApplication defaultApp = mimeSettings.getDefaultApp();

		String cmd = pCmd;

		// use the default command
		if (pCmd == null && defaultApp != null) {
			cmd = defaultApp.getCommand();
		}
		
		if(SessionBean.rcMode && cmd != null && !mRcModeCommands.contains(cmd)) {
			mLog.debug("In rc mode command " + cmd + " is not allowed to be executed.");
			return;
		}

		if(cmd == null && !PJCUtils.isWindows()) {
			String msg = "No command defined for extension " + extension;
			mLog.info(msg);
			MainWindow.errorLabel.setText(msg);
			return;
		}

		try {

			if(PJCUtils.isWindows() && cmd == null) {
				executeCommand("cmd.exe", "\"" + pFile.getFileName() + "\"", "/c start \"dummy\"", pDir);
			} else {
				executeCommand(cmd, pFile.getAbsolutePath(), pArgs, pDir);
			}

		} catch (IOException ioex) {
			String msg = "Error executing command for file " + fileName + ": ";
			mLog.error(msg + ioex.getMessage(), ioex);
			MainWindow.errorLabel.setText(msg);
		}

	}

	/**
	 * @param pArgs
	 * @param pDir
	 * @param cmd
	 * @param file
	 * @throws IOException
	 */
	public void executeCommand(String cmd, String file, String pArgs, String pDir) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		List<String> commands = new ArrayList<String>();
		commands.add(cmd);

		String args = null;
		if (pArgs != null && pArgs.indexOf("%") != -1) {
			args = pArgs.replaceAll("%1", file);
		} else {
			args = pArgs;
		}
		if (args != null) {
			args = args.trim();
			StringTokenizer token = new StringTokenizer(args, " ");
			while(token.hasMoreTokens()) {
				commands.add(token.nextToken());
			}
		}
		if(file != null) {
			commands.add(file.trim());
		}
		processBuilder.directory(new File(pDir));
		processBuilder.command(commands);
		mLog.info(processToString(processBuilder));
		Process process = processBuilder.start();

		if (process != null) {
			Runnable outputThread = new CommandOutputLogger(process);
			Thread thread = new Thread(outputThread);
			thread.start();
		}
	}
	
	private String processToString(ProcessBuilder pProcessBuilder) {
		StringBuffer buff = new StringBuffer();
		
		List<String> commands = pProcessBuilder.command();
		File dir = pProcessBuilder.directory();
		
		buff.append(dir.getAbsolutePath() + ": ");
		for (String string : commands) {
			buff.append(string + " ");
		}
		
		return buff.toString();
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#delete(java.lang.String)
	 */
	public void delete(String pFile) {
		if(pFile == null) return;
		File file  = new File(pFile);
		if(!file.exists()) {
			mLog.info("File " + pFile + " does not exist");
			return;
		}
		
		mLog.info("Deleting file " + pFile);
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				delete(subFile.getAbsolutePath());
			}
		}
		file.delete();
		if(file.exists()) {
			JOptionPane.showMessageDialog(ServiceFactory.getMainWindowController().getMainWindow(), "The file " + pFile + " could not be deleted for some reason!");
			mLog.info("file " + file + " still exists");
		}
	}

	public Collection<File> getPureFiles(String pPath) {
		return getPureFiles(pPath, new FilePatternFilter());
	}
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#getFiles(java.lang.String)
	 */
	public Collection<File> getPureFiles(String pPath, FilePatternFilter pPattern) {
		File dir = new File(pPath);
		if(!dir.exists() || !dir.isDirectory()) return new ArrayList<File>();
		File[] files = dir.listFiles(pPattern);
		if(files == null) return new ArrayList<File>();
		return Arrays.asList(files);
	}
	
	/** TODO: make regexp
	 * @param pFile
	 * @return
	 */
	public final static boolean isAudioFile(String pFile) {
		String extension = StringUtils.getFilenameExtension(pFile);
		if(extension != null) {
			extension = extension.toLowerCase();
			if(extension.equals("mp3") || extension.equals("wav") || extension.equals("mp4")) {
				return true;
			}
		}
		return false;
	}
	
	/** Checks if the file is a image type.
	 * TODO: make regexp
	 * @param pFile
	 * @return
	 */
	public final static boolean isImage(String pFile) {
		String extension = StringUtils.getFilenameExtension(pFile);
		if(extension != null) {
			extension = extension.toLowerCase();
			if(extension.equals("jpg") || extension.equals("gif") || extension.equals("png")) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#rename(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rename(String pPath, String pName, String pNewName) {
		String src = pPath + File.separator + pName;
		String dst = pPath + File.separator + pNewName;
		
		moveFile(src, dst);
	}

	public Map<String, String> getDirIconsMap() {
		return mDirIconsMap;
	}

	public void setDirIconsMap(Map<String, String> pDirIconsMap) {
		mDirIconsMap = pDirIconsMap;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#moveFile(java.lang.String, java.lang.String)
	 */
	public void moveFile(String pSourceFile, String pDestFile) {
		File srcFile = new File(pSourceFile);
		File toFile = new File(pDestFile);
		
		boolean b = srcFile.renameTo(toFile);
		
		if(b) {
			mLog.info("File renamed: " + srcFile.getAbsolutePath() + " => " + toFile.getAbsolutePath());
		} else {
			mLog.error("Could not rename " +  srcFile.getAbsolutePath() + " to " + toFile.getAbsolutePath());
		}
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#makeDirectory(java.lang.String)
	 */
	public boolean makeDirectory(String pDir) {
		boolean success = (new File(pDir)).mkdir();
	    if (!success) {
	   	 mLog.error("Could not create directory " + pDir);
	    }
		return success;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#loadFileList(de.pd.pjc.gui.table.FileTable, java.lang.String, de.pd.pjc.FilePatternFilter, boolean)
	 */
	public void loadFileList(FileTable pTable, String pPath,
			FilePatternFilter pPattern, boolean pRecursive) throws FileNotFoundException {

		if(!new File(pPath).isDirectory()) {
			throw new IllegalArgumentException(pPath + " is not a directory");
		}

		TableModel tableModel = pTable.getModel();
		FileTableModel model = null;
		TableSorter sorter = null;
		if(tableModel instanceof TableSorter) {
			sorter = (TableSorter) tableModel;
			model = (FileTableModel) sorter.getTableModel();
		} else {
			model = (FileTableModel) tableModel;
		}
		model.clear();

		// load the file list
		Collection<FileElement> dirList = getFiles(pPath, pPattern, pRecursive);

		model.addFiles(dirList);

		// path settings
		DirSettingsBean dirSettings = ServiceFactory.getXMLSettingsService().getDirSettings(pPath);
		if (dirSettings != null && dirSettings.getIconSize() != null) {
			pTable.setRowHeight(Integer.parseInt(dirSettings.getIconSize()));
		} else {
			pTable.setRowHeight(Settings.DEFAULT_TABLE_ROW_HEIGHT);
		}
		if (sorter != null) {
			sorter.resetSorting();

			if (dirSettings != null && dirSettings.getSortOrder() != null) {
				String pathSort = dirSettings.getSortOrder();
				try {
					String[] sorting = StringUtils
							.commaDelimitedListToStringArray(pathSort);
					sorter.setSortingStatus(Integer.parseInt(sorting[0]), Integer
							.parseInt(sorting[1]));

				} catch (ArrayIndexOutOfBoundsException e) {
					mLog
							.error("Sort property "
									+ pathSort
									+ " is not well formatted. See the application.property file for an example");
				}
			} else {
				sorter.setSortingStatus(Constants.COLUMN_NAME,
						TableSorter.ASCENDING);
			}

		}
	}

	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#openConsoleInCurrentDir(java.lang.String)
	 */
	public void openConsoleInCurrentDir(String pDir) {
		try {
			executeCommand(mTerminalCommand, null, null, pDir);
		} catch (IOException e) {
			mLog.error("Could not run console command");
		}
	}

	/**
	 * @return the terminalCommand
	 */
	public String getTerminalCommand() {
		return mTerminalCommand;
	}

	/**
	 * @param pTerminalCommand the terminalCommand to set
	 */
	public void setTerminalCommand(String pTerminalCommand) {
		mTerminalCommand = pTerminalCommand;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#completePath(java.lang.String)
	 */
	public String completePath(String pPath) {
	
		int last = pPath.lastIndexOf(File.separator);
		if(last == -1 || last == pPath.length()) {
			return "";
		}
		String realPath = pPath.substring(0, last);
		String startPath = pPath.substring(last+1);
		FilePatternFilter filter = new FilePatternFilter("^" + startPath, false, true);
		filter.setCaseSensitive(true);
		Collection<File> files = getPureFiles(realPath, filter);
		if(files.isEmpty() || (files.size() > 1 && startPath.length() < 3)) {
			return "";
		} else {
			String name = files.iterator().next().getAbsolutePath();
			int pos = 0;
			if(PJCUtils.isWindows()) {
				pos = 1;
			}
			String completion = name.substring(pPath.length() + pos);
			return completion;
		}
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FileService#calculateDirSize(java.lang.String)
	 */
	public FileCountBean calculateDirSize(String pDir, boolean pRecursive) {
		FileCountBean cnt = new FileCountBean();
		
		if(SessionBean.DIR_SIZE_CALCULATION_ABBORTED) {
			return new FileCountBean();
		}
		
		Collection<File> files = getPureFiles(pDir);
		for (File file : files) {
			if(file.isDirectory() && pRecursive) {
				FileCountBean countBean = calculateDirSize(file.getAbsolutePath(), true);
				cnt.size += countBean.size;
				cnt.dirs += countBean.dirs;
				cnt.files += countBean.files;
				cnt.dirs++;
				
			} else {
				cnt.size += file.length();
				cnt.files++;
			}
		}
		return cnt;
	}

	/**
	 * @return the rcModeCommands
	 */
	public List<String> getRcModeCommands() {
		return mRcModeCommands;
	}

	/**
	 * @param pRcModeCommands the rcModeCommands to set
	 */
	public void setRcModeCommands(List<String> pRcModeCommands) {
		mRcModeCommands = pRcModeCommands;
	}

}
