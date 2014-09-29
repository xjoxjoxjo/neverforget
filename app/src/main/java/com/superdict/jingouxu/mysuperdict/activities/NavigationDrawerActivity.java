package com.superdict.jingouxu.mysuperdict.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.superdict.jingouxu.mysuperdict.fragments.NavigationDrawerFragment;
import com.superdict.jingouxu.mysuperdict.R;
import com.superdict.jingouxu.mysuperdict.fragments.SearchFragment;
import com.superdict.jingouxu.mysuperdict.utils.Constants;
import com.superdict.jingouxu.mysuperdict.utils.WordList;

import java.io.IOException;
import java.util.ArrayList;

import static com.superdict.jingouxu.mysuperdict.activities.NavigationDrawerActivity.ActivityUsage.*;

public class NavigationDrawerActivity extends SherlockFragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchFragment.OnFragmentInteractionListener {

    public final String TAG = ((Object)this).getClass().getSimpleName();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private String mQuery;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ActivityUsage mUsage;

    public ArrayList<String> wordList;

    public NavigationDrawerActivity() {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public enum ActivityUsage{
        MAIN,
        SEARCH_RESULT
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        handleIntent(getIntent());
        try {
            wordList = WordList.getInstance(this).list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectFragment();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mQuery = null;
    }

    @Override
    protected void onNewIntent(Intent intent){
        setIntent(intent);
        handleIntent(intent);
    }

    private void selectFragment(){
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (mUsage){
            case MAIN:
                args.putInt(Constants.ACTIVITY_USAGE, MAIN.ordinal());
                fragment = SearchFragment.newInstance(args);
                break;
            case SEARCH_RESULT:
                args.putInt(Constants.ACTIVITY_USAGE, SEARCH_RESULT.ordinal());
                args.putString(Constants.QUERY, mQuery);
                fragment = SearchFragment.newInstance(args);
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().
                replace(R.id.container, fragment).
                commit();
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
/*        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();*/
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    private void handleIntent(Intent intent){
        mUsage = ActivityUsage.values()[intent.getIntExtra(Constants.ACTIVITY_USAGE, MAIN.ordinal())];
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query){
        mQuery = query;
        selectFragment();
    }


    @Override
    public void startActivity(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            intent.putExtra(Constants.ACTIVITY_USAGE, NavigationDrawerActivity.ActivityUsage.SEARCH_RESULT.ordinal());
        }
        super.startActivity(intent);
    }
}
