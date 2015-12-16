package com.afzaln.cleanarch.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by afzal on 2015-12-07.
 */
public class Question implements Serializable {
    public String question;
    public String published_at;
    public String url;
    public ArrayList<Choice> choices;

    private int id = -1;

    public int getId() {
        if (id == -1) {
            String[] questionUrl = url.split("/");
            id = Integer.parseInt(questionUrl[questionUrl.length - 1]);
        }

        return id;
    }
}
