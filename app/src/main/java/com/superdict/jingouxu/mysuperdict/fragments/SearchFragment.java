package com.superdict.jingouxu.mysuperdict.fragments;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.superdict.jingouxu.mysuperdict.R;
import com.superdict.jingouxu.mysuperdict.activities.NavigationDrawerActivity;
import com.superdict.jingouxu.mysuperdict.utils.Constants;
import com.superdict.jingouxu.mysuperdict.utils.RequestTask;
import com.superdict.jingouxu.mysuperdict.utils.Utils;

import java.util.ArrayList;

import static com.superdict.jingouxu.mysuperdict.activities.NavigationDrawerActivity.ActivityUsage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener,  RequestTask.CallBack {

    public final String TAG = ((Object)this).getClass().getSimpleName();
    private OnFragmentInteractionListener mListener;
    private ActivityUsage mUsage;
    private String mTranslation;
    private String mQuery;

    public static SearchFragment newInstance(Bundle args) {
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsage = ActivityUsage.values()[getArguments().getInt(Constants.ACTIVITY_USAGE)];
        }

        switch (mUsage){
            case SEARCH_RESULT:
                mQuery = getArguments().getString(Constants.QUERY);
                RequestTask requestTask = new RequestTask(this);
                requestTask.execute(Utils.generateUrl(mQuery));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<String> list = ((NavigationDrawerActivity)getActivity()).wordList;
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        SearchView sv = (SearchView)rootView.findViewById(R.id.search_word_view);
        SearchManager sm = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
//        sv.setOnQueryTextListener(this);
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "it's changed");

        return false;
    }

    @Override
    public void onAsyncReturned(String result) {
        mTranslation = result;
        Log.d(TAG, mTranslation);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume(){
        super.onResume();
    }


}
