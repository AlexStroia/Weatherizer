package co.alexdev.weatherizer.job;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import javax.inject.Inject;

import co.alexdev.weatherizer.scope.WeatherizerAppScope;
import timber.log.Timber;

@WeatherizerAppScope
public class WeatherReminderFirebaseJobService extends JobService {

    private ReminderWeatherTask mReminderWeatherTask;
    private static AsyncTask mBackgroundTask;

    public WeatherReminderFirebaseJobService() {
        super();
    }

    @Inject
    public WeatherReminderFirebaseJobService(ReminderWeatherTask reminderWeatherTask) {
        this.mReminderWeatherTask = reminderWeatherTask;
    }

    @Override
    public boolean onStartJob(JobParameters job) {

        Context context = WeatherReminderFirebaseJobService.this;
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                mReminderWeatherTask.executeTask(context);
                Timber.d("OnStartJob");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jobFinished((JobParameters) o, false);
                Timber.d("Job finished");
            }
        }.execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
