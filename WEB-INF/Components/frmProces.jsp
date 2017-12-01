<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %> 
<!-- action=Proces pour servletProces et method get pour le doGet? -->
<form action="Proces" method="get">
	<div class="panel">
		<legend>Ajouter un Proces</legend>
		<div class="row">
			<div class="col-xs-6">
				<label for="id">ID du proces</label> <input name="id" type="number" />
			</div>			
			<div class="col-xs-6">
				<label for="id_juge">ID du juge</label>
				<div class="select-wrap">
					<select name="id_juge">
						 <c:forEach items="${lstJugesActifs}" var="juge">							
							<option value="${juge.id}" selected="selected">${juge.prenom} ${juge.nom}</option>								
					    </c:forEach>					
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<label for="date_debut">Date Debut</label> <input name="date_debut" type="text" placeholder="AAAA-MM-DD"/>
			</div>
			<div class="col-xs-6">
				<label for="devant_jury">Devant jury</label>
				<div class="select-wrap">
					<select name="devant_jury">
						<option value="1" selected="selected">Avec Jury</option>
						<option value="0">Juge seulement</option>
					</select>
				</div>
			</div>
			
			
			
		</div>
		<div class="row">	
			<div class="col-xs-6">
				<label for="id_defense">Partie Defense</label>
				<div class="select-wrap">
					<select name="id_defense">
						 <c:forEach items="${lstParties}" var="var">							
							<option value="${var.id}" selected="selected">${var.prenom} ${var.nom}</option>								
					    </c:forEach>					
					</select>
				</div>
			</div>
			<div class="col-xs-6">
				<label for="id_poursuite">Partie Poursuite</label>
				<div class="select-wrap">
					<select name="id_poursuite">
						 <c:forEach items="${lstParties}" var="var">							
							<option value="${var.id}" selected="selected">${var.prenom} ${var.nom}</option>								
					    </c:forEach>					
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<input class="btn_black" type="submit" value="Ajouter">
		</div>
	</div>
</form>
