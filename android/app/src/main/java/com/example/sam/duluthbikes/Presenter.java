package com.example.sam.duluthbikes;

import android.app.Activity;
import android.content.Context;

/**
<<<<<<< HEAD
 * Created by Sam on 3/26/2017.
 */

public class Presenter implements ModelViewPresenterComponents.PresenterContract {

    private ModelViewPresenterComponents.Model mModel;
    private ModelViewPresenterComponents.View mView;
    private Context mContext;
    private Activity mActivity;

    public Presenter(Context context, Activity activity,ModelViewPresenterComponents.View view){
        mView = view;
        mContext = context;
        mActivity = activity;

    }

    @Override
    public void updateMapLocation(){
        mView.locationChanged(mModel.getLocation());
    }

    @Override
    public void clickStart(){
        mModel = new Model(mContext,mActivity,this);
    }

    @Override
    public void endRideButton() { mModel.stopLocationUpdates(); }

    @Override
    public void connectApi() { mModel.connectApiOnResume(); }

}
