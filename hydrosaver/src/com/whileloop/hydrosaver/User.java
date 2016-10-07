package com.whileloop.hydrosaver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;

public class User implements Serializable{
	public static final String FILENAME = "user.ser";
	public int id;
	public String email;
	
	public static void saveUser(Context context,User user){ 
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(user);
			oos.close();
			fos.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static User getUser(Context context){
		User user;
		try{
			FileInputStream fis = context.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			user = (User) is.readObject();
			is.close();
			fis.close();
		}catch(Exception e){
			System.out.println("get user "+e.getMessage());
			user = null;
		}
		return user;
		
	}
	
	
	
	
	
	
	
}
