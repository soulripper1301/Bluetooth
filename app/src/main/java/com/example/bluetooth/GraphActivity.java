package com.example.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.IOException;
import java.util.ArrayList;


public class GraphActivity extends AppCompatActivity {

    LineDataSet lineDataSet1;
    LineData data;
    ArrayList<ILineDataSet> dataSets;
    LineChart graph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph =findViewById(R.id.LineChart);

        graph.setDrawBorders(true);
        graph.setBorderColor(R.drawable.grad);
        graph.setBorderWidth(5);

        XAxis axis=graph.getXAxis();
        axis.setValueFormatter(new XaxisValueFormatter());

        YAxis yAxis=graph.getAxisLeft();
        yAxis.setValueFormatter(new YaxisValueFormatter());

        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 0));
        lineDataSet1 = new LineDataSet(dataVals, "Data Set 1");
        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        data = new LineData(dataSets);
        graph.setData(data);
        graph.invalidate();
        int i=0;
        while(i<200) {
            AddData();
            i++;
        }
    }

    public synchronized void AddData(){
        Runnable runable;
        new Handler().postDelayed(runable=new Runnable(){
            public void run() {
                data.addEntry(new Entry(lineDataSet1.getEntryCount(),(float)(int)(Math.random()*100)),0);//change the value to increse or decre the limit
                graph.notifyDataSetChanged();
            }
        },1000);
    }

    public class XaxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            return value+"*c";
        }
    }
    public class YaxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            return value+"s";
        }
    }
}