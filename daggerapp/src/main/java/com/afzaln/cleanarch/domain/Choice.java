package com.afzaln.cleanarch.domain;

/**
 * Created by afzal on 2015-12-07.
 */
public class Choice {
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
