package lab2_205_02.uwaterloo.ca.lab2_205_02;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import ca.uwaterloo.sensortoy.LineGraphView;
/*NOTES:
NOISE : Unwanted signals that cause a harm to transmission frequency.
Bias:
*/
public class Lab2_205_02 extends AppCompatActivity implements SensorEventListener{
    LineGraphView graph;
    Sensor LinearAcceleration;
    SensorManager sensorManager;
    Button ResetButton;
    TextView StepCountValue,Log;
    int stepcounting=0;
    boolean clicked=false;
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float[] pointstoplot = {event.values[0], event.values[1], event.values[2]};
            double total = Math.sqrt(event.values[0] * event.values[0] + event.values[1] * event.values[1] + event.values[2]* event.values[2]);
            if (total>1) {
                StepCountValue.setText(Integer.toString(stepcounting/60));
                stepcounting++;
            }
            Log.setText(Double.toString(total));
            //Log.setText(Double.toString(event.values[0])+" " +Double.toString(event.values[1])+" "+Double.toString(event.values[2]));
            graph.addPoint(pointstoplot);
        }

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_205_02);
        initializeViews();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager!=null) {
            LinearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorManager.registerListener(this, LinearAcceleration, SensorManager.SENSOR_DELAY_FASTEST);
        }
        else
            return;
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click detected!
                clicked = true;
                StepCountValue.setText("0");
                stepcounting=0;
                graph.clearAnimation();
            }
        });

    }
    public void initializeViews() {
        LinearLayout layout = ((LinearLayout) findViewById(R.id.layout));
        graph = new LineGraphView(getApplicationContext(), 100, Arrays.asList("X", "Y", "Z"));
        layout.addView(graph);
        graph.setVisibility(View.VISIBLE);
        ResetButton = (Button) findViewById(R.id.ResetButton);
        Log=(TextView)findViewById(R.id.LOG);
        StepCountValue = (TextView) findViewById(R.id.Value);
        DisplayCleanValues();

    }
    void DisplayCleanValues() {
    StepCountValue.setText("0");
    }

}


