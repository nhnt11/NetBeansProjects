<%-- 
    Document   : page1
    Created on : Jan 28, 2009, 6:54:00 PM
    Author     : Nihanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page 1</title>
    </head>
    <body>
        <h3>Hello, <%=session.getAttribute("user") == null?"Guest":session.getAttribute("user")%></h3>
        <p>This is Page 1.</p>
        <a href="/JSPStuff/index.jsp">Back to index</a>
    </body>
</html>
