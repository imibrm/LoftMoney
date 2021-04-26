package com.imibragimov.loftmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.imibragimov.loftmoney.BudgetFragment.ARG_BUDGET;
import static com.imibragimov.loftmoney.BudgetFragment.ARG_VALUE;
import static com.imibragimov.loftmoney.BudgetFragment.REQUEST_CODE;

public class AddItemActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mPriceEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mNameEditText = findViewById(R.id.name_edittext);
        mPriceEditText = findViewById(R.id.price_edittext);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra(ARG_BUDGET, mNameEditText.getText().toString());
                intent.putExtra(ARG_VALUE, mPriceEditText.getText().toString());
                setResult(REQUEST_CODE, intent);
                finish();
            }
        });
    }
}
