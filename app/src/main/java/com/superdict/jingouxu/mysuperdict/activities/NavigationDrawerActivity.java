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

import static com.superdict.jingouxu.mysuperdict.activities.NavigationDrawerActivity.ActivityUsage.*;

public class NavigationDrawerActivity extends SherlockFragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ActivityUsage mUsage;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public enum ActivityUsage{
        MAIN
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
        selectFragment();
    }

    @Override
    protected void onNewIntent(Intent intent){
        setIntent(intent);
        handleIntent(intent);
    }

    private void selectFragment(){
        Fragment fragment = null;
        switch (mUsage){
            case MAIN:
                fragment = SearchFragment.newInstance();
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
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        mUsage = ActivityUsage.values()[intent.getIntExtra(Constants.ACTIVITY_USAGE, MAIN.ordinal())];
    }

    private void doMySearch(String query){

    }

}
