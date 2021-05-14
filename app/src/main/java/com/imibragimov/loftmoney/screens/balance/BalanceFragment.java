package com.imibragimov.loftmoney.screens.balance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.imibragimov.loftmoney.LoftApp;
import com.imibragimov.loftmoney.R;
import com.imibragimov.loftmoney.remote.BalanceResponse;
import com.imibragimov.loftmoney.remote.MoneyApi;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class BalanceFragment extends Fragment {

    private TextView balance, expense, income;
    private BalanceView balanceView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        balance = view.findViewById(R.id.txtBalanceFinanceValue);
        expense = view.findViewById(R.id.txtExpenseValue);
        income = view.findViewById(R.id.txtIncomeValue);
        balanceView = view.findViewById(R.id.balanceView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_balance);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBalance();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadBalance();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void loadBalance() {
        String authToken = getActivity().getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.AUTH_KEY, "");
        Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().getBalance(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BalanceResponse>() {
                    @Override
                    public void accept(BalanceResponse balanceResponse) throws Exception {
                        String balanceString = String.valueOf((int)balanceResponse.getTotalIncome() - (int)balanceResponse.getTotalExpenses());

                        balance.setText(balance.getContext().getResources().getString(R.string.price_with_currency, balanceString));
                        income.setText(income.getContext().getResources().getString(R.string.price_with_currency, String.valueOf((int)balanceResponse.getTotalIncome())));
                        expense.setText(expense.getContext().getResources().getString(R.string.price_with_currency, String.valueOf((int)balanceResponse.getTotalExpenses())));

                        balanceView.update(balanceResponse.getTotalExpenses(), balanceResponse.getTotalIncome());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Toast.makeText(getActivity().getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        compositeDisposable.add(disposable);
    }
}
