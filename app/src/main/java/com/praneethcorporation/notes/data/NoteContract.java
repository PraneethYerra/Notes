package com.praneethcorporation.notes.data;

import android.provider.BaseColumns;

/**
 * Created by user on 9/30/2017.
 */

public class NoteContract  {
    NoteContract(){

    }

    public class NoteEntry implements BaseColumns{
        public static final String TABLE_NAME = "note_info";
        public static final String Id = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_TIME = "time";
    }
}
