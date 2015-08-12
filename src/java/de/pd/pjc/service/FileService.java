package de.pd.pjc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import de.pd.pjc.FilePatternFilter;
import de.pd.pjc.bean.FileCountBean;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.table.FileTable;

public interface FileService {

	/**
	 * @param pPath
	 * @return
	 * @throws FileNotFoundException
	 */
	public Collection<FileElement> getFiles(String pPath) throws FileNotFoundException;
	
	/**
	 * @param pPath
	 * @return
	 * @throws FileNotFoundException
	 */
	public Collection<FileElement> getFiles(String pPath, FilePatternFilter pPattern) throws FileNotFoundException;
	
	/**
	 * @param pPath
	 * @param pPattern
	 * @param pRecursive
	 * @return
	 * @throws FileNotFoundException
	 */
	public Collection<FileElement> getFiles(String pPath, FilePatternFilter pPattern, boolean pRecursive) throws FileNotFoundException;
	
	/** wrapper.
	 * @param pPath
	 * @return
	 */
	public Collection<File> getPureFiles(String pPath);
	
	/** Returns the files of the path.
	 * @param pPath
	 * @param pPattern
	 * @return
	 */
	public Collection<File> getPureFiles(String pPath, FilePatternFilter pPattern);
	
	/** Returns the top directory from a path.
	 * @param pPath
	 * @return
	 */
	public String getTopDirectory(String pPath);
	
	/**
	 * @param pPath
	 * @return
	 */
	public String getDownDirectory(String pPath);
	
	/** returns the file mime type by the file extion.
	 * @param pFile
	 * @return
	 */
	public String getFileMimeType(String pFile);
	
	/** wrapper. uses the default command.
	 * @param pFile
	 */
	public void executeCommandByFileExt(FileElement pFile, String pDir);
	
	/** Executes an command defined in the property file, determined by the file extension.
	 * @param pFile
	 */
	public void executeCommandByFileExt(FileElement pFile, String pCmd, String pArgs, String pDir);
	
	/**
	 * @param pArgs
	 * @param pDir
	 * @param cmd
	 * @param file
	 * @throws IOException
	 */
	public void executeCommand(String cmd, String file, String pArgs, String pDir) throws IOException;
	
	/** Delete a file/dir
	 * @param pFile
	 */
	public void delete(String pFile);
	
	/** Renames a file.
	 * @param pPath The path of the file.
	 * @param pName The old name of the file.
	 * @param pNewName The new name of the file.
	 */
	public void rename(String pPath, String pName, String pNewName);
	
	/** Moves a file.
	 * @param pSourceFile
	 * @param pDestFile
	 */
	public void moveFile(String pSourceFile, String pDestFile);
	
	/** Create a new directory.
	 * @param pDir
	 * @return
	 */
	public boolean makeDirectory(String pDir);
	
	/** 
	 * @param pPath
	 * @return
	 */
	public String getDirUp(String pPath);
	
	/**
	 * Loads the file list
	 * 
	 * @param pModel
	 * @param pPath
	 */
	public void loadFileList(FileTable pTable, String pPath, FilePatternFilter pPattern, boolean pRecursive)throws FileNotFoundException;
	
	
	/** Opens a console in the given dir.
	 */
	public void openConsoleInCurrentDir(String pDir);
	
	/** Fullfills the pPath.
	 * @param pPath The path to be completed.
	 * @return Returns the path completions.
	 */
	public String completePath(String pPath);

	/** Calculates the directory's files, size etc...
	 * @param pDir
	 * @return
	 */
	public FileCountBean calculateDirSize(String pDir, boolean pRecursive);
}
