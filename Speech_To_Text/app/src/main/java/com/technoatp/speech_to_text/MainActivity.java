package com.technoatp.speech_to_text;

import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class MainActivity extends Activity {

    protected static final int RESULT_SPEECH = 1;
    TextToSpeech t1;
    private ImageButton btnSpeak;
    private TextView Text;
    private int arrindex=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Text = (TextView) findViewById(R.id.Text);
        btnSpeak = (ImageButton) findViewById(R.id.mic);

        //語音輸出宣告***************************************************************
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.CHINA);
                }
            }
        });
        //語音輸出宣告***************************************************************
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //intent .putExtra("name",name);//可放所有基本類別
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());

                try {
                    //使用的 Intent 物件沒有任何特殊之處，但是您需要將附加整數引數傳遞至 startActivityForResult() 方法。
                    startActivityForResult(intent, RESULT_SPEECH);
                    Text.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        使用者處理隨後的應用行為顯示並返回時，系統會呼叫應用行為顯示的 onActivityResult() 方法。 此方法包括 三個引數：
        1.您傳遞至 startActivityForResult() 的要求代碼。
        2.由第二個應用行為顯示指定的結果代碼。此代碼是 RESULT_OK (若操作成功) 或 RESULT_CANCELED (若因故使用者退出或操作失敗)。
        3.攜帶結果資料的 Intent。
         */

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    Text.setText(text.get(arrindex));
                    for (int i = 0; i < text.get(arrindex).length(); i++) {
                        if(text.get(arrindex).charAt(i) == '關')
                        {
                            System.out.println("成功進來");
                            for(int j=0;j<text.get(arrindex).length();j++)
                            {
                                switch(text.get(arrindex).charAt(j))
                                {
                                    case '燈':
                                        Toast.makeText(getApplicationContext(), "好的將為您關燈", Toast.LENGTH_SHORT).show();
                                        t1.speak("好的將為您關燈", TextToSpeech.QUEUE_FLUSH, null);
                                        Text.setText("好的將為您關燈");
                                        break;
                                    case '窗':
                                        Toast.makeText(getApplicationContext(), "好的將為您關窗", Toast.LENGTH_SHORT).show();
                                        t1.speak("好的將為您關窗", TextToSpeech.QUEUE_FLUSH, null);
                                        Text.setText("好的將為您關窗");
                                        break;
                                    default:
                                        break;
                                }

                            }
                            break;
                        }
                        else if(text.get(arrindex).charAt(i) == '開')
                        {
                            System.out.println("成功進來");
                            for(int j=0;j<text.get(arrindex).length();j++)
                            {
                                switch(text.get(arrindex).charAt(j))
                                {
                                    case '燈':
                                        Toast.makeText(getApplicationContext(), "好的將為您開燈", Toast.LENGTH_SHORT).show();
                                        t1.speak("好的將為您開燈", TextToSpeech.QUEUE_FLUSH, null);
                                        break;
                                    case '窗':
                                        Toast.makeText(getApplicationContext(), "好的將為您開窗", Toast.LENGTH_SHORT).show();
                                        t1.speak("好的將為您開窗", TextToSpeech.QUEUE_FLUSH, null);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            break;
                        }

                    }
                    text.remove(arrindex);
                    break;



                }

            }
        }

    }
}