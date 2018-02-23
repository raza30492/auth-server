package com.jazasoft.authserver;

public class Utility {

    public static String getAppHome() {
        String appHome = System.getenv("AUTH_SERVER_HOME");
        if (appHome == null) {
            throw new RuntimeException("AUTH_SERVER_HOME not found.");
        }
        return appHome;
    }
}
