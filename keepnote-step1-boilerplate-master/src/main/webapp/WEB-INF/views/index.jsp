
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
	<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>KeepNote</title>
</head>
<body>
	<!-- Create a form which will have text boxes for Note ID, title, content and status along with a Send 
		 button. Handle errors like empty fields -->
	 <form action="/saveNote" method="post">
	 	<input id="noteId">
	 	<input id="noteTitle">
	 	<input id="noteContent">
	 	<input id="noteStatus">
	 	<input type="submit">
	 </form>
	<!-- display all existing notes in a tabular structure with Id, Title,Content,Status, Created Date and Action -->
	<c:forEach items="${notes}" var="note">
		${note.noteId}		<a href="/delete/?id=${note.noteId}">Delete</a>
		${note.noteTitle}
		${note.noteContent}
		${note.noteStatus}
		${note.createdAt}
	</c:forEach>
</body>
</html>