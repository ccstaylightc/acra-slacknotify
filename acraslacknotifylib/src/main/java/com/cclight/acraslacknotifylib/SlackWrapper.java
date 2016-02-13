package com.cclight.acraslacknotifylib;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author claire
 */
public class SlackWrapper {


    String username;

    String text;

    @SerializedName("icon_emoji")
    String icon;

    List<SlackAttachment> attachments;


    public static class Builder {

        SlackWrapper instance;

        String username;

        String text;

        @SerializedName("icon_emoji")
        String icon;

        StringBuilder reportFields;

        List<SlackAttachment> attachments;

        public Builder() {
            instance = new SlackWrapper();
            this.reportFields = new StringBuilder();
            this.attachments = new ArrayList<>();
        }

        public Builder setUserName(String userName) {
            this.username = userName;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setReportFieldText() {
            this.text = reportFields.toString();
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder addAttachment(String fallback, String color, String title, String value) {
            this.attachments.add(new SlackAttachment(fallback, color, title, value));
            return this;
        }

        public Builder build() {

            if (TextUtils.isEmpty(this.username)) {
                throw new IllegalStateException("User name required.");
            }

            instance.username = this.username;
            instance.text = this.text;
            if (!TextUtils.isEmpty(this.icon)) instance.icon = this.icon;
            instance.attachments = this.attachments;

            return this;
        }

    }

    private static class SlackAttachment {

        String fallback;

        String color;

        ArrayList<SlackField> fields;

        private SlackAttachment(String fallback, String color, String title, String value) {

            this.fallback = fallback;
            this.color = color;
            this.fields = new ArrayList<>();
            this.fields.add(new SlackField(title, value));

        }

    }

    private static class SlackField {

        String title;

        String value;

        private SlackField(String title, String value) {

            this.title = title;
            this.value = value;

        }
    }

}
