package com.example.kenkina.heroapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.kenkina.heroapp.R;

public class NewHeroActivity extends AppCompatActivity {

    public static final String EXTRA_HERO_SERVER_ID = "com.example.kenkina.heroapp.HERO_ID";
    public static final String EXTRA_HERO_NAME = "com.example.kenkina.heroapp.HERO_NAME";
    private EditText mIdEditText, mNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hero);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIdEditText = findViewById(R.id.idEditText);
        mNameEditText = findViewById(R.id.nameEditText);

        FloatingActionButton fab = findViewById(R.id.newHeroFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (TextUtils.isEmpty(mIdEditText.getText()) || TextUtils.isEmpty(mNameEditText.getText())) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    String id = mIdEditText.getText().toString();
                    String name = mNameEditText.getText().toString();
                    intent.putExtra(EXTRA_HERO_SERVER_ID, id);
                    intent.putExtra(EXTRA_HERO_NAME, name);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
