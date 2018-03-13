<%@ page import="com.smile.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
 
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<%
		RequestAttributesForSong2 song2Attr = (RequestAttributesForSong2)request.getAttribute("song2Attributes");
		if (song2Attr == null)
		{
			System.out.println("song2list.jsp -> song2Attributes object is null.");
		}
		else
		{
			System.out.println("song2list.jsp -> song2Attributes object is not null.");
			System.out.println("song2list.jsp -> actFunction = " + song2Attr.getActFunction());
			System.out.println("song2list.jsp -> accessMethod = " + song2Attr.getAccessMethod());
		}
		request.setAttribute("song2Attributes", song2Attr);
	%>
	
	<script type="text/javascript" src="jQuery/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
		function toSubmit(type,songno){
		   $('#listForm').append("<input type='hidden' name='actFunction' value='"+type+ "'>");
		   if (songno !== undefined) {
		   	   $('#listForm').append("<input type='hidden' name='song_no' value='"+songno+ "'>");
		   }
		   $('#listForm').submit();
		}
		
		// Using jQuery.
		$(function(){
			// load logout page
			$('#logoutJsp').load("logout.jsp")
		});		
	</script>
		
	<style>
		body
		{
			background-color:#d0e4fe;
		}
		h1
		{
			color: green;
			font-size: 20px;
			text-align:center;
		}
		p
		{
			font-family:"New Roman";
			font-size:12px;
			text-align:left;
		}
		table
		{
			width: 100%;
		    border-collapse: collapse;
		   	border: 1px solid black;
		}
		th,td
		{
			font-family:"New Roman";
			font-size:12px;
			text-align:center;
			border: 1px solid black;
		}
		td
		{
			font-family:"New Roman";
			font-size:12px;
			text-align:left;
			border: 1px solid black;
		}
		button
		{
			font-family: "New Roman";
			color:DarkRed;
			background-color:lightgrey;
			height:28px; font-size:20px;
			text-align:center;
		}
		button.table
		{
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
	
<form id="listForm" action="Song2Servlet.html" method="get" >	
    <input type="hidden" name="accessMethod" 	value="${accessMethod}" readonly >
	<input type="hidden" name="orgSong_no"   	value="${orgSong_no}" readonly >
	<input type="hidden" name="queryCondition"	value="${queryCondition}" readonly >
	<input type="hidden" name="byOrder"   		value="${byOrder}" readonly >
	<input type="hidden" name="pageNo"   		value="${pageNo}" readonly >  
		
	<table style="margin:auto" border=1>
        <thead>
            <tr>
                <th>song_no</th>            
                <th>song_na</th>
                <!-- <th>lang_no</th> -->
                <th>lang_na</th>
                <th>s_num_word</th>
                <th>num_fw</th>
                <th>num_pw</th>
                <!-- <th>sing_no1</th> -->
                <th>sing_na1</th>
                <!-- <th>sing_no2</th> -->
                <th>sing_na2</th>
                <th>sele_tf</th>
                <th>chor</th>
                <th>n_mpeg</th>
                <th>m_mpeg</th>
                <th>vod_yn</th>
                <th>vod_no</th>
                <th>pathname</th>
				<th>in_date</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach items="${songs}" var="song2">
                <tr>
                    <td>${song2.song_no}</td>                
                    <td>${song2.song_na}</td>
                    <!--  <td>${song2.lang_no}</td> -->
                    <td>${song2.lang_na}</td>
                    <td>${song2.s_num_word}</td>
                    <td>${song2.num_fw}</td>
                    <td>${song2.num_pw}</td>
                    <!-- <td>${song2.sing_no1}</td> -->
                    <td>${song2.sing_na1}</td>
                    <!-- <td>${song2.sing_no2}</td> -->
                    <td>${song2.sing_na2}</td>
                    <td>${song2.sele_tf}</td>
                    <td>${song2.chor}</td>
                    <td>${song2.n_mpeg}</td>                    
                    <td>${song2.m_mpeg}</td>
                    <td>${song2.vod_yn}</td>
                    <td>${song2.vod_no}</td>
                    <td>${song2.pathname}</td>
                    <td>${song2.in_date}</td>
                    
                    <td><button class="table" onclick="toSubmit('UPDATE','${song2.song_no}')">UPDATE</button></td>
                    <td><button class="table" onclick="toSubmit('DELETE','${song2.song_no}')">DELETE</button></td>
                    
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p style="text-align:center; font-family:'New Roman'; font-size:16px; margin:12px 0px 0px 0px">
	    <button onclick="toSubmit('ADD')">Add Songs</button>&nbsp;
	    <button onclick="toSubmit('FIND')">Find Songs</button>&nbsp;  
	    <button onclick="toSubmit('PRINT')">Print</button>&nbsp;  
	    <button onclick="toSubmit('FIRSTPAGE')">First Page</button>&nbsp;
	    <button onclick="toSubmit('LASTPAGE')">Last Page</button>&nbsp; 
	    <button onclick="toSubmit('PREVPAGE')">Previous Page</button>&nbsp;
	    <button onclick="toSubmit('NEXTPAGE')">Next Page</button>&nbsp;
	    <button onclick="toSubmit('QUIT')">QUIT</button>
    </p>
    <p style="text-align:center; margin:10px 0px 0px 0px">
    	<b style="color:brown; font-size:20px;">Order By:</b>
   	    <button onclick="toSubmit('ADD')">Song No</button>&nbsp;
	    <button onclick="toSubmit('FIND')">Song Name</button>&nbsp;  
	    <button onclick="toSubmit('PRINT')">Singer1 Name</button>&nbsp;  
	    <button onclick="toSubmit('FIRSTPAGE')">Singer2 Name</button>&nbsp;
	    <button onclick="toSubmit('LASTPAGE')">VOD No</button>&nbsp; 
	    <button onclick="toSubmit('PREVPAGE')">Lang+Song Name</button>&nbsp;
	    <button onclick="toSubmit('NEXTPAGE')">Lang+No.Wds+Song Name </button>&nbsp;
	    <button onclick="toSubmit('QUIT')">Lang+No.Wds+Song No</button>
    </p>
</form>
</body>
</html>
