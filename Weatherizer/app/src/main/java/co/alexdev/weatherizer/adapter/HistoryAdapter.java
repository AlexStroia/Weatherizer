package co.alexdev.weatherizer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.databinding.HistoryLayoutItemBinding;
import co.alexdev.weatherizer.model.weather.History;
import co.alexdev.weatherizer.utils.Listener;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private static List<History> history;
    private static Listener.OnHistoryClickListener mListener;

    public HistoryAdapter(Listener.OnHistoryClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        HistoryLayoutItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.history_layout_item, parent, false);
        return new HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(history.get(position));
    }

    @Override
    public int getItemCount() {
        if (history == null) {
            return 0;
        }
        return history.size();
    }

    public void setList(List<History> history) {
        this.history = history;
        notifyDataSetChanged();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

        private HistoryLayoutItemBinding mBinding;

        public HistoryViewHolder(HistoryLayoutItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(History history) {
            mBinding.tvHistoryCity.setText(history.getCity());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mListener.onItemPositionClick(history.get(position).getCity());
        }
    }
}
