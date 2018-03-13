<%@ page import="java.util.UUID" %>
<%@ page import="com.smile.model.RequestAttributesForSong2" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%
            // initialize a object of RequestAttributesForSong2
            RequestAttributesForSong2 song2Attr = new RequestAttributesForSong2();
            String song2AttributesObject = UUID.randomUUID().toString();	// make it unique
            request.getSession().setAttribute(song2AttributesObject, song2Attr);
            request.setAttribute("song2AttributesObject", song2AttributesObject);
        %>
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/jquery-1.11.3.min.js"></script>
        <script type="text/javascript">
            // Using jQuery.
            $(document).ready( function() {
                $('form').submit();
            });
        </script>
        <title>songs</title>
</head>

<body>

    <form action="<%=request.getContextPath()%>/song2Controller/Song2ListServlet" method="get">
        <input type="hidden" name="song2AttributesObject" value="${song2AttributesObject}" />
    </form>

</body>
</html>