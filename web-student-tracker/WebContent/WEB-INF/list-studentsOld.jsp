<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.proyecto.web.jdbc.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student Tracker App</title>

<link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<%
	
	// get the students from the request object (sent by servlet)
	
	List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");
	
%>

<body>
<div id="wrapper">
	<div id="header">
		<h2>FooBar University</h2>
	</div>
</div>

<div id="container">
	<div id= content">
		<table>
		
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
			</tr>
			<% for (Student tempStudent : theStudents) {%>
			
				<tr>
					<td> <%= tempStudent.getFirstName() %> </td>
					<td> <%= tempStudent.getLastName() %> </td>
					<td> <%= tempStudent.getEmail() %> </td>
				</tr>
			<% } %>
			
		</table>
	</div>

</div>

</body>
</html>