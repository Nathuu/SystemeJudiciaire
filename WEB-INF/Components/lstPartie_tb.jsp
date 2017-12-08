
<%@ page import="java.util.*,java.text.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<div class="panel">
	<table id="lstPartiProces">
		<caption>Poursuite</caption>
		<tr>
			<th>id</th>
			<th>prenom</th>
			<th>nom</th>
			<th>idAvocat</th>
			<th>prenom avocat</th>
			<th>nom avocatt</th>
		</tr>
		<tr>
			<td>${lstPartiPoursuite.id}</td>
			<td>${lstPartiPoursuite.prenom}</td>
			<td>${lstPartiPoursuite.nom}</td>
			<td>${lstPartiPoursuite.avocat.id}</td>
			<td>${lstPartiPoursuite.avocat.prenom}</td>
			<td>${lstPartiPoursuite.avocat.nom}</td>
		</tr>
	</table>
	<table id="lstPartiProces">
		<caption>Défense</caption>
		<tr>
			<th>id</th>
			<th>prenom</th>
			<th>nom</th>
			<th>idAvocat</th>
			<th>prenom avocat</th>
			<th>nom avocatt</th>
		</tr>
		<tr>
			<td>${lstPartiDefense.id}</td>
			<td>${lstPartiDefense.prenom}</td>
			<td>${lstPartiDefense.nom}</td>
			<td>${lstPartiDefense.avocat.id}</td>
			<td>${lstPartiDefense.avocat.prenom}</td>
			<td>${lstPartiDefense.avocat.nom}</td>
		</tr>
	</table>
</div>