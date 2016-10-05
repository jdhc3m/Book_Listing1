package com.example.jd158.booklisting;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jd158.booklisting.Book;
import com.example.jd158.booklisting.R;

import java.util.ArrayList;

/**
 * Created by jd158 on 5/10/2016.
 */
public class BookAdapter extends ArrayAdapter {
   

        //We initialize the ArrayAdapter's internal storage for the context and the list.
        public BookAdapter(Activity context, ArrayList<Book> books) {
            super(context, 0, books);

        }

        /**
         * Provides a view for an AdapterView (ListView, GridView, etc.)
         *
         * @param position The position in the list of data that should be displayed in the
         *                 list item view.
         * @param convertView The recycled view to populate.
         * @param parent The parent ViewGroup that is used for inflation.
         * @return The View for the position in the AdapterView.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.book_list_item, parent, false);
            }

            // Get the {@link Earthquake} object located at this position in the list
            Book currentBook = (Book) getItem(position);

            // Find the ImageView in the list_item.xml layout with the ID version_name
            TextView authorView = (TextView) listItemView.findViewById(R.id.author);

            // set this text on the name TextView
            authorView.setText(currentBook.getAuthor());

            // Find the ImageView in the list_item.xml layout with the ID version_name
            TextView titleView = (TextView) listItemView.findViewById(R.id.title);

            // set this text on the name TextView
            titleView.setText(currentBook.getTitle());


            return listItemView;

        }
}
