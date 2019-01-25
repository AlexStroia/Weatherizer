package co.alexdev.weatherizer.utils;

import android.net.Uri;

import java.text.DecimalFormat;

import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import timber.log.Timber;

@WeatherizerAppScope
public final class StringUtils {

    public String convertFarenheitToCelsius(double kelvin) {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        return decimalFormat.format(kelvin - 273.15);
    }

    public String buildTempText(String temp, String tempType) {
        return new StringBuilder().append(tempType).append(" ").append(temp).toString();
    }

    public String formatDate(String date) {
        String[] dateValues = date.split(":");
        Timber.d("DateValues: " + dateValues[0] + dateValues[1]);
        return dateValues[0] + ":" + dateValues[1];
    }

    /*ICON URL UTILS: */
    public static String buildIconURL(String icon) {
        return new Uri.Builder().scheme(Constants.URL.URL_SCHEME)
                .authority(Constants.URL.BASE_IMAGE_URL_PATH)
                .appendEncodedPath(Constants.URL.IMAGE_PATH)
                .appendEncodedPath(buildIconFormat(icon, Constants.URL.IMAGE_FORMAT)).toString();
    }

    private static String buildIconFormat(String icon, String format) {
        return new StringBuilder().append(icon).append(format).toString();
    }
}
