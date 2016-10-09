package com.whileloop.hydrosaver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public class DataFile implements Serializable{

	public static final String FILENAME = "datafile2.ser";
	ArrayList<Device> homedevices;
	public DataFile() {
		// TODO Auto-generated constructor stub
		homedevices =  new ArrayList<Device>();
	}
	//clear the datafile
	public void clearDataFile(){
		homedevices =  new ArrayList<Device>();
	}
	public static void saveDataFile(Context context,DataFile datafile){ 
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(datafile);
			oos.close();
			fos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static DataFile getDataFile(Context context){
		DataFile datafile;
		try{
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			datafile = (DataFile) is.readObject();
			is.close();
			fis.close();
		}catch(Exception e){
			System.out.println("get user "+e.getMessage());
			datafile = null;
		}
		return datafile;
		
	}
}
