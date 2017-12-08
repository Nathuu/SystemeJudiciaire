<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tableau de Bord</title>
<style>
	<%@ include file="/WEB-INF/css/style.css" %>
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/header.jsp" />
	<jsp:include page="/WEB-INF/messageErreur.jsp" />
	<div id="main-panel">
		<div class="container-inline">			
			<div	>
			<h1>Tableau de Bord</h1>
				<form action="TableauDeBord" method="get">
					<select name="IDproces">
						<option value="${selectedProces}" selected>${selectedProces}</option>
						<c:forEach items="${lstProces}" var="var">
							<c:if test="${var.id != selectedProces}">
								<option value="${var.id}">${var.id}</option>
							</c:if>
						</c:forEach>
					</select>
					<div>
						<input class="btn_black" type="submit" value="Afficher Procès"
							name="afficherProces" />
					</div>
					<div>
						<jsp:include page="/WEB-INF/Components/lstProces.jsp" />
					</div>
					<div>
						<jsp:include page="/WEB-INF/Components/lstSeance.jsp" />
					</div>
					<div>
						<jsp:include page="/WEB-INF/Components/lstJury.jsp" />
					</div>
					<div>
						<jsp:include page="/WEB-INF/Components/lstPartie_tb.jsp" />
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/footer.jsp" />
	</div>

</body>

</html>