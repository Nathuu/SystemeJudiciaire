<style>

</style>
<!-- action=Proces pour servletProces et method get pour le doGet? -->
<form action="Proces" method="get">
	<div class="panel">
		<legend>Ajouter une Seance</legend>
		<div class="row">
			<div class="col-xs-6">
				<label for="idSeance">ID de la Seance</label> <input name="id" type="number" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<label for="date">Date Seance</label> <input name="date" type="text" />
			</div>
		</div>
		<div class="row">	
			<div class="col-xs-6">
				<label for="id_proces">ID du proces</label> <input name="id_juge" type="number" />
			</div>
			
			<div class="col-xs-6">
				<label for="id_proces">Id Proces</label>
				<div class="select-wrap">
					<select name="id_proces">
						 <c:forEach items="${lstProces}" var="var">							
							<option value="${var.id}" selected="selected">${var.id}</option>								
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
