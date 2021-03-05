package com.example.edutab;

import android.content.Context;
import android.content.SharedPreferences;

public class prefmanager {

    private static String KEY_PRIMARY_LOCALE = "language";
    private static String KEY_PRIMARY_SUBJECT = "subject";
    private static String KEY_PRIMARY_CHAPTERS = "chapter";
    private static String KEY_PRIMARY_ACTION = "action";
    Context context;

    public prefmanager(Context context) {
        this.context = context;
    }

    public  String getKeyPrimaryLocale() {
        return KEY_PRIMARY_LOCALE;
    }

    public  void setKeyPrimaryLocale(String keyPrimaryLocale) {
        KEY_PRIMARY_LOCALE = keyPrimaryLocale;
    }

    public  String getKeyPrimarySubject() {
        return KEY_PRIMARY_SUBJECT;
    }

    public  void setKeyPrimarySubject(String keyPrimarySubject) {
        KEY_PRIMARY_SUBJECT = keyPrimarySubject;
    }

    public  String getKeyPrimaryChapters() {
        return KEY_PRIMARY_CHAPTERS;
    }

    public  void setKeyPrimaryChapters(String keyPrimaryChapters) {
        KEY_PRIMARY_CHAPTERS = keyPrimaryChapters;
    }

    public  String getKeyPrimaryAction() {
        return KEY_PRIMARY_ACTION;
    }

    public  void setKeyPrimaryAction(String keyPrimaryAction) {
        KEY_PRIMARY_ACTION = keyPrimaryAction;
    }
}
