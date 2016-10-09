package com.whileloop.hydrosaver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class SendDayBreak extends AsyncTask<String,Void,String>{
	private Context context;
	public SendDayBreak(Context context){
		this.context = context;
	}
	@Override
	protected String doInBackground(String... params) {
		try{
			String userid = params[1];
			String usage = params[0];
			
            String link="http://hydrosaver.azurewebsites.net/controller_sendcommunity.php";
            String data  = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid+"", "UTF-8");
            data += "&" + URLEncoder.encode("usage", "UTF-8") + "=" + URLEncoder.encode(usage, "UTF-8");
            
            URL url = new URL(link);
            URLConnection conn = url.openConnection(); 
            
            conn.setDoOutput(true); 
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
            
            wr.write( data ); 
            wr.flush(); 
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
               sb.append(line);
               break;
            }
            return sb.toString();
         }
         catch(Exception e){
            return new String("Exception: " + e.getMessage());
         }
	}
	
	 @Override
	 protected void onPostExecute(String result){
		 Log.i("Sent Status..", result);
	      
	 }
}
