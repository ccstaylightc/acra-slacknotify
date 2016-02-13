package com.cclight.arca.slacknotify;

import android.app.Application;

import com.cclight.acraslacknotifylib.SlackClient;
import com.cclight.acraslacknotifylib.SlackReportSender;

import org.acra.annotation.ReportsCrashes;

/**
 * @author claire
 */
@ReportsCrashes(reportSenderFactoryClasses = { SlackReportSender.class })
public class MyApp extends Application {

    @Override
    public void onCreate() {

        SlackClient.init(this);

        super.onCreate();
    }
}
