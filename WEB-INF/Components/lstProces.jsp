
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
			<th>Terminer Proces</th>
			
		</tr>		
		 <c:forEach items="${lstProces}" var="proces">
			<tr>
				<td>${proces.id}</td>		
				<td>${proces.debut}</td>
				<td>${proces.devantJury}</td>				
				<td>${proces.complet}</td>
				<td>${proces.id_poursuite}</td>
				<td>${proces.id_defense}</td>
				<td>${proces.decision}</td>
				<td>${proces.id_juge}</td>
				<td>

					<form action="Proces" method="get">
						<div class="col-xs-6">
							<label for="decision">Decision</label>
							<div class="select-wrap">
								<select name="decision">
									<option value="1" selected="selected">Victoire Poursuite</option>
									<option value="0">Victoire Defense</option>
								</select>
							</div>
						</div>
						<input class="btn_black" type="submit" value="terminer_proces" />
						<input type="hidden" name="terminer_proces" value="${proces.id}">
					</form>
				</td>
			</tr>
	    </c:forEach>
	</table>
</div>