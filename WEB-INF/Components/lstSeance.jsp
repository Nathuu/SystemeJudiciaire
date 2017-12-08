
<%@ page import="java.util.*,java.text.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<div class="panel">
	<div>
		<c:if test="${multiple}">
			<form action="Proces" method="post">
				<div>
					<label for="proces_seance">Proces ID</label>
					<div class="col-xs-6">
						<div class="select-wrap">
							<select name="proces_seance">
								<option value="${selectedProces}" selected>${selectedProces}</option>			
								<c:forEach items="${lstProces}" var="var">
									<c:if test="${var.id != selectedProces}">
										<option value="${var.id}">${var.id}</option>
									</c:if>									
								</c:forEach>
							</select>
						</div>
					</div>
					<div>
						<input class="btn_black" type="submit" value="Afficher Seances"
							name="afficher_seances" />
					</div>
				</div>
			</form>
		</c:if>
	</div>

	<table id="lstSeance">
		<caption>Seances du proces</caption>
		<tr>
			<th>ID seance</th>
			<th>Date seance</th>
			<th>ID Proces</th>
			<c:if test="${multiple}">
				<th>Effacer seance</th>
			</c:if>
		</tr>
		<c:forEach items="${lstSeance}" var="seance">
			<tr>
				<td>${seance.id}</td>
				<td>${seance.date}</td>
				<td>${seance.idProces}</td>
				<c:if test="${multiple}">
					<td>
						<form action="Proces" method="post">
							<input class="btn_black" type="submit" name="supprimer_session"
								value="Supprimer Seance" /> <input type="hidden"
								name="session_id" value="${seance.id}">
						</form>
					</td>
				</c:if>
			</tr>
		</c:forEach>		
	</table>
	<c:if test="${empty lstSeance}">
		<p>Aucune seance</p>
	</c:if>
</div>