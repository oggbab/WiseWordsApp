package com.neomu.neomu;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    Button main_join_btn;
    EditText nickName, email, pw, pw2;
    RadioGroup gender;
    FirebaseAuth firebaseAuth;
    String mail, password;
    boolean result_create=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        firebaseAuth = FirebaseAuth.getInstance();

        main_join_btn = findViewById(R.id.main_join_btn);
        main_join_btn.setOnClickListener(this);

        nickName = findViewById(R.id.main_join_nickName);
        email = findViewById(R.id.main_join_email);
        pw = findViewById(R.id.main_join_pw);
        pw2 = findViewById(R.id.main_join_pw2);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.join_gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio_btn = (RadioButton) findViewById(checkedId);
            }
        });


    }

    @Override
    public void onClick(View v) {

        mail = email.getText().toString();
        password = pw.getText().toString();

        //이메일 유효성검사
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(JoinActivity.this, "올바른 메일주소 형식이 아닙니다..", Toast.LENGTH_SHORT).show();

        } else {

            //비밀번호 일치 확인
            if (password.equals(pw2.getText().toString())) {

                //계정생성
                createUser(mail, password);
/*                    if(result_create==false)
                do{
                    {Toast.makeText(JoinActivity.this, "이미 있는 계정입니다.", Toast.LENGTH_SHORT).show();}
                }while(result_create==false);
                    result_create=true;*/


                //다음 페이지에 전달해두기
                intent = new Intent(JoinActivity.this, JoinDetailActivity.class);

                //라디오 체크한 값
                RadioGroup rg = (RadioGroup) findViewById(R.id.join_gender); // 라디오그룹 객체 맵핑
                RadioButton selectedRdo = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                String gender = selectedRdo.getText().toString(); // 해당 라디오버튼 객체의 값 가져오기
                intent.putExtra("gender", gender);
                //나머지
                intent.putExtra("nickName", nickName.getText().toString());
                intent.putExtra("email", email.getText().toString());
                intent.putExtra("pw", pw.getText().toString());

                startActivity(intent);
            }


        }


    }


    // 회원가입
    private boolean createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    }
                });
        return result_create=true;
    }
}
