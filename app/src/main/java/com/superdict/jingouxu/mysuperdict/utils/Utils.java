package com.superdict.jingouxu.mysuperdict.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jingouxu on 9/21/14.
 */
public class Utils {
    public static String generateUrl(String query){
        return "http://fanyi.youdao.com/openapi.do?keyfrom=SunnyDict&key=159753549&type=data&doctype=json&version=1.1&q=" + query;
    }
}
