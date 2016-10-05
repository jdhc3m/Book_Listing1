package com.example.jd158.booklisting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
    public void search (View view) {

        EditText textSearchBook = (EditText) findViewById(R.id.search_text);

        // Create a new intent to open the {@link NumbersActivity}
        Intent bookIntent = new Intent(MainActivity.this, BookActivity.class);

        // Use this to pass textSearchBook to BookActivity
        bookIntent.putExtra("bookText", textSearchBook.getText().toString());

        // Start the new activity
        startActivity(bookIntent);
    }
}

