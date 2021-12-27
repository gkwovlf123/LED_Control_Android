// RTDB : https://fir-led-test-default-rtdb.firebaseio.com/
// 스케치 코드 : Firebase_LED_Control.ico
package com.example.fd_led_control;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button onButton; //버튼 변수 지정
    Button offButton; //버튼 변수 지정
    TextView textView; //TextView 변수 지정
    ImageView LEDimage; //ImageView 변수 지정
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onButton = findViewById(R.id.btn01); //btn01의 ID 값을 가져옴
        offButton = findViewById(R.id.btn02); //btn02의 ID 값을 가져옴
        int bluecolor = getColor(R.color.BLUE); //R.color의 BLUE를 bluecolor 변수에 저장
        int redcolor = getColor(R.color.RED); //R.color의 RED를 redcolor 변수에 저장
        textView = findViewById(R.id.textView); //textView의 ID 값을 가져옴
        LEDimage = findViewById(R.id.imageView2); //imageView2의 ID 값을 가져옴
        setTitle(("LED Remote Control")); //화면 상단 타이틀 텍스트 지정
        LEDimage.setImageResource(R.drawable._f4a12); //ImageView에 표시될 사진을 지정
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(); //Instance값을 변수에 저장
        DatabaseReference myRef = database.getReference("LED_STATUS"); //RealTime DB의 LED_Status 값을 myRef 변수에 저장

        myRef.setValue("Hello, World!"); //LED_Status의 값을 Hello World로 지정
        textView.setText(myRef.getKey()); //TextView에 myRef값 지정


        // read from the Database, addValue Event Listenning
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue(); //datasnapshot의 값을 ledstate 변수에 저장
                //String value = dataSnapshot.getValue(String.class);

                textView.setText("LED is " + ledState); //textView에 ledState값 지정

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onButton.setOnClickListener(new View.OnClickListener() { //버튼 클릭 이벤트 리스너 지정

            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.RED); //Textview 배경색 Red지정
                // write to the Database
                myRef.setValue("ON"); //myRef값 On으로 지정
                LEDimage.setImageResource(R.drawable._f4a1); //ImageView의 사진을 지정
                onButton.setBackgroundColor(redcolor); //버튼의 색을 redcolor값으로 지정
                offButton.setBackgroundColor(bluecolor); //버튼의 색을 bluecolor값으로 지정


            }
        });

        offButton.setOnClickListener(new View.OnClickListener() { //버튼 클릭 이벤트 리스너 지정
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                textView.setBackgroundColor(Color.BLUE); //Textview 배경색 blue지정
                // write to the Database
                myRef.setValue("OFF"); //myRef값 Off으로 지정
                LEDimage.setImageResource(R.drawable._f4a12); //ImageView의 사진을 지정
                onButton.setBackgroundColor(bluecolor); //버튼의 색을 bluecolor값으로 지정
                offButton.setBackgroundColor(redcolor); //버튼의 색을 redcolor값으로 지정

            }
        });
    }
}