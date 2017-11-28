package com.mr.rohmani.moviezamannow.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;

/**
 * Created by USER on 26/11/2017.
 */

public class Base extends Fragment {
    private ProgressDialog mProgressDialog;


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }
    //hidding progress dialog
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
