
<%@ page import="java.util.*,java.text.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %> 

<div class="panel">
	<table id="lstSeance">
		<caption>Seances du proces</caption>
		<tr>
			<th>ID seance</th>
			<th>Date seance</th>
			<th>ID Proces</th>
			<th>Effacer seance</th>
			
		</tr>
		 <c:forEach items="${lstSeance}" var="seance">
			<tr>
				<td>${seance.id}</td>		
				<td>${seance.date}</td>
				<td>${seance.idProces}</td>
				<td>
					<form action="Proces" method="get">
						<input class="btn_black" type="submit" value="supprimer_session" />
						<input type="hidden" name="supprimer_session" value="${seance.id}">
					</form>
				</td>
			</tr>        
	    </c:forEach>
	</table>
</div>