package tw.edu.pu.cs.s1032869.mqtt_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends AppCompatActivity {

    private MqttClient client;
    private MqttConnectOptions connOpts;
    private String topic = "123";
    private String content = "111";
    private int qos = 2;
    private String broker = "tcp://192.168.43.8:1883";
    private String clientId = "username";
    MemoryPersistence persistence;

    private TextView txv;
    private Button conBtn;
    private Button publishBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.textView);
        conBtn = (Button) findViewById(R.id.button);
        publishBtn = (Button) findViewById(R.id.button2);
    }

    public void connect()
    {
        boolean flag = false;
        persistence = new MemoryPersistence();
        try
        {
            client = new MqttClient(broker, clientId, persistence);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
        }
        catch(MqttException e)
        {
            System.out.println("reason "+e.getReasonCode());
            System.out.println("msg "+e.getMessage());
            System.out.println("loc "+e.getLocalizedMessage());
            System.out.println("cause "+e.getCause());
            System.out.println("excep "+e);
            e.printStackTrace();
        }
        if(client != null)
        {
            flag = true;
        }
        else
        {
            flag = false;
        }
        txv.setText(String.valueOf(flag));
    }

    public void publish()
    {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        try
        {
            client.publish(topic, message);
        }
        catch (MqttException e)
        {
            System.out.println("reason "+e.getReasonCode());
            System.out.println("msg "+e.getMessage());
            System.out.println("loc "+e.getLocalizedMessage());
            System.out.println("cause "+e.getCause());
            System.out.println("excep "+e);
            e.printStackTrace();
        }
    }

    public void subscribe()
    {
        try
        {
            client.subscribe(topic, qos);
        }
        catch(MqttException e)
        {
            System.out.println("reason "+e.getReasonCode());
            System.out.println("msg "+e.getMessage());
            System.out.println("loc "+e.getLocalizedMessage());
            System.out.println("cause "+e.getCause());
            System.out.println("excep "+e);
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        try
        {
            client.disconnect();
        }
        catch(MqttException e)
        {
            System.out.println("reason "+e.getReasonCode());
            System.out.println("msg "+e.getMessage());
            System.out.println("loc "+e.getLocalizedMessage());
            System.out.println("cause "+e.getCause());
            System.out.println("excep "+e);
            e.printStackTrace();
        }
    }
}
