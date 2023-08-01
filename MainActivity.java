package jp.suntech.c22010.myapplication;

import static java.lang.Float.parseFloat;
import static java.lang.String.*;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BCListener bcListener = new BCListener();
        findViewById(R.id.bt_calc).setOnClickListener(bcListener);
        findViewById(R.id.bt_clr).setOnClickListener(bcListener);
    }
    private class BCListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            EditText in_age = findViewById(R.id.et_age);
            EditText in_height = findViewById(R.id.et_height);
            EditText in_weight = findViewById(R.id.et_weight);
            TextView out_show_obesity = findViewById(R.id.tv_show_obesity);
            TextView out_obesity = findViewById(R.id.tv_obesity);
            TextView out_show_best_weight = findViewById(R.id.tv_show_best_weight);
            TextView out_best_weight = findViewById(R.id.tv_best_weight);
            TextView out_best_weight_kg = findViewById(R.id.tv_best_weight_kg);

            int id = view.getId();

            //計算ボタンが押されたら
            if(id == R.id.bt_calc){
                //ダイアログ表示
                CalcDialogFragment dialogFragment = new CalcDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "CalcDialogFragment");

                float height = parseFloat(in_height.getText().toString());
                float weight = parseFloat(in_weight.getText().toString());
                float age = parseFloat(in_age.getText().toString());
                float best_weight;

                if(age < 6){
                    //カウプ指数で計算
                    float kaup = weight * 10000 / height / height;
                    float min_weight = 0, max_weight = 0;
                    if(age < 1){
                        min_weight = 16;
                        max_weight = 18;
                    }
                    else if(age < 2){
                        min_weight = 15.5f;
                        max_weight = 17.5f;
                    }
                    else if(age < 3){
                        min_weight = 15;
                        max_weight = 17;
                    }
                    else{
                        min_weight = 14.5f;
                        max_weight = 16.5f;
                    }
                    //肥満度を設定
                    if(kaup < min_weight){
                        out_show_obesity.setText(R.string.tv_obesity_child_low);
                        out_show_obesity.setTextColor(Color.parseColor("#00FFFF"));
                    }
                    else if(kaup < max_weight){
                        out_show_obesity.setText(R.string.tv_obesity_child_nom);
                        out_show_obesity.setTextColor(Color.parseColor("#00FF7F"));
                    }
                    else {
                        out_show_obesity.setText(R.string.tv_obesity_child_fat);
                        out_show_obesity.setTextColor(Color.parseColor("#F0E68C"));
                    }
                    best_weight = weight * ((min_weight+max_weight) / 2) / kaup;
                }
                else if(age < 16){
                    //ローレル指数で計算
                    float rohrer = weight * 10000000 / (height*height*height);
                    if(rohrer < 100){
                        out_show_obesity.setText(R.string.tv_obesity_child_too_low);
                        out_show_obesity.setTextColor(Color.parseColor("#00008b"));
                    }
                    else if(rohrer < 115){
                        out_show_obesity.setText(R.string.tv_obesity_child_low);
                        out_show_obesity.setTextColor(Color.parseColor("#00FFFF"));
                    }
                    else if(rohrer < 145){
                        out_show_obesity.setText(R.string.tv_obesity_child_nom);
                        out_show_obesity.setTextColor(Color.parseColor("#00FF7F"));
                    }
                    else if(rohrer < 160){
                        out_show_obesity.setText(R.string.tv_obesity_child_fat);
                        out_show_obesity.setTextColor(Color.parseColor("#F0E68C"));
                    }
                    else{
                        out_show_obesity.setText(R.string.tv_obesity_child_too_fat);
                        out_show_obesity.setTextColor(Color.parseColor("#FFFF00"));
                    }
                    best_weight = weight * 130 / rohrer;
                }
                else {
                    float bmi = weight * 10000 / height / height;
                    best_weight = weight * 22 / bmi;


                    //判定を表示
                    out_obesity.setText(R.string.tv_obesity);
                    if (bmi < 18.5) {
                        out_show_obesity.setText(R.string.tv_obesity_low);
                        out_show_obesity.setTextColor(Color.parseColor("#00FFFF"));
                    } else if (bmi < 25) {
                        out_show_obesity.setText(R.string.tv_obesity_nom);
                        out_show_obesity.setTextColor(Color.parseColor("#00FF7F"));
                    } else if (bmi < 30) {
                        out_show_obesity.setText(R.string.tv_obesity_1);
                        out_show_obesity.setTextColor(Color.parseColor("#F0E68C"));
                    } else if (bmi < 35) {
                        out_show_obesity.setText(R.string.tv_obesity_2);
                        out_show_obesity.setTextColor(Color.parseColor("#FFFF00"));
                    } else if (bmi < 40) {
                        out_show_obesity.setText(R.string.tv_obesity_3);
                        out_show_obesity.setTextColor(Color.parseColor("#DC143C"));
                    } else {
                        out_show_obesity.setText(R.string.tv_obesity_4);
                        out_show_obesity.setTextColor(Color.parseColor("#800000"));
                    }
                }

                //適正体重を表示
                out_best_weight.setText(R.string.tv_best_weight);
                out_show_best_weight.setText(format("%.1f", best_weight));
                out_best_weight_kg.setText(R.string.tv_weight2);
            }
            //クリアボタンが押されたら
            else if(id == R.id.bt_clr){
                in_age.setText("");
                in_height.setText("");
                in_weight.setText("");
                out_show_obesity.setText("");
                out_obesity.setText("");
                out_show_best_weight.setText("");
                out_best_weight.setText("");
                out_best_weight_kg.setText("");
            }
        }
    }
}