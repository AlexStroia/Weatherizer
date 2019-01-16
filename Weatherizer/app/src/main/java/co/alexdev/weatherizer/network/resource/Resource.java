package co.alexdev.weatherizer.network.resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import co.alexdev.weatherizer.utils.Constants;

public class Resource<T> {

    @NonNull
    @Constants.NetworkingStatus.NetworkStatus
    public final int status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public Resource(@NonNull int status,  @Nullable T data, @Nullable String message) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> Resource <T> success(@Nullable T data) {
        return new Resource<>(Constants.NetworkingStatus.RESPONSE_SUCCESS, data,null);
    }

    public static <T> Resource <T> error(String message, @Nullable T data) {
        return new Resource<>(Constants.NetworkingStatus.RESPONSE_ERROR, data,message);
    }

    public static <T> Resource<T> loading (@Nullable T data) {
        return new Resource<>(Constants.NetworkingStatus.RESPONSE_LOADING, data,null);
    }
}
