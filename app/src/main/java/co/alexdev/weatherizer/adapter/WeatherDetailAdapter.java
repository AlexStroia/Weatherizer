package co.alexdev.weatherizer.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.weatherizer.R;
import co.alexdev.weatherizer.databinding.WeatherDetailItemBinding;
import co.alexdev.weatherizer.model.weather.Main;
import co.alexdev.weatherizer.utils.StringUtils;
import timber.log.Timber;

public class WeatherDetailAdapter extends RecyclerView.Adapter<WeatherDetailAdapter.WeatherDetailViewHolder> {

    private List<Main> main;
    private static Resources mRes;
    public static StringUtils mStringUtils;

    public WeatherDetailAdapter(Resources resources, StringUtils stringUtils) {
        mRes = resources;
        mStringUtils = stringUtils;
    }

    @NonNull
    @Override
    public WeatherDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WeatherDetailItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.weather_detail_item, parent, false);

        return new WeatherDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDetailViewHolder holder, int position) {
        holder.bind(main.get(position));
    }

    @Override
    public int getItemCount() {
        if (main == null) return 0;
        return main.size();
    }

    public void setMain(List<Main> main) {
        this.main = main;
        notifyDataSetChanged();
    }

    static class WeatherDetailViewHolder extends RecyclerView.ViewHolder {

        WeatherDetailItemBinding mBinding;

        public WeatherDetailViewHolder(WeatherDetailItemBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(Main main) {
            mBinding.tvCityName.setText(main.getName());
            String tempMin = mRes.getString(R.string.temp_min);
            String tempMax = mRes.getString(R.string.temp_max);
            final String temp_min = mStringUtils.convertFarenheitToCelsius(main.getTemp_min());
            final String temp_max = mStringUtils.convertFarenheitToCelsius(main.getTemp_max());
            mBinding.tvTempMin.setText(mStringUtils.buildTempText(temp_min, tempMin));
            mBinding.tvTempMax.setText(mStringUtils.buildTempText(temp_max, tempMax));
            mBinding.tvDateHour.setText(main.getDate_txt());
            Picasso.get().load(main.getIcon_id()).into(mBinding.ivWeather);
            Timber.d("Main icon url" + main.getIcon_id());
        }
    }
}
