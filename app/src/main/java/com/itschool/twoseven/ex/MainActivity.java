package com.itschool.twoseven.ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_text  = findViewById(R.id.tw_text);
    }

    public void toNext(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        intent.putExtra("keyOne", "Some String");
        startActivityForResult(intent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                tv_text.setText(data.getStringExtra("keyOne"));
        }

    }

    public void callIntent(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Text");
        intent.setType("text/plain");
        startActivity(intent);
    }
}
