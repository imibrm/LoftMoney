package com.imibragimov.loftmoney;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddItemActivity extends AppCompatActivity {

    private EditText NameEditText;
    private EditText PriceEditText;
    private Button addButton;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String mPrice;
    private String mName;
    private String fragmentType;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        NameEditText = (EditText) findViewById(R.id.et_name);
        NameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mName = s.toString();
                checkEditTextHasText();
            }
        });
        PriceEditText = (EditText) findViewById(R.id.et_price);
        PriceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPrice = s.toString();
                checkEditTextHasText();
            }
        });

        addButton =(Button) findViewById(R.id.btn_add);

        Bundle indexBundle = getIntent().getExtras();
        int currentPosition = indexBundle.getInt("activeFragmentIndex");
        if (currentPosition == 0) {
            fragmentType = "expense";
        } else {
            fragmentType = "income";
        }

        if (currentPosition == 0) {
            NameEditText.setTextColor(getResources().getColor(R.color.priceColor));
            PriceEditText.setTextColor(getResources().getColor(R.color.priceColor));
            addButton.setTextColor(getResources().getColorStateList(R.color.add_button_text_color_selector_expense, null));
        } else {
            NameEditText.setTextColor(getResources().getColor(R.color.priceColor_2));
            PriceEditText.setTextColor(getResources().getColor(R.color.priceColor_2));
            addButton.setTextColor(getResources().getColorStateList(R.color.add_button_text_color_selector_income, null));
        }

        configureButton();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }


    private void configureButton() {
        addButton.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice)) {
                setResult(
                        RESULT_OK,
                        new Intent().putExtra("name", mName).putExtra("price", mPrice));
                finish();

                Disposable disposable = ((LoftApp) getApplication()).moneyApi.postMoney(
                        Integer.parseInt(PriceEditText.getText().toString()),
                        NameEditText.getText().toString(), fragmentType,
                        getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.AUTH_KEY, ""))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Toast.makeText(getApplicationContext(), getString(R.string.success_added), Toast.LENGTH_LONG).show();
                            finish();
                        }, throwable -> Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show());
            }
        });

    }
    public void checkEditTextHasText() {
        addButton.setEnabled(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice));
    }

}
