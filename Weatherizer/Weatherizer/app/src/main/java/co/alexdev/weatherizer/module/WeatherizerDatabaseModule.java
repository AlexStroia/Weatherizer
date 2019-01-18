package co.alexdev.weatherizer.module;

import android.content.Context;

import androidx.room.Room;
import co.alexdev.weatherizer.database.WeatherDatabase;
import co.alexdev.weatherizer.database.WeatherDatabaseDao;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class WeatherizerDatabaseModule {

    @WeatherizerAppScope
    @Provides
    public WeatherDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, WeatherDatabase.class,WeatherDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build();
    }

    @WeatherizerAppScope
    @Provides
    public WeatherDatabaseDao provideDatabaseDao(WeatherDatabase weatherDatabase) {
        return weatherDatabase.getWeatherDatabaseDao();
    }
}
