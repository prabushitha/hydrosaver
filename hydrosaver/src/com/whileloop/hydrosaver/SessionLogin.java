package com.whileloop.hydrosaver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionLogin{
	Context context;
	public static final String MyPREFERENCES = "MyPrefs";
	public static final String LOG_STATUS = "islogged";
	public static final String USER_ID = "userid";
	
	
	SharedPreferences sharedpreferences;
	public SessionLogin(Context context){
		this.context = context;
		sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	}
	//check if the user is logged in. this is checked in each activity
	public boolean isLogged(){
		boolean logged = sharedpreferences.getBoolean(LOG_STATUS, false);
		return logged;
	}
	//setting login session
	public void setLogged(boolean value, int userid){
		SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(LOG_STATUS, value);
        editor.putInt(USER_ID, userid);
        editor.commit();
	}
	//logout
	public void logout(){
		SharedPreferences.Editor editor = sharedpreferences.edit();
	    editor.clear();
	    editor.commit();
	}
	//static method to check user is logged, if not redirect to login
	public static void checkLoginStatus(Context context){
		SessionLogin sl = new SessionLogin(context);
		Intent in;
		if(sl.isLogged()){
			//if the user is logged
			
			
			if(context instanceof MainActivity){
				//if the user is in the login screen direct user to home screen
				in = new Intent(context,HomeActivity.class);
	            context.startActivity(in);
			}
			
			User user = User.getUser(context);
			if(user.id != sl.sharedpreferences.getInt(USER_ID, 0)){
				//if saved id and serialized id doesn't match
				sl.logout();
				in = new Intent(context,MainActivity.class);
	            context.startActivity(in);
			}
		}else if(!(context instanceof MainActivity)) {
			//if the user is not in the login screen direct to login screen
			in = new Intent(context,MainActivity.class);
            context.startActivity(in);
		}
	}
	
	
}
