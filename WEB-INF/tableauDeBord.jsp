<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tableau de Bord</title>
<style>
	<%@ include file="/WEB-INF/css/style.css" %>
</style>
</head>
<jsp:include page="/WEB-INF/header.jsp" />
<body>
	<div id="main-panel">
		<h1>Tableau de Bord</h1>
		<p>Ici, on va avoir tout les components afficher de chacun des contrôle qu'on veux bien afficher
		exemple, 
		afficherJuges
		afficherAvocats
		afficherJurys
		afficherParties
		afficherProces 1
		afficherJurys
		</p>
	</div>
	<jsp:include page="/WEB-INF/footer.jsp" />
</body>

</html>