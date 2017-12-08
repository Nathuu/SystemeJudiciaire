
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
			<c:if test="${multiple}">
				<th>Terminer Proces</th>
			</c:if>
		</tr>
		<c:if test="${multiple}">
			<c:forEach items="${lstProces}" var="proces">
				<tr>
					<td>${proces.id}</td>
					<td>${proces.debut}</td>
					<td>${proces.devantJury}</td>
					<td>${proces.complet}</td>
					<td>${proces.id_poursuite}</td>
					<td>${proces.id_defense}</td>
					<c:if test="${not proces.complet}">
						<td>-</td>
					</c:if>
					<c:if test="${proces.complet}">
						<td>${proces.decision}</td>
					</c:if>
					<td>${proces.id_juge}</td>
					<td>
						<form action="Proces" method="post">
							<div class="col-xs-6">
								<label for="decision">Decision</label>
								<div class="select-wrap">
									<select name="decision">
										<option value="1" selected="selected">Victoire
											Poursuite</option>
										<option value="0">Victoire Defense</option>
									</select>
								</div>
							</div>
							<input class="btn_black" type="submit" value="Terminer Proces"
								name="terminer_proces" /> <input type="hidden"
								name="idproces_terminer" value="${proces.id}">
						</form>
					</td>
				</tr>
			</c:forEach>
		</c:if>		
		<c:if test="${not multiple}">		
			<tr>
				<td>${procesUnique.id}</td>
				<td>${procesUnique.debut}</td>
				<td>${procesUnique.devantJury}</td>
				<td>${procesUnique.complet}</td>
				<td>${procesUnique.id_poursuite}</td>
				<td>${procesUnique.id_defense}</td>
				<td>${procesUnique.decision}</td>
				<td>${procesUnique.id_juge}</td>
			</tr>
		</c:if>
	</table>
</div>