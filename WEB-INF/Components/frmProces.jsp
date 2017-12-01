<style>

</style>
<!-- action=Proces pour servletProces et method get pour le doGet? -->
<form action="Proces" method="get">
	<div class="panel">
		<legend>Ajouter un Proces</legend>
		<div class="row">
			<div class="col-xs-6">
				<label for="id">ID du proces</label> <input name="id" type="number" />
			</div>
			<div class="col-xs-6">
				<label for="id_juge">ID du juge</label> <input name="id_juge" type="number" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<label for="date_debut">Date Debut</label> <input name="date_debut" type="text" />
			</div>
			<div class="col-xs-6">
				<label for="devant_jury">Devant jury</label> <input name="devant_jury" type="number" />
			</div>
		</div>
		<div class="row">	
			<div class="col-xs-6">
				<label for="id_defense">ID defense</label> <input name="id_defense" type="number" />
			</div>
			<div class="col-xs-6">
				<label for="id_poursuite">ID poursuite</label> <input name="id_poursuite" type="number" />
			</div>
		</div>
		<div class="row">
			<input class="btn_black" type="submit" value="Ajouter">
		</div>
	</div>
</form>
