<style>

</style>
<!-- action=Proces pour servletProces et method get pour le doGet? -->
<form action="Proces" method="get">
	<div class="panel">
		<legend>Ajouter une Seance</legend>
		<div class="row">
			<div class="col-xs-6">
				<label for="id">ID de la Seance</label> <input name="id" type="number" />
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<label for="date">Date Debut</label> <input name="date" type="text" />
			</div>
		</div>
		<div class="row">	
			<div class="col-xs-6">
				<label for="id_juge">ID du juge</label> <input name="id_juge" type="number" />
			</div>
		</div>
		<div class="row">
			<input class="btn_black" type="submit" value="Ajouter">
		</div>
	</div>
</form>
