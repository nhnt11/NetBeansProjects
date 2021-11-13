<%-- 
    Document   : index
    Created on : Jan 28, 2009, 6:33:56 PM
    Author     : Nihanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
    </head>
    <body>
        <%!
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
        }
        %>
        <%
        String user = "";
        String pass = "";
        boolean b = false;
        Cookie cookies[] = request.getCookies();
        if (cookies != null && cookies.length > 0 && cookies[0].getName().equals("user")) {
            java.util.StringTokenizer tokens = new java.util.StringTokenizer(cookies[0].getValue(), ",");
            if (tokens.hasMoreTokens())
                user = decode(tokens.nextToken());
            if (tokens.hasMoreTokens())
                pass = decode(tokens.nextToken());
            b = true;
        }
        if (request.getParameter("logout") != null) {
            session.removeAttribute("user");
            Cookie cookie = new Cookie("user", "");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        if (session.getAttribute("user") == null) {%>
        <form action="login.jsp" method="post">
            <h3>Please login!</h3>
            <table border="0">
            <tr><td style="text-align:right">Username:<label></label></td><td><input type="text" name="user" value="<%=user%>" /></td></tr>
            <tr><td style="text-align:right">Password:<label></label></td><td><input type="password" name="pass" value="<%=pass%>" /></td></tr>
            <tr><td>&nbsp;</td><td><input type="checkbox" value="Remember Me" checked="true" name="remember">Remember Me</input></td></tr>
            <tr><td>&nbsp;</td><td><input type="submit" value="OK" /></td></tr>
            </table>
        </form>
        <%} else {%>
            <h3>Welcome, <span style="color:red"><%= session.getAttribute("user") %></span>[<small><a href="/JSPStuff/index.jsp?logout=1">Logout</a>]</small></h3>
        <%}%>
        <ul>
            <lh><strong>Page List</strong></lh>
            <li><a href="/JSPStuff/page1.jsp">Page 1</a></li>
            <li><a href="/JSPStuff/page2.jsp">Page 2</a></li>
            <li><a href="/JSPStuff/page3.jsp">Page 3</a></li>
        </ul>
    </body>
</html>
