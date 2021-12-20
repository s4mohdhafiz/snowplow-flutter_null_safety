
package com.wogaaflutter.snowplow;

import com.snowplowanalytics.snowplow.configuration.*;
import com.snowplowanalytics.snowplow.controller.*;
import com.snowplowanalytics.snowplow.*;
import com.snowplowanalytics.snowplow.network.HttpMethod;
import com.snowplowanalytics.snowplow.tracker.LogLevel;
import com.snowplowanalytics.snowplow.util.TimeMeasure;

import android.content.Context;

import java.util.concurrent.TimeUnit;

public class SnowplowTrackerBuilder {
    public static final String HTTPS_SNOWPLOW_COLLECTOR_URL_COM = "https://snowplow.dcube.cloud";

    public static TrackerController getTracker(Context context) {
        String appId = context.getPackageName();
            
        NetworkConfiguration networkConfig = new NetworkConfiguration(HTTPS_SNOWPLOW_COLLECTOR_URL_COM, HttpMethod.POST);
        TrackerConfiguration trackerConfig = new TrackerConfiguration(appId)
                .base64encoding(true)
                .sessionContext(true)
                .platformContext(true)
                .lifecycleAutotracking(true)
                .screenViewAutotracking(true)
                .screenContext(true)
                .applicationContext(true)
                .exceptionAutotracking(true)
                .installAutotracking(true);

        SessionConfiguration sessionConfig = new SessionConfiguration(
                new TimeMeasure(30, TimeUnit.SECONDS),
                new TimeMeasure(30, TimeUnit.SECONDS)
        );

        SubjectConfiguration subjectConfiguration = new SubjectConfiguration().useragent(System.getProperty("http.agent"));

        return Snowplow.createTracker(context,
                "WogaaTracker",
                networkConfig,
                trackerConfig,
                sessionConfig,
                subjectConfiguration
        );

    }
}
