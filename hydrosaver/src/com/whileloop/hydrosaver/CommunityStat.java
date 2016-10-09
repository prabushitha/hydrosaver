package com.whileloop.hydrosaver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class CommunityStat extends AsyncTask<String,Void,String>{
	private Context context;
	public CommunityStat(Context context){
		this.context = context;
	}
	@Override
	protected String doInBackground(String... params) {
		try{
			int duration = Integer.parseInt(params[0]);
            String link="http://hydrosaver.azurewebsites.net/controller_community.php";
            String data  = URLEncoder.encode("duration", "UTF-8") + "=" + URLEncoder.encode(duration+"", "UTF-8");
            
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
		 Log.i("Community..", result);
	      if(!result.equals("error")){
	    	  try{
		    	  JSONArray mixed = new JSONArray(result);
		    	  JSONArray json = mixed.getJSONArray(0);//new JSONArray(mixed.getString(index)(0));
		    	  int today = mixed.getInt(1);
		    	  int[] usage = new int[json.length()];
		    	  for(int i=0;i<json.length();i++){
		    		  usage[i] = json.getInt(i);
		    	  }
		    	  //direct user to the home page
		    	  Intent in = new Intent(context,CommunityActivity.class);
		    	  in.putExtra("averageUsage", usage);
		    	  in.putExtra("today", today);
		          context.startActivity(in);
		      }catch(Exception e){
		    	  
		      }
	      }else{
	    	  
	      }
	      
	 }
}
