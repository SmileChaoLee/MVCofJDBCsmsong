<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <title>Add songs</title>
</head>

<body>  
    <h1 style="margin: 0px 0px 0px 0px;">Add one song</h1>
    <form action="../song2Controller/Song2AddServlet" method="post"> 
        <!-- jsp:include page="song2OneRecordForm.jsp"/ -->
        <%@ include file="song2OneRecordForm.jsp" %>
    </form>
</body>
</html>