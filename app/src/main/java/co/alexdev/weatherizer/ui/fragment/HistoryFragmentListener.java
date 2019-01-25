package co.alexdev.weatherizer.ui.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.adapter.HistoryAdapter;
import co.alexdev.weatherizer.databinding.FragmentHistoryBinding;
import co.alexdev.weatherizer.repo.AppRepository;
import co.alexdev.weatherizer.utils.Listener;
import co.alexdev.weatherizer.viewmodel.SharedViewModel;
import co.alexdev.weatherizer.viewmodel.factory.ViewModelFactory;
import timber.log.Timber;

public class HistoryFragmentListener extends Fragment implements Listener.OnHistoryClickListener {

    private FragmentHistoryBinding mBinding;
    private HistoryAdapter adapter;
    private SharedViewModel vm;
    private Listener.OnStartSearchListener mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (Listener.OnStartSearchListener) getActivity();
        } catch (ClassCastException e) {
            Timber.d(e.getMessage());
        }
    }

    @Inject
    AppRepository mRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_history, container, false);
        ViewModelFactory factory = new ViewModelFactory(mRepository);
        vm = ViewModelProviders.of(this.getActivity(), factory).get(SharedViewModel.class);

        initRecycler();

        setHasOptionsMenu(true);
        return mBinding.getRoot();
    }

    private void initRecycler() {
        adapter = new HistoryAdapter(this);
        mBinding.rvHistory.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        DividerItemDecoration decoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.HORIZONTAL);
        mBinding.rvHistory.addItemDecoration(decoration);
        mBinding.rvHistory.setAdapter(adapter);

        vm.getHistoryList().observe(this.getActivity(), histories -> {
            adapter.setList(histories);
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        menuItem.setVisible(false);
    }

    @Override
    public void onItemPositionClick(String localityName) {
        mListener.onStartSearch(localityName);
    }
}
