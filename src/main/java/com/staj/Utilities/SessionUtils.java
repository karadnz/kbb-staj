package com.staj.Utilities;

import javax.servlet.http.HttpSession;

public class SessionUtils
{

    public static boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("token") != null;
    }

    public static void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}
