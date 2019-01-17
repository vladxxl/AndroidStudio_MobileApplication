package com.example.user.myapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.user.myapplication.bus.Bus;
import com.example.user.myapplication.bus.BusController;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartActivity extends AppCompatActivity {

    private static String TAG = "ChartActivity";

    private int[] yData ;
    private String[] xData ;

    private HashMap<String,Integer> data = new HashMap<>();

    BusController busController;
    PieChart pieChart;

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chart);

    busController = ViewModelProviders.of(this, new BusController.Factory(getApplicationContext())).get(BusController.class);

    int arrayLength = busController.getAllBusses().size();
    yData = new int[arrayLength];
    xData = new String[arrayLength];

    pieChart = (PieChart) findViewById(R.id.idPieChart);
    pieChart.setRotationEnabled(true);
    pieChart.setHoleRadius(25f);
    pieChart.setTransparentCircleAlpha(0);
    pieChart.setCenterText("My Chart");
    pieChart.setCenterTextSize(10);

    addDataSet();

    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            Log.d(TAG, "onValueSelected: Value select from chart.");
            Log.d(TAG, "onValueSelected: "+e.toString());
            Log.d(TAG, "onValueSelected: "+h.toString());

        }

        @Override
        public void onNothingSelected() {

        }
    });

//    //GoHome
//    Button bHome = (Button) findViewById(R.id.bHome);
//
//    bHome.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(ChartActivity.this, BusActivity.class);
//            ChartActivity.this.startActivity(intent);
//        }
//    });

    }

    private void addDataSet() {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        int j=0;
        for (Bus bus: busController.getAllBusses()){
            if ( !data.containsKey(bus.getRoute())) {
                data.put(bus.getRoute(),1);
            } else{
                int val=data.get(bus.getRoute());
                data.put(bus.getRoute() , val+1);
            }

        }

        for (String key : data.keySet()){
            yEntrys.add(new PieEntry(data.get(key).intValue(),key));
        }


        //create data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys,"Rute cumparate:");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


}
