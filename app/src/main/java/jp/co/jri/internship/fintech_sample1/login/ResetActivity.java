package jp.co.jri.internship.fintech_sample1.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;


import androidx.lifecycle.ViewModelProvider;

import jp.co.jri.internship.fintech_sample1.Main2Activity;
import jp.co.jri.internship.fintech_sample1.R;

public class ResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // ウィジェットの参照を取得
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button sendButton = findViewById(R.id.sendButton);
        TextView resultTextView = findViewById(R.id.resultTextView);
        // 戻るボタンの参照を取得
        Button backButton = findViewById(R.id.backButton);

        resultTextView.setVisibility(View.GONE); // 初期状態では非表示

        sendButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();

            // メールアドレスの検証
            if (!email.contains("@")) {
                // メールアドレスが不正な場合
                resultTextView.setText("メールアドレスの入力が不正です");

                // 全ての入力欄を空にする
                nameEditText.setText("");
                emailEditText.setText("");
                passwordEditText.setText("");
            } else {
                // メールアドレスが正しい場合
                // 送信成功メッセージを表示
                resultTextView.setText("送信されました");

                // 全ての入力欄を空にする
                nameEditText.setText("");
                emailEditText.setText("");
                passwordEditText.setText("");
            }
            resultTextView.setVisibility(View.VISIBLE);

        });
        // 戻るボタンのクリックリスナー
        backButton.setOnClickListener(v -> {
            // LoginActivityに戻る
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}