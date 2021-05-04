package com.imibragimov.loftmoney.screens.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imibragimov.loftmoney.remote.AuthApi;
import com.imibragimov.loftmoney.remote.AuthResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<String> authToken = new MutableLiveData<>("");

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    void makeLogin(AuthApi authApi, String userId) {
        compositeDisposable.add(authApi.makeLogin(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(authResponse -> {
                    authToken.postValue(authResponse.getAuthToken());
                }, (Consumer<Throwable>) throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }
}
