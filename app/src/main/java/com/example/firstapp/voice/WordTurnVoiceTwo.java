package com.example.firstapp.voice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstapp.R;

import java.util.Locale;

/**
 * 文字转语音2
 *     使用科大讯飞引擎
 * @auto Bill
 */
public class WordTurnVoiceTwo extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {

    private EditText main_edit_text;
    private Button main_btn_read;
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_turn_voice_two);
        initView(); //初始化页面
        initTextToSpeech(); //初始化语音播报
    }

    private void initTextToSpeech(){
        //getApplicationContext()  OnInitlistener
        mTextToSpeech = new TextToSpeech(this, this);
        //设置音调，值越大越女人, 1.0f为正常音调
        mTextToSpeech.setPitch(1.0f);
        //设置语速
        mTextToSpeech.setSpeechRate(1.0f);
    }

    private void initView(){
        main_edit_text = findViewById(R.id.main_edit_text);
        main_btn_read = findViewById(R.id.main_btn_read);

        main_btn_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_btn_read:
                //开始将输入的字符转换为语音，并将其播放
                submit();
                break;
            default:
                break;
        }
    }

    private void submit(){
        //获取用户输入的内容
        String text = main_edit_text.getText().toString().trim();
        if (TextUtils.isEmpty(text)){
            Toast.makeText(this, "请您输入需要转换的内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()){
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     * 初始化TextToSpeech引擎
     * @param status
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            // 请使用科大讯飞
            int result = mTextToSpeech.setLanguage(Locale.CHINESE);
            //如果数据丢失或者不支持该语言   则报错
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "数据丢失或者语言不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 关闭语音播报的功能
     */
    @Override
    protected void onStop() {
        super.onStop();
        //不管朗读TTS的状态   都将他关闭
        mTextToSpeech.stop();
        // 释放资源
        mTextToSpeech.shutdown();
    }

    /**
     * 在退出程序之前做判断，
     *      TTS是否还在启动。 是：将其关闭，赋值null
     */
    @Override
    protected void onDestroy() {
        if (mTextToSpeech != null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
        super.onDestroy();
    }
}
