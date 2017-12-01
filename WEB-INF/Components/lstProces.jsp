
<%@ page import="java.util.*,java.text.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %> 

<div class="panel">
	<table id="lstProces">
	<caption>Proces</caption>
		<tr>
			<th>ID proces</th>
			<th>Date debut</th>
			<th>Devant Jury</th>			
			<th>Complet</th>
			<th>ID Poursuite</th>
			<th>ID Defense</th>
			<th>Decision</th>
			<th>ID Juge</th>
		</tr>		
		 <c:forEach items="${lstProces}" var="proces">
			<tr>
				<td>${proces.id}</td>		
				<td>${proces.debut}</td>
				<td>${proces.devantJury}</td>				
				<td>${proces.complet}</td>
				<td>${proces.id_poursuite}</td>
				<td>${proces.id_defense}</td>
				<td>${proces.id_decision}</td>
				<td>${proces.id_juge}</td>
			</tr>
	    </c:forEach>
	</table>
</div>