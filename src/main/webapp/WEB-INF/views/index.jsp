<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Accueil</title>
</head>
<body>
	<h1>Bienvenue sur cet example d'application n-tiers</h1>
	<h2>Menu :</h2>
	<ul>
		<li>
			<a href="<c:url value="/show-all.html" />">Liste des clients</a>
		</li>
	</ul>

</body>
</html>