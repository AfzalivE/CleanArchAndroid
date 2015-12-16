package com.afzaln.cleanarch.domain;

import java.io.Serializable;

/**
 * Created by afzal on 2015-12-07.
 */
public class Choice implements Serializable {
    public String choice;
    public String url;
    public int votes;

    private int id = -1;

    public int getId() {
        if (id == -1) {
            String[] choiceUrl = url.split("/");
            id = Integer.parseInt(choiceUrl[choiceUrl.length - 1]);
        }

        return id;
    }
}
