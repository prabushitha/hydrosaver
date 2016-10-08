package com.whileloop.hydrosaver;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends Activity {
	SessionLogin sessionlogin;
	Context context;
	PieChart piechart;
	Button syncButton;
	TextView overallUsageTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		context = this;
		sessionlogin = new SessionLogin(this);
		syncButton = (Button)findViewById(R.id.btnsync);
		overallUsageTextView = (TextView)findViewById(R.id.mainUnitsText);
		//getting piechart 
		piechart = (PieChart)findViewById(R.id.graphMainPie);
		OnClickListener mainButtonClicks = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId()==R.id.btnLogout){
					sessionlogin.logout();
					Intent in = new Intent(context,MainActivity.class);
					startActivity(in);
				}else if(v.getId()==R.id.btnsections){
					Intent in = new Intent(context,SectionsActivity.class);
					startActivity(in);
				}else if(v.getId()==R.id.btncommunity){
					Intent in = new Intent(context,MainActivity.class);
					startActivity(in);
				}else if(v.getId()==R.id.btnsync){
					ConnectDevice conDevice = new ConnectDevice(context, syncButton,overallUsageTextView);
				}
				
			}
		};
		//menu buttons
		Button btnsync = syncButton;
		Button btnsections = (Button)findViewById(R.id.btnsections);
		Button btncommunity = (Button)findViewById(R.id.btncommunity);
		Button btnlogout = (Button)findViewById(R.id.btnLogout);
		
		btnsync.setOnClickListener(mainButtonClicks);
		btnsections.setOnClickListener(mainButtonClicks);
		btncommunity.setOnClickListener(mainButtonClicks);
		btnlogout.setOnClickListener(mainButtonClicks);
		
		//--------------------------------------
		//creating pie chart
		createPieChart();
		
		
		
	}
	public void createPieChart(){
		
		//getting data
		Entry currentEntry = new Entry(72f, 0);
		Entry remainEntry = new Entry(28f, 1);
		//adding entries
		ArrayList<Entry> entries = new ArrayList<Entry>();
		entries.add(currentEntry);
		entries.add(remainEntry);
		//defining labels
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("");
		labels.add("");
		//define dataset
		PieDataSet dataset = new PieDataSet(entries, "Overall");
		//pie data
		PieData data = new PieData(labels,dataset);
		//setting pie data to the graph
		piechart.setData(data);
		
		
		//configuring chart
		piechart.setDescription("");    // Hide the description
		piechart.getLegend().setEnabled(false);   // Hide the legend
		piechart.setCenterText("72%"); 
		piechart.setCenterTextColor(Color.rgb(255, 255, 255));
		piechart.setHoleColor(Color.rgb(66, 66, 66));
		piechart.setCenterTextSize(40);
		piechart.setHoleRadius(80);
		//dataset.setColors(ColorTemplate.COLORFUL_COLORS);
		int[] colors = new int[]{
				Color.rgb(0, 160, 255),
				Color.rgb(228, 156, 52)
				};
		dataset.setColors(colors);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
    public void onBackPressed(){
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		/*
		Intent i = new Intent(this,MainActivity.class);
		startActivity(i);*/
	}
}
