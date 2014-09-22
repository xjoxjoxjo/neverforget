package com.superdict.jingouxu.mysuperdict.utils;

import android.app.Activity;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by jingouxu on 9/24/14.
 */
public class WordList {
    private static WordList instance = null;
    public static ArrayList<String> list = new ArrayList<String>(Constants.WORDS_LIST_LENGTH);

    protected WordList(Activity activity) throws IOException {
        AssetManager assetManager = activity.getAssets();
        InputStream in = assetManager.open("words");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int i = 0;
        String line;
        while ((line = reader.readLine()) != null){
            list.add(line.trim());
            i++;
        }
        in.close();
        reader.close();
    }

    public static WordList getInstance(Activity activity) throws IOException {
        if (instance == null){
            instance = new WordList(activity);
        }
        return instance;
    }
}
