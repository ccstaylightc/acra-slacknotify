package com.cclight.acraslacknotifylib;

import android.content.Context;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.config.ACRAConfig;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;
import org.acra.sender.ReportSenderFactory;

import java.util.EnumMap;

/**
 * @author claire
 */
public class SlackReportSender implements ReportSender, ReportSenderFactory {



    private static class Builder {

        Context mContext;
        private CrashReportData mErrorContent;
        private EnumMap<ReportField, String> mReportMap;

        public Builder(Context context, CrashReportData errorContent) {
            mContext = context;
            mErrorContent = errorContent;
            mReportMap = new CrashReportData();
        }

        public Builder addAndroidVersionReportField() {
            mReportMap.put(ReportField.ANDROID_VERSION, mErrorContent.get(ReportField.ANDROID_VERSION));
            return this;
        }

        public Builder addAppVersionCodeReportField() {
            mReportMap.put(ReportField.APP_VERSION_CODE, mErrorContent.get(ReportField.APP_VERSION_CODE));
            return this;
        }

        public Builder addAppVersionNameReportField() {
            mReportMap.put(ReportField.APP_VERSION_NAME, mErrorContent.get(ReportField.APP_VERSION_NAME));
            return this;
        }

        public Builder addAvailableMemoryReportField() {
            mReportMap.put(ReportField.AVAILABLE_MEM_SIZE, mErrorContent.get(ReportField.AVAILABLE_MEM_SIZE));
            return this;
        }

        public Builder addBrandReportField() {
            mReportMap.put(ReportField.BRAND, mErrorContent.get(ReportField.BRAND));
            return this;
        }

        public Builder addDisplayReportField() {
            mReportMap.put(ReportField.DISPLAY, mErrorContent.get(ReportField.DISPLAY));
            return this;
        }

        public Builder addPackageNameField() {
            mReportMap.put(ReportField.PACKAGE_NAME, mErrorContent.get(ReportField.PACKAGE_NAME));
            return this;
        }

        public Builder addPhoneModelReportField() {
            mReportMap.put(ReportField.PHONE_MODEL, mErrorContent.get(ReportField.PHONE_MODEL));
            return this;
        }

        public Builder addProductReportField() {
            mReportMap.put(ReportField.PRODUCT, mErrorContent.getProperty(ReportField.PRODUCT));
            return this;
        }

        public Builder addReportIdReportField() {
            mReportMap.put(ReportField.REPORT_ID, mErrorContent.getProperty(ReportField.REPORT_ID));
            return this;
        }

        public Builder addStackTraceReportField() {
            mReportMap.put(ReportField.STACK_TRACE, mErrorContent.get(ReportField.STACK_TRACE));
            return this;
        }

        public Builder commit() {

            if (this.mReportMap.size() == 0) {
                throw new IllegalStateException("ReportField required.");
            }

            SlackClient.sendSlackMessage(mContext.getString(R.string.app_name), mReportMap);

            return this;
        }
    }

    @Override
    public ReportSender create(Context context, ACRAConfig config) {
        return this;
    }

    @Override
    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {

        new Builder(context, errorContent)
                .addAndroidVersionReportField()
                .addAppVersionCodeReportField()
                .addAppVersionNameReportField()
                .addAvailableMemoryReportField()
                .addBrandReportField()
                .addPhoneModelReportField()
                .addStackTraceReportField()
                .commit();

    }
}
