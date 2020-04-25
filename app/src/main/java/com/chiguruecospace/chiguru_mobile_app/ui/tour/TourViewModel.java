package com.chiguruecospace.chiguru_mobile_app.ui.tour;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TourViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TourViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tour fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}