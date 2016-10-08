package com.example.test;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class HomeActivity extends Activity {
	private RelativeLayout mainLayout;
	private LineChart mChart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mainLayout= (RelativeLayout) findViewById(R.id.mainLayout);
		
		mChart = (LineChart)findViewById(R.id.graphCommunityLine);
				
				 
		
		
		mChart.setDescription("");
		mChart.setNoDataTextDescription("No data for the moment");
		
		mChart.setHighlightEnabled(true);
		mChart.setTouchEnabled(true);
		
		mChart.setDragDecelerationEnabled(true);
		mChart.setScaleEnabled(true);
		mChart.setDrawGridBackground(false);
		mChart.setPinchZoom(true);
		mChart.setBackgroundColor(Color.GRAY);
		
		LineData data= new LineData();
		data.setValueTextColor(Color.WHITE);
		
		mChart.setData(data);
		
		Legend l = mChart.getLegend();
		
		l.setForm(LegendForm.LINE);
		l.setTextColor(Color.WHITE);
		
		XAxis x1 = mChart.getXAxis();
		x1.setTextColor(Color.WHITE);
		x1.setDrawGridLines(false);
		x1.setAvoidFirstLastClipping(true);
		
		YAxis y1 = mChart.getAxisLeft();
		y1.setTextColor(Color.WHITE);
		y1.setAxisMaxValue(120f);
		y1.setDrawGridLines(true);
		
		YAxis y12 = mChart.getAxisRight();
		y12.setEnabled(false);
		
		
		
	}
	
	
	
	private void addEntry(){
		LineData data = mChart.getData();
		if(data != null){
			LineDataSet set = data.getDataSetByIndex(0);
			if(set == null){
				set = createSet();
				data.addDataSet(set);
			}
			data.addXValue("");
			data.addEntry(new Entry((float)(Math.random() * 75)+60f, set.getEntryCount()),0);
			
			mChart.notifyDataSetChanged();
			
			mChart.setVisibleXRangeMaximum(6);
			
			mChart.moveViewToX(data.getXValCount()-7);
			
			
		}
	}
	
	private LineDataSet createSet(){
		LineDataSet set = new LineDataSet(null, "SPL Db");
		set.setDrawCubic(true);
		set.setCubicIntensity(0.2f);
		set.setAxisDependency(AxisDependency.LEFT);
		set.setColor(ColorTemplate.getHoloBlue());
		set.setCircleColor(ColorTemplate.getHoloBlue());
		set.setLineWidth(2f);
		set.setCircleSize(4f);
		set.setFillAlpha(65);
		set.setFillColor(Color.rgb(244, 117, 110));
		set.setValueTextColor(Color.WHITE);
		set.setValueTextSize(10f);
		
		return set;
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
}
