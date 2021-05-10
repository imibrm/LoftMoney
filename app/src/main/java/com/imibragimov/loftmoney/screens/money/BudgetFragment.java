package com.imibragimov.loftmoney.screens.money;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.imibragimov.loftmoney.LoftApp;
import com.imibragimov.loftmoney.R;
import com.imibragimov.loftmoney.cells.ItemModel;
import com.imibragimov.loftmoney.cells.ItemsAdapter;
import com.imibragimov.loftmoney.cells.ItemsAdapterClick;

import com.imibragimov.loftmoney.screens.dashboard.EditModeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment implements BudgetEditListener {

    public static final int REQUEST_CODE = 0;
    public static final String ARG_POSITION = "position";
    private RecyclerView recyclerView;
    private final ItemsAdapter itemsAdapter = new ItemsAdapter();
    private List<ItemModel> moneyItemModels = new ArrayList<>();
    private int currentPosition;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private BudgetViewModel budgetViewModel;
    public FloatingActionButton addNewItem;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_POSITION);
        }

        itemsAdapter.setItemsAdapterClick(new ItemsAdapterClick() {
            @Override
            public void onCellClick(ItemModel itemModel) {
                if (budgetViewModel.isEditMode.getValue()) {
                    itemModel.setSelected(!itemModel.isSelected());
                    itemsAdapter.updateItem(itemModel);
                    checkSelectedCount();
                }

            }

            @Override
            public void onLongCellClick(ItemModel itemModel) {
                if (!budgetViewModel.isEditMode.getValue())
                    itemModel.setSelected(true);
                itemsAdapter.updateItem(itemModel);
                budgetViewModel.isEditMode.postValue(true);
                checkSelectedCount();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addNewItem = view.findViewById(R.id.add_new_item);

        configureViewModel();
        configureView(view);

    }

    private void configureView(View view) {

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setAdapter(itemsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);


        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                budgetViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi, currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        budgetViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi, currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
        itemsAdapter.clearItems();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void configureViewModel() {
        budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        budgetViewModel.moneyItemsList.observe(this, itemsAdapter::setData);

        budgetViewModel.isEditMode.observe(this, isEditMode -> {

            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onEditModeChanged(isEditMode);
            }
        });

        budgetViewModel.selectedCounter.observe(this, newCount -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof EditModeListener) {
                ((EditModeListener) parentFragment).onCounterChanged(newCount);
            }
        });


        budgetViewModel.messageString.observe(this, message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });

        budgetViewModel.messageInt.observe(this, message -> {
            if (message > 0) {
                showToast((getString(message)));
            }
        });

    }

    private void checkSelectedCount() {
        int selectedItemsCount = 0;
        for (ItemModel itemModel : itemsAdapter.getMoneyItemModelList()) {
            if (itemModel.isSelected()) {
                selectedItemsCount++;
            }
        }
        budgetViewModel.selectedCounter.postValue(selectedItemsCount);
    }

    @Override
    public void onClearEdit() {
        budgetViewModel.isEditMode.postValue(false);
        budgetViewModel.selectedCounter.postValue(-1);

        for (ItemModel itemModel : itemsAdapter.getMoneyItemModelList()) {
            if (itemModel.isSelected()) {
                itemModel.setSelected(false);
                itemsAdapter.updateItem(itemModel);
            }
        }
    }

    @Override
    public void onClearSelectedClick() {
        budgetViewModel.isEditMode.postValue(false);
        budgetViewModel.selectedCounter.postValue(-1);
        itemsAdapter.deleteSelectedItems();
    }



    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String nameAdd = data.getStringExtra("name");
        String priceAdd = data.getStringExtra("price");

        moneyItemModels.add(new ItemModel(nameAdd, priceAdd, currentPosition));
    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

}

