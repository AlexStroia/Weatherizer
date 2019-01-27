package co.alexdev.weatherizer.utils;

public class Listener {

    public interface OnHistoryClickListener {
        void onItemPositionClick(String localityName);
    }

    public interface OnStartSearchListener {
        void onStartSearch(String localityName);
    }

    public interface OnLocationRequestFinishedListener {
        void onLocationRequestFinished();
    }
}
