package com.itschool.twoseven.ex;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int RESULT_SPEECH = 1;
    private static final int PUT_TO_ANOTHER = 2;
    TextView tv_text;
    Button bt_Show;
    ImageView iv_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv_text = findViewById(R.id.tw_text);
        bt_Show = findViewById(R.id.bt_show);
        iv_image = findViewById(R.id.image);

        bt_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        "ru-RU");
                recognizeSpeech(intent);

            }
        });
    }

    private void recognizeSpeech(Intent intent) {
        try {
            startActivityForResult(intent, RESULT_SPEECH);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "текст не распознан",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void toNext(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        intent.putExtra("keyOne", "Some String");
        startActivityForResult(intent, PUT_TO_ANOTHER);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PUT_TO_ANOTHER:
                tv_text.setText(data.getStringExtra("keyOne"));
                break;
            case RESULT_SPEECH:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (text.get(0).equalsIgnoreCase("Хочу кота")) {
                        try {
                            setImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    tv_text.setText(text.get(0));
                }


        }

    }

    @SuppressLint("StaticFieldLeak")
    private void setImage() throws IOException {

        new AsyncTask<Void, Void, Bitmap>() {
            Bitmap cat;

            @Override
            protected Bitmap doInBackground(Void... voids) {
                URL url = null;
                try {
                    url = new URL("https://i.redd.it/0nybg7zed9w21.png");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    cat = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return cat;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                iv_image.setImageBitmap(bitmap);
                iv_image.setVisibility(View.VISIBLE);
            }
        }.execute();


    }


    public void callIntent(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Text");
        intent.setType("text/plain");
        startActivity(intent);
    }
}
