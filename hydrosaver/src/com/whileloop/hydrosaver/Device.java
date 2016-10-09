package com.whileloop.hydrosaver;

import java.io.Serializable;

public class Device implements Serializable{
	public int volume; //
	public String date;
	
	
	
	public float calculateCost(){
		//cost making algorithm here
		
		return getUnits()*8;
	}
	public float getUnits(){
		//1000 liters = 1 unit
		//in our system 100ml = 1 unit
		return volume/100;
	}
	public static int getVolume(int percentage){
		return (percentage*ConnectDevice.MAX_PER_DAY)/100;
	}
	
	
}
