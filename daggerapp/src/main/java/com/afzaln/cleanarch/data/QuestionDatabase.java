package com.afzaln.cleanarch.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by afzal on 2015-12-22.
 */
@Database(name = QuestionDatabase.NAME, version = QuestionDatabase.VERSION)
public class QuestionDatabase {
    public static final String NAME = "Questions";
    public static final int VERSION = 1;
}
