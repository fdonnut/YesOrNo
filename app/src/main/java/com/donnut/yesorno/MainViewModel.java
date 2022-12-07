package com.donnut.yesorno;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    private final MutableLiveData<YesNoImage> yesNoImage = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<YesNoImage> getYesNoImage() {
        return yesNoImage;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsError() {
        return isError;
    }

    public void loadYesNoImage() {
        Disposable disposable = loadYesNoImageRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable1 -> {
                    isError.setValue(false);
                    isLoading.setValue(true);
                })
                .doAfterTerminate(() -> isLoading.setValue(false))
                .doOnError(throwable -> isError.setValue(true))
                .subscribe(image -> {
                    Log.d(TAG, "" + image);
                    yesNoImage.setValue(image);
                }, throwable -> Log.d(TAG, "Error: " + throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private Single<YesNoImage> loadYesNoImageRx() {
        return ApiFactory.getApiService().loadYesNoImage();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
