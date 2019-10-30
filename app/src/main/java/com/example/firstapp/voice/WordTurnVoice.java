package com.example.firstapp.voice;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.R;

import java.util.Locale;

/**
 * 文字转语音2
 * @auto Bill
 */
public class WordTurnVoice extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Button speechButton;
    private EditText speechTextView;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_turn_voice);

        //初始化文字转语音
        tts = new TextToSpeech(this, this);

        speechTextView = findViewById(R.id.speechTextView);
        speechButton = findViewById(R.id.speechButton);

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);
                tts.speak(speechTextView.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                Log.i("****","点击并转换了语音");
            }
        });
    }

    @Override
    public void onInit(int status) {
        //判断是否转换成功
        if (status == TextToSpeech.SUCCESS){
            //设置转换语言为中文
            int result = tts.setLanguage(Locale.CHINA);
            //result == TextToSpeech.LANG_MISSING_DATA  || result == TextToSpeech.LANG_NOT_SUPPORTED
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(WordTurnVoice.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                Log.i("****","有问题");
            }
            /*else{
                //转换为英文
                tts.setLanguage(Locale.US);
                Log.i("****","转英文");
            }*/
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Log.i("****","走menu");
        return true;
    }
}
