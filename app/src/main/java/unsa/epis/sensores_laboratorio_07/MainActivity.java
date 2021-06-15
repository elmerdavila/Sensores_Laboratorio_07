package unsa.epis.sensores_laboratorio_07;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView x,y,z;
    private Sensor acelerometro;
    //private Sensor gravedad;
    private Sensor magnetometro;
    private SensorManager sensorManager;

    //private float [] acelerometroValores=new float[3];
    private float [] gravedadValores=new float[3];
    private float [] magnetometroValores=new float[3];
    private float [] matrizRotacion=new float[16];
    private float [] orientacionValores=new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x = (TextView) findViewById(R.id.x_posicion);
        y = (TextView) findViewById(R.id.y_posicion);
        z = (TextView) findViewById(R.id.z_posicion);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //gravedad = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magnetometro = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
       

        SensorEventListener sensorEventListenerAcelerometro_Gravedad = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //acelerometroValores=sensorEvent.values;
                gravedadValores = sensorEvent.values;
                //SensorManager.getRotationMatrix(matrizRotacion,null,acelerometroValores,magnetometroValores);
                Log.d("VALORES DE GRAVEDAD", String.valueOf(sensorEvent.values[0]));
                SensorManager.getRotationMatrix(matrizRotacion, null, gravedadValores, magnetometroValores);
                SensorManager.getOrientation(matrizRotacion, orientacionValores);

                orientacionValores[0] = (float) Math.toDegrees(orientacionValores[0]);
                orientacionValores[1] = (float) Math.toDegrees(orientacionValores[1]);
                orientacionValores[2] = (float) Math.toDegrees(orientacionValores[2]);

                x.setText(String.valueOf(orientacionValores[0]));
                y.setText(String.valueOf(orientacionValores[1]));
                z.setText(String.valueOf(orientacionValores[2]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        SensorEventListener sensorEventListenerMagnetometro = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                magnetometroValores = sensorEvent.values;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        sensorManager.registerListener(sensorEventListenerAcelerometro_Gravedad, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagnetometro, magnetometro, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
