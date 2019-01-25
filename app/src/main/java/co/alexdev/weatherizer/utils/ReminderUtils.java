package co.alexdev.weatherizer.utils;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import co.alexdev.weatherizer.job.WeatherReminderFirebaseJobService;
import co.alexdev.weatherizer.scope.WeatherizerAppScope;

/*Used to schedule the job to start the service */
@WeatherizerAppScope
public class ReminderUtils {

    private Context mContext;
    public static final String REMINDER_TAG = "weather_tag";
    private static int INTERVAL_MINUTES = 1;
    private static int INTERVAL_MINUTES_IN_SECONDS = (int) TimeUnit.MINUTES.toSeconds(INTERVAL_MINUTES);
    private static final int SYNC_FLEXTIME_SECONDS = INTERVAL_MINUTES_IN_SECONDS;// WAIT 10 minutes and after schedule the job again

    private static boolean sInitialized;

    @Inject
    public ReminderUtils(Context mContext) {
        this.mContext = mContext;
    }

    /*Schedule a firebasejobdispatcher job using the jobservice created*/
    synchronized public void scheduleTheJob() {
        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job job = dispatcher.newJobBuilder().
                setService(WeatherReminderFirebaseJobService.class)
                .setLifetime(Lifetime.FOREVER)
                .setReplaceCurrent(true)
                .setTag(REMINDER_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(INTERVAL_MINUTES_IN_SECONDS, SYNC_FLEXTIME_SECONDS + INTERVAL_MINUTES_IN_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(job);
        sInitialized = true;
    }
}
