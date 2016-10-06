package com.example.jd158.booklisting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    BookAdapter adapterGlobal;
    ListView bookListViewGlobal;
    ArrayList<Book> booksGlobal;
    /**
     * Tag for the log messages
     *
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    // String to get the fields from JSON.

    public static String title = null;
    public static String author = null;



/**
     * URL to query the USGS dataset for Books information
     */
    private static String USGS_REQUEST_URL = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        Intent intent = getIntent();
        String userInput = intent.getStringExtra("bookText");

        USGS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

        USGS_REQUEST_URL += userInput.replaceAll(" ","+") + "&maxResults=20";

        // Kick off an {@link AsyncTask} to perform the network request
        BookAsyncTask task = new BookAsyncTask();
        task.execute();

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Book> books = new ArrayList<>();

        booksGlobal = books;

        // Create a new {@link ArrayAdapter} of earthquakes
        BookAdapter adapter = new BookAdapter(this, books);
        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);

        bookListViewGlobal = bookListView;
        adapterGlobal = adapter;


    }



    /**
     * Update the screen to display information from the given {@link Book}.
     */
    private void updateUi(Book book) {

    }
    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first book in the response.
     */
    public class BookAsyncTask extends AsyncTask<URL, Void, Book> {

        @Override
        protected Book doInBackground(URL... urls) {
            // Create URL object
            URL url = null;
            try {


                url = createUrl(USGS_REQUEST_URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Book} object
            Book book = extractFeatureFromJson(jsonResponse);

            // Return the {@link Book} object as the result fo the {@link TsunamiAsyncTask}
            return book;
        }

        /**
         * Update the screen with the given books (which was the result of the
         * {@link BookAsyncTask}).
         */
        @Override
        protected void onPostExecute(Book book) {
            if (book == null) {
                return;
            }
            // so the list can be populated in the user interface
           // bookListViewGlobal.setAdapter(adapterGlobal);
            adapterGlobal.addAll(booksGlobal);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) throws MalformedURLException {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                // if the request was successful (response code 200),
                // the read the input strem and parse the response
                if (urlConnection.getResponseCode() == 200) {

                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);

                } else {
                    Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                // TODO: Handle the exception
                Log.e(LOG_TAG, "Problem retrieving the book results", e);

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link Book} object by parsing out information
         * about the first book from the input bookJSON string.
         */
        private Book extractFeatureFromJson(String bookJSON) {
            // if the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(bookJSON)) {
                return null;
            }

            try {
                JSONObject baseJsonResponse = new JSONObject(bookJSON);
                JSONArray bookArray = baseJsonResponse.getJSONArray("items");

                // Loop through each feature in the array
                for(int i = 0; i < bookArray.length(); i++) {
                    // Extract out the first feature (which is an book)
                    JSONObject firstFeature = bookArray.getJSONObject(i);
                    JSONObject properties = firstFeature.getJSONObject("volumeInfo");
                    // Extract out the title and Author values
                    title = properties.getString("title");
                    author = properties.getString("authors");

                    // Create a new {@link Book} object
                    booksGlobal.add(new Book(title, author));
                    return new Book(title, author);

                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the book JSON results", e);

            }
            //books.add(new Book("Book not found, please try again", ""));
            return null;
        }
    }
}
