package com.superdict.jingouxu.mysuperdict.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.superdict.jingouxu.mysuperdict.R;
import com.superdict.jingouxu.mysuperdict.utils.Constants;
import com.superdict.jingouxu.mysuperdict.utils.Utils;
import com.superdict.jingouxu.mysuperdict.utils.WordList;

import java.io.IOException;

/**
 * The login screen displaying animation to the user
 */
public class WelcomeActivity extends Activity {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        try {
            WordList.getInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //start your activity here
                Intent intent = new Intent(getApplicationContext(), NavigationDrawerActivity.class);
                intent.putExtra(Constants.ACTIVITY_USAGE, NavigationDrawerActivity.ActivityUsage.MAIN.ordinal());
                startActivity(intent);
                finish();
            }

        }, 3000L);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
