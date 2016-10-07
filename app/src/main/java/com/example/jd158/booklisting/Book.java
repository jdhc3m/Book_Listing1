package com.example.jd158.booklisting;

import java.util.ArrayList;

/**
 * Created by jd158 on 5/10/2016.
 */
public class Book extends ArrayList {
    

    // Author of the Book
    private ArrayList mAuthor;

    /** Title of the book */
    private String mTitle;

    // URL of Earthquake page
    private String mUrl;



    public Book(ArrayList Author, String Title)  {
        mAuthor = Author;
        mTitle = Title;
    }

    
    public ArrayList getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }


}
