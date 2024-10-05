package com.example.healthbuddypro.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;  // 로그를 위한 import
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthbuddypro.ApiService;
import com.example.healthbuddypro.MainActivity;
import com.example.healthbuddypro.R;
import com.example.healthbuddypro.RetrofitClient;
import com.example.healthbuddypro.SignUp.SignupActivity;
import com.example.healthbuddypro.SignUp.SignupActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private final String BASE_URL = "http://165.229.89.154:8080/"; // 실제 AWS API URL로 변경
    private static final String TAG = "LoginActivity";  // 로그 태그

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // UI 요소 초기화
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        // 로그인 버튼 클릭 리스너
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        // 회원가입 버튼 클릭 리스너
        findViewById(R.id.buttonSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void performLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "아이디와 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 로그인 요청 객체 생성
        LoginRequest loginRequest = new LoginRequest(username, password);

        // Retrofit을 통해 로그인 요청 보내기
        ApiService apiService = RetrofitClient.getApiService(BASE_URL);
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 로그인 성공 로그 출력
                    Log.d(TAG, "로그인 성공: " + response.body().toString());

                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    // 로그인 성공 후 메인 화면으로 이동
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 서버에서 반환된 오류에 대한 자세한 정보 로그
                    logDetailedErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // 네트워크 또는 기타 오류 로그 출력
                Log.e(TAG, "네트워크 오류: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logDetailedErrorResponse(Response<LoginResponse> response) {
        try {
            // HTTP 응답 코드 로그 (예: 403, 500 등)
            Log.e(TAG, "HTTP 상태 코드: " + response.code());

            // 에러 바디가 있을 경우 상세 에러 메시지 출력
            if (response.errorBody() != null) {
                String errorBody = response.errorBody().string();
                Log.e(TAG, "에러 메시지: " + errorBody);
            }

            // 서버에서 추가로 제공한 헤더 출력 (필요한 경우)
            for (String header : response.headers().names()) {
                Log.e(TAG, "헤더: " + header + " = " + response.headers().get(header));
            }

        } catch (Exception e) {
            Log.e(TAG, "에러 응답 처리 중 오류: " + e.getMessage());
        }

        // 403 오류일 경우 사용자에게 친절한 메시지 출력
        if (response.code() == 403) {
            Log.e(TAG, "로그인 실패: 권한 없음 (403)");
            Toast.makeText(LoginActivity.this, "로그인 실패: 권한 없음", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "로그인 실패: " + response.code());
            Toast.makeText(LoginActivity.this, "로그인 실패: " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }


}
