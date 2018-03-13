<%-- 
    Document   : song2Update
    Created on : 26-Aug-2017, 1:16:36 PM
    Author     : chaolee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    
    <script type="text/javascript" src="../jQuery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        // jQuery function to set focus on first input item
        $(function() {
            $('#seleYN').val("${song2.sele_tf}");
            $('#chorYN').val("${song2.chor}");
            $('#vodYN').val("${song2.vod_yn}");
            // $('form input[type="text"]').prop("readonly",false);
            // $('form input[type="date"]').prop("readonly",false);
            // $('form select').prop("disabled",false);
            $('form input:first').focus();
        });
    </script>

    <link rel="stylesheet" type="text/css" href="../song2Web/song2OneRecordStyle.css">

    <title>Update song</title>
</head>

<body>  
    <h1>Update one song</h1>
    <form action="../song2Controller/Song2UpdateServlet" method="post"> 
        <!-- jsp:include page="song2OneRecordForm.jsp"/ -->
        <%@ include file="song2OneRecordForm.jsp" %>
    </form>
</body>
</html>