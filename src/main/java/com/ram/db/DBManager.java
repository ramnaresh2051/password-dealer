package com.ram.db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DBManager {
  /* Error */
  public static boolean writeIntoFile(String fileData) {
	boolean statusFlag = false;
	String userHome = System.getProperty("user.home");
    File file = new File(userHome + "/dealer.txt");
	FileWriter fout = null;
	BufferedWriter bout = null;
	try {
		fout = new FileWriter(file);
		bout = new BufferedWriter(fout);
		bout.write(fileData);
		statusFlag = true;
	} catch(IOException ioe) {
		ioe.printStackTrace();
	} finally {
		if (bout != null) {
			try {
				bout.close();
			} catch (IOException e) {
				statusFlag = false;
				System.out.println("Exception in closing out stream");
				e.printStackTrace();
			}
		}
	}
	return statusFlag;
   
  }
  
  public static ArrayList<String> readFromFile() {
    String userHome = System.getProperty("user.home");
    File file = new File(userHome + "/dealer.txt");
    ArrayList<String> stringArray = null;
    FileReader fin = null;
    BufferedReader bin = null;
    try {
      if (!file.exists()) {
        writeIntoFile("");
      }
      fin = new FileReader(file);
      bin = new BufferedReader(fin);
      String line = null;
      stringArray = new ArrayList();
      while ((line = bin.readLine()) != null) {
        stringArray.add(line);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
    	if (bin != null) {
    		try {
				bin.close();
			} catch (IOException e) {
				System.out.println("Exception in closing in stream");
				e.printStackTrace();
			}
    	}
    }
    return stringArray;
  }
}
