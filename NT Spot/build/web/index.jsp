<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="ntsite.css" />
<link rel="icon" type="image/png" href="images/favicon.png" />
<title>NT Spot</title>
</head>

<frameset rows="*" cols="*,825,*,0" frameborder="no" border="0" framespacing="0">
  <frame src="left_.html" scrolling="no" noresize="noresize" />
  <frameset rows="193,*" cols="800" frameborder="no" border="0" framespacing="0">
  	<frame src="banner_links.html" name="banner_links" scrolling="no" noresize="noresize" id="banner_links" title="banner_links" />
  	<frameset rows="*" cols="150,*,0" framespacing="0" frameborder="1" border="2" bordercolor="#666666">
    	<frame src="<%= request.getAttribute("t") == null ? "home_tabs.html" :  request.getAttribute("t") %>" name="tabs" scrolling="auto" noresize="noresize" id="tabs" title="tabs" />
        <frame src="<%= request.getAttribute("p") == null ? "home.html" :  request.getAttribute("p") %>" scrolling="auto" noresize="noresize" name="content" id="content" title="home"/>
        <frame src="about:blank" />
  	</frameset>
  </frameset>
  <frame src="right_.html" scrolling="no" noresize="noresize" />
  <frame src="right_.html" scrolling="no" noresize="noresize" />
<noframes>
<body>
</body>
</noframes>
</frameset>
</html>