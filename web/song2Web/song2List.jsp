<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
 
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        function toSubmitGeneratedForm(url, songno) {
            // If there is no form in body, then use this submit function
            var $form = $('<form action="'+url+'" method="get"></form>');
            if (songno !== undefined) {
                $form.append('<input type="hidden" name="song_no" value="'+songno+'" />');
            }
            $form.append('<input type="hidden" name="song2AttributesObject" value="${song2AttributesObject}" />');
            $form.appendTo('body').submit();
        }

        function toSubmitBodyForm(form, url, songno) {
            // If there is form in body, then use this submit function
            if (url !== undefined) {
                $(form).attr("action", url);
                if (songno !== undefined) {
                    $(form).append('<input type="hidden" name="song_no" value="'+songno+'" />');
                }
                $(form).append('<input type="hidden" name="song2AttributesObject" value="${song2AttributesObject}" />');
                $(form).submit();
            }
        }

        // use jQuery
        function toSubmit(url0, songno) {
            var url = "<%=request.getContextPath()%>/song2Controller/" + url0;
            var form = "#bodyForm";
            if ( $(form).length ) {
                // if form with id="bodyForm" exist. jQuery the length is greater than 0.
                toSubmitBodyForm(form, url, songno);
            } else {
                // alert("bodyForm does not exist");
                toSubmitGeneratedForm(url, songno);
            }
        }

        // use JavaScript
        function toSubmitJavaScript(url0, songno) {
            var url = "<%=request.getContextPath()%>/song2Controller/" + url0;
            var form = document.getElementById("bodyForm");
            if (form !== null ) {
                // if form with id="bodyForm" exist.
                toSubmitGeneratedForm(url, songno);
            } else {
                toSubmitBodyForm(url, songno);
            }
        }
        // Using jQuery to load a page.
        $(function() {
            // load logout page
            $('#logoutJsp').load("<%=request.getContextPath()%>/logout.jsp");
        });		
    </script>

    <style>
        body {
            background-color:#d0e4fe;
        }
        h1 {
            color: green;
            font-size: 20px;
            text-align:center;
        }
        p {
            font-family:"New Roman";
            font-size:12px;
            text-align:left;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid black;
        }
        th {
            font-family:"New Roman";
            font-size:12px;
            text-align:center;
            border: 1px solid black;
        }
        td {
            font-family:"New Roman";
            font-size:12px;
            text-align:left;
            border: 1px solid black;
        }
        button {
            font-family: "New Roman";
            color:DarkRed;
            background-color:lightgrey;
            height:28px; font-size:20px;
            text-align:center;
        }
        button.table {
            font-family: "New Roman";
            color:yellow;
            background-color:green;
            height:18px;font-size:12px;
            text-align:center;
        }
    </style>

    <title>Songs Management</title>
</head>

<body>    
 
<div id="logoutJsp" style="text-align:right;"></div>
<h1 style="margin: 0px 0px 0px 0px;">Songs Management</h1>

<form id="bodyForm" action="" method="get" >			
    <table style="margin:auto" border=1>
        <thead>
            <tr>
                <th>song_no</th>            
                <th>song_na</th>
                <th>lang_na</th>
                <th>s_num_word</th>
                <th>num_fw</th>
                <th>num_pw</th>
                <th>sing_na1</th>
                <th>sing_na2</th>
                <th>sele_tf</th>
                <th>chor</th>
                <th>n_mpeg</th>
                <th>m_mpeg</th>
                <th>vod_yn</th>
                <th>vod_no</th>
                <th>pathname</th>
                <th>in_date</th>
                <th>
                <th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${songs}" var="song2">
                <tr>
                    <td>${song2.song_no}</td>                
                    <td>${song2.song_na}</td>
                    <td>${song2.lang_na}</td>
                    <td>${song2.s_num_word}</td>
                    <td>${song2.num_fw}</td>
                    <td>${song2.num_pw}</td>
                    <td>${song2.sing_na1}</td>
                    <td>${song2.sing_na2}</td>
                    <td>${song2.sele_tf}</td>
                    <td>${song2.chor}</td>
                    <td>${song2.n_mpeg}</td>                    
                    <td>${song2.m_mpeg}</td>
                    <td>${song2.vod_yn}</td>
                    <td>${song2.vod_no}</td>
                    <td>${song2.pathname}</td>
                    <td>${song2.in_date}</td>
                    
                    <td>
                        <button class="table" onclick="toSubmit('Song2UpdateServlet','${song2.song_no}')">UPDATE</button>
                    </td>
                    <td>
                        <button class="table" onclick="toSubmit('Song2DeleteServlet','${song2.song_no}')">DELETE</button>
                    </td>    
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p style="text-align:center; font-family:'New Roman'; font-size:16px; margin:12px 0px 0px 0px">
        <button onclick="toSubmit('Song2AddServlet')">Add Songs</button>&nbsp;
        <button onclick="toSubmit('Song2FindServlet')">Find Songs</button>&nbsp;  
        <button onclick="toSubmit('Song2PrintServlet')">Print</button>&nbsp;  
        <button onclick="toSubmit('Song2FirstPageServlet')">First Page</button>&nbsp;
        <button onclick="toSubmit('Song2LastPageServlet')">Last Page</button>&nbsp; 
        <button onclick="toSubmit('Song2PreviousPageServlet')">Previous Page</button>&nbsp;
        <button onclick="toSubmit('Song2NextPageServlet')">Next Page</button>&nbsp;
        <button style="color:red;" onclick="toSubmit('Song2QuitServlet')">QUIT</button>
    </p>
    <p style="text-align:center; margin:10px 0px 0px 0px">
    	<b style="color:brown; font-size:20px;">Order By:</b>
        <button onclick="toSubmit('Song2OrderBySongNoServlet.html')">Song No</button>&nbsp;
        <button onclick="toSubmit('Song2OrderBySongNameServlet.html')">Song Name</button>&nbsp;  
        <button onclick="toSubmit('Song2OrderByFirstSingerNameServlet.html')">Singer1 Name</button>&nbsp;  
        <button onclick="toSubmit('Song2OrderBySecondSingerNameServlet.html')">Singer2 Name</button>&nbsp;
        <button onclick="toSubmit('Song2OrderByVodNoServlet.html')">VOD No</button>&nbsp; 
        <button onclick="toSubmit('Song2OrderByLanguageSongNameServlet.html')">Lang+Song Name</button>&nbsp;
        <button onclick="toSubmit('Song2OrderByLanguageNoWordSongNameServlet.html')">Lang+No.Wds+Song Name </button>&nbsp;
        <button onclick="toSubmit('Song2OrderByLanguageNoWordSongNoServlet.html')">Lang+No.Wds+Song No</button>
    </p>
</form>

</body>
</html>