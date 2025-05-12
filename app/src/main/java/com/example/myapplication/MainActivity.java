    package com.example.myapplication;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;

    import com.google.firebase.FirebaseApp;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.FirebaseAuth;

    public class MainActivity extends AppCompatActivity {
        EditText email;
        EditText password;
        FirebaseAuth mAuth;
        Button emailLoginButton;
        sadsad
        TextView textview;
        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            FirebaseApp.initializeApp(this);

            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();

            email = findViewById(R.id.email_editText);
            password = findViewById(R.id.password_editText);
            emailLoginButton = findViewById(R.id.email_login_button);


            emailLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String emailText = email.getText().toString(); //텍스트 추출
                    String passwordtext = password.getText().toString();
                    mAuth.signInWithEmailAndPassword(emailText,passwordtext).addOnCompleteListener(task->{
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                            //intetn에 있는 정보를 통해 엑티비티를 실행하겠다
                            startActivity(intent);
                            //현재 엑티비티 종료
                            finish();
                        }
                        else{
                            mAuth.createUserWithEmailAndPassword(emailText,passwordtext).addOnCompleteListener(task1->{
                                if(task1.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                }
                                else{
                                    Exception e = task1.getException();
                                    Toast.makeText(MainActivity.this, "실패: " + e.getMessage(), Toast.LENGTH_LONG).show();                                }
                            });
                        }
                    });
                    // context란 현재 앱 내에서 내 위치가 어디인지
                    //화면 변환(엑티비티 변환)                 context            내가 이동할 클래스
                    // 결론은 내가 있는 현재위치에서 내가 이동할 클래스로 엑티비티 변환해주는거
                }
            });
        }
    }