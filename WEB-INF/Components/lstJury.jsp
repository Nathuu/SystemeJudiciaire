
<%@ page import="java.util.*,java.text.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<div class="panel">
	<table id="lstJury">
		<caption>Jurys</caption>
		<tr>
			<th>id</th>
			<th>prenom</th>
			<th>nom</th>
			<th>sexe</th>
			<th>age</th>
			<c:if test="${multiple}">
				<th>Assigner a Proces</th>
			</c:if>
		</tr>
		<c:if test="${multiple}">
			<c:forEach items="${lstJurys}" var="jury">
				<tr>
					<td>${jury.nas}</td>
					<td>${jury.prenom}</td>
					<td>${jury.nom}</td>
					<td>${jury.sexe}</td>
					<td>${jury.age}</td>
					<td>
						<div class="container-inline">
							<c:if test="${jury.actif}">
								<form action="${pageAction}" method="post">
									<div>
										<select name="proces_jury">
											<c:forEach items="${lstProces}" var="var">
												<option value="${var.id}">${var.id}</option>
											</c:forEach>
										</select>
									</div>
									<input class="btn_black" type="submit" name="assigner_jury"
										value="Assigner Jury" /> <input type="hidden" name="id_jury"
										value="${jury.nas}">
								</form>
							</c:if>
							<c:if test="${jury.actif == false}">
								<p>Non disponible</p>
							</c:if>
						</div>
					</td>
				</tr>
			</c:forEach>			
		</c:if>
		<c:if test="${not multiple}">
			<c:forEach items="${lstJuryProces}" var="jury">
				<tr>
					<td>${jury.nas}</td>
					<td>${jury.prenom}</td>
					<td>${jury.nom}</td>
					<td>${jury.sexe}</td>
					<td>${jury.age}</td>
				</tr>
			</c:forEach>
			
		</c:if>
	</table>
	<c:if test="${empty lstJurys}">
		<p>Aucun jurys</p>
	</c:if>
</div>