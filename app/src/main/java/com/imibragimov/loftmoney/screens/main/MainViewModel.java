package com.imibragimov.loftmoney.screens.main;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imibragimov.loftmoney.LoftApp;
import com.imibragimov.loftmoney.cell.ItemModel;
import com.imibragimov.loftmoney.remote.MoneyApi;
import com.imibragimov.loftmoney.remote.MoneyRemoteItem;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<ItemModel>> moneyItemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadIncomes(MoneyApi moneyApi, int currentPosition, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        String typeRequest;

        if (currentPosition == 0) {
            typeRequest = "expense";
        } else {
            typeRequest = "income";
        }

        compositeDisposable.add(moneyApi.getMoneyItems(typeRequest, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyRemoteItems -> {
                    List<ItemModel> moneyItemModels = new ArrayList<>();

                    for (MoneyRemoteItem moneyRemoteItem : moneyRemoteItems) {
                        moneyItemModels.add(new ItemModel(moneyRemoteItem.getName(), moneyRemoteItem.getPrice(), currentPosition));
                    }
                    moneyItemsList.postValue(moneyItemModels);
                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }
}
