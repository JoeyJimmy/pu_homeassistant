package tw.edu.pu.cs.s1032869.mqtt_v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MainActivity extends AppCompatActivity {

    private MqttClient client;
    private MqttConnectOptions connOpts;
    private String topic = "hello";
    private String content = "123";
    private int qos = 0;
    private String broker = "tcp://192.168.0.150:1883";
    private String clientId = "username";
    MemoryPersistence persistence;

    private TextView txv;
    private Button conBtn;
    private Button publishBtn;
    private Button subscribeBtn;
    private Button disconBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.textView);
        conBtn = (Button) findViewById(R.id.connect);
        publishBtn = (Button) findViewById(R.id.publish);
        subscribeBtn = (Button) findViewById(R.id.subscribe);
        disconBtn = (Button) findViewById(R.id.disconnect);

        conBtn.setOnClickListener(cB);
        publishBtn.setOnClickListener(pB);
        subscribeBtn.setOnClickListener(sB);
        disconBtn.setOnClickListener(dB);

        this.registerReceiver(batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            int lv=intent.getIntExtra("level",0);
            txv.setText("目前電量為:"+String.valueOf(lv)+"%");
            if(lv == 100)
            {
                publish();
            }
        }
    };

    //底下是按鈕

    public Button.OnClickListener cB =new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            connect();
        }

    };
    public Button.OnClickListener pB =new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            publish();
        }

    };
    public Button.OnClickListener sB =new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            subscribe();
        }

    };
    public Button.OnClickListener dB =new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            disconnect();
        }

    };

    //底下是方法 勿動!

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
        txv.setText("你 CONNECT 的狀態是: "+String.valueOf(flag));
    }

    public void publish()
    {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);

        try
        {
            client.publish(topic, message);
            txv.setText("你 PUBLISH 的字串是: "+String.valueOf(content));
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
            //txv.setText("你 SUBSCRIBE 獲得的字串是: "+String.valueOf());
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


//底下是實現callback的一種方法
//client.setCallback(new MqttCallback() {
//@Override
//public void connectionLost(Throwable cause) {
//
//        }
//
//@Override
//public void messageArrived(String topic, MqttMessage message) throws Exception {
//        txv.setText(String.valueOf(message.getPayload()));
//        }
//
//@Override
//public void deliveryComplete(IMqttDeliveryToken token) {
//
//        }
//        });