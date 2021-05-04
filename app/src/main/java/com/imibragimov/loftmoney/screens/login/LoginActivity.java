package com.imibragimov.loftmoney.screens.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.imibragimov.loftmoney.LoftApp;
import com.imibragimov.loftmoney.R;
import com.imibragimov.loftmoney.screens.main.MainActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configureView();
        configureViewModel();
        configureGoogleAuth();
    }

    private void configureGoogleAuth() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if(account != null) {
                loginViewModel.makeLogin(((LoftApp) getApplication()).authApi, account.getId());
            } else {
                Log.e("TAG", "Can't parse account");
            }
        } catch (ApiException e) {
                Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void configureView() {
        AppCompatButton loginEnterView = findViewById(R.id.login_enter_view);

        loginEnterView.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private void configureViewModel() {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.messageString.observe(this,error ->{
            if(!TextUtils.isEmpty(error)) {

            }
            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
        });

        loginViewModel.authToken.observe(this, authToken -> {
            if (!TextUtils.isEmpty((authToken))) {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), 0);
                sharedPreferences.edit().putString(LoftApp.AUTH_KEY, authToken).apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
