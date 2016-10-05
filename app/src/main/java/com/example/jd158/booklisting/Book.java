package com.example.jd158.booklisting;

/**
 * Created by jd158 on 5/10/2016.
 */
public class Book {
    

    // Author of the Book
    private String mAuthor;

    /** Title of the book */
    private String mTitle;

    // URL of Earthquake page
    private String mUrl;


    public Book(String Author, String Title, String Url)  {
        mAuthor = Author;
        mTitle = Title;
        mUrl = Url;
    }

    public Book(String Author, String Title)  {
        mAuthor = Author;
        mTitle = Title;
    }

    
    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
