<%-- 
    Document   : helloworld
    Created on : Jan 26, 2009, 5:41:40 PM
    Author     : Nihanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <%!
        String encode(String s) {
            char c[] = s.toCharArray();
            String ss = "";
            for (int i = 0, n = 1; i < s.length(); i++, n++) {
                if (i % 2 == 0) {
                    c[i] = (char) ((int) c[i] + n);
                } else {
                    c[i] = (char) ((int) c[i] - n);
                }
                ss += String.valueOf(c[i]);
                if (n == 15) {
                    n = 0;
                }
            }
            return ss.toUpperCase();
        }
        String decode(String s) {
            char c[] = s.toCharArray();
            String ss = "";
            for (int i = 0, n = 1; i < s.length(); i++, n++) {
                if (i % 2 == 0) {
                    c[i] = (char) ((int) c[i] - n);
                } else {
                    c[i] = (char) ((int) c[i] + n);
                }
                ss += String.valueOf(c[i]);
                if (n == 15) {
                    n = 0;
                }
            }
            return ss.toLowerCase();
        }%>
        <%
        String userpass[][] = {
            {"Nihanth", "ntrulz"},
            {"Rama", "nichin"},
            {"SGB", "chinih"},
            {"Chinpin", "wintersnowflake"}
        };
        boolean userb = true;
        boolean passb = true;
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String msg = "Hi dude(tte)!";
        if (user != null && pass != null && !(user.equals("") && pass.equals(""))) {
            for (int i = 0; i < userpass.length; i++) {
                if (user.equals(userpass[i][0]) || decode(user).equals(userpass[i][0])) {
                    userb = false;
                    if (pass.equals(userpass[i][0]) || decode(pass).equals(userpass[i][0])) {
                        msg = "<p>Welcome, <strong>" + userpass[i][0] + "</strong>!</p>";
                        session.setAttribute("user", user);
                        Cookie cookie = new Cookie("user", "");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        if (request.getParameter("remember") != null) {
                            cookie = new Cookie("user", encode(user) + "," + encode(pass));
                            cookie.setMaxAge(10 * 365 * 24 * 60 * 60);
                            response.addCookie(cookie);
                        }
                        passb = false;
                    }
                    break;
                }
            }
        } if (userb) {
            msg = "<p>Sorry, but who are you?</p>";
        } else if (passb) {
            msg = "<p>Sorry, <strong>" + user + "</strong>, but you gave the wrong password!</p>";
        }
        msg += "<p>It is now <strong>" + new java.util.Date() + "</strong>.</p>" ;
        out.println (msg);
        %>
        <a href="/JSPStuff/index.jsp">Back to index</a>
    </body>
</html>
