package com.imibragimov.loftmoney.screens.money;

import android.content.SharedPreferences;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.imibragimov.loftmoney.LoftApp;
import com.imibragimov.loftmoney.R;
import com.imibragimov.loftmoney.cell.ItemModel;
import com.imibragimov.loftmoney.remote.MoneyApi;
import com.imibragimov.loftmoney.remote.MoneyRemoteItem;
import com.imibragimov.loftmoney.screens.balance.BalanceFragment;
import com.imibragimov.loftmoney.screens.main.MainActivity;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BudgetViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<ItemModel>> moneyItemsList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isEditMode = new MutableLiveData<>(false);
    public MutableLiveData<Integer> selectedCounter = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isNeedLoadData = new MutableLiveData<>(false);
    public List<ItemModel> selectedItems = new ArrayList<>();
    private String typeRequest;
    private BudgetFragment budgetFragment = new BudgetFragment();
    public void selectItem(ItemModel item) {
        selectedItems.add(item);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void loadIncomes(MoneyApi moneyApi, int currentPosition, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");


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
                        moneyItemModels.add(new ItemModel(moneyRemoteItem.getItemId(), moneyRemoteItem.getName(), moneyRemoteItem.getPrice(), currentPosition));
                    }

                    moneyItemsList.postValue(moneyItemModels);

                }, throwable -> {
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }


    private int countForClear = 0;

    public void removeItems(MoneyApi moneyApi, SharedPreferences sharedPreferences, List<ItemModel> items) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        for (ItemModel item : items) {
            compositeDisposable.add(moneyApi.removeItem(item.getId(), authToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(authResponse -> {
                        countForClear++;
                        if (countForClear == items.size()) {
                            isNeedLoadData.postValue(true);
                            selectedItems.clear();
                        }
                        List<ItemModel> moneyItemModels = new ArrayList<>();
                        moneyItemsList.postValue(moneyItemModels);
                    }, throwable -> {
                        isNeedLoadData.postValue(true);
                        selectedItems.clear();
                        messageString.postValue(throwable.getLocalizedMessage());
                    }));
        }
        //*****************************************
        //(new BalanceFragment()).loadBalance();
        //*****************************************
    }

}
