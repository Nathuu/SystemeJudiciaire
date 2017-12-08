package gestionnaireTransaction;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import gestionnaireTable.Avocats;
import gestionnaireTable.Juges;
import gestionnaireTable.Jurys;
import gestionnaireTable.Parties;
import gestionnaireTable.Process;
import gestionnaireTable.Seances;

import model.Juge;
import model.Jury;
import model.Partie;
import model.Proces;
import model.Seance;
import general.Connexion;
import general.IFT287Exception;

public class GestionProces {
	
	private Process process;
	private Seances seances;
	private Parties parties;
	private Juges juges;
	private Jurys jurys;
	private Connexion cx;
	
	GestionProces(Process process, Seances seances, Parties parties, Juges juges, Jurys jurys, Connexion cx) throws IFT287Exception {				
		this.process = process;
		this.seances = seances;
		this.parties = parties;
		this.juges = juges;
		this.jurys = jurys;
		this.cx = cx;
	}

	//	â€” creerProces <idProces> <idJuge> <dateInitiale> <devantJury> <idPartieDefenderesse> <idPartiePoursuivante> 
	/**
	 * Permet de créer un nouveau procès en indiquant si le procès se tient devant jury (1) ou devant un juge seul (0).
	 *         
	 * @param idProces				l'id du proces que l'on souhaite créer 
	 * @param idJuge				l'id du juge que l'on souhaite assignée au procès
	 * @param dateInitiale			la date initiale du procès (première séeance)
	 * @param devantJury			Si le procès se tient devant un jury 
	 * @param idPartieDefenderesse	l'id de la partie qui défend
	 * @param idPartiePoursuivante	l'id de la partie qui poursuit 
	 * @param cx 
	 * @throws SQLException
	 * @throws IFT287Exception
	 */
	public void creerProces(int idProces, int idJuge, Date dateInitiale, boolean devantJury, int idPartieDefenderesse, int  idPartiePoursuivante)
			throws SQLException, IFT287Exception
	{
		try {
			Date now = new Date(Calendar.getInstance().getTimeInMillis());
			if(dateInitiale.before(now)) throw new IFT287Exception("La date initiale est passée");
			
			Juge juge = juges.selectOne(idJuge);
			if(juge == null) throw new IFT287Exception("Ce juge n'existe pas");
			if(!juge.isActif()) throw new IFT287Exception("Ce juge n'est pas disponible");
			
			Partie defense = parties.selectOne(idPartieDefenderesse);
			Partie poursuite = parties.selectOne(idPartiePoursuivante);
			
			if(defense == null) throw new IFT287Exception("Il n'y a pas de défenes");
			if(poursuite == null) throw new IFT287Exception("Il n'y a pas de poursuite");
			if(poursuite.getId() == defense.getId()) throw new IFT287Exception("La poursuite ne peut pas se poursuivre elle-même");
						
			Proces proces = new Proces(idProces, juge, dateInitiale, devantJury, defense, poursuite);
					
			if(process.exist(proces)) throw new IFT287Exception("Le proces existe déjà ");			
			process.creeProces(proces);		

			cx.getConnection().commit();
		}catch(IFT287Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;
		}
	}

	
	
	//	â€” assignerJury <nas> <idProces> 
	/**
	 * Permet dâ€™assigner un jury à  un procès.
	 *     
	 * @param nas				numero d'assurance social
	 * @param idProces			l'id du proces a ajouter 
	 * @param cx 
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public void assignerJury(int nas, int idProces)
			throws IFT287Exception, SQLException
	{
		try {
			Proces proces = process.selectOne(idProces);
			if(proces == null) throw new IFT287Exception("Le proces n'existe pas");
			if(proces.isComplet()) throw new IFT287Exception("Le proces est terminé");
			if(!proces.isDevantJury()) throw new IFT287Exception("Le proces n'est pas devant un jury");
			
			Jury jury = jurys.selectOne(nas);			
	
			if(jury == null) throw new IFT287Exception("ce jury n'existe pas");
			if(!jury.isActif()) throw new IFT287Exception("Ce jury n'est pas disponible");
			jurys.assignerJury(jury, proces);
			
			cx.getConnection().commit();
		}catch(IFT287Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;
		}
	}

	//	â€” ajouterSeance <idSeance> <idProces> <dateSeance> 
	/**
	 * Permet dâ€™ajouter une séance supplémentaire à  un procès.
	 *     
	 * @param idSeance
	 * @param idProces
	 * @param dateSeance
	 * @param cx 
	 * @throws Exception 
	 */
	public void ajouterSeance(int idSeance, int idProces, Date dateSeance)
			throws SQLException, IFT287Exception
	{
		try { 
			Date now = new Date(Calendar.getInstance().getTimeInMillis());		
			if(dateSeance.before(now)) throw new IFT287Exception("La date est passée");
			Proces proces = process.selectOne(idProces);
			Date debut_proces = proces.getDebut();
			if(dateSeance.before(debut_proces)) throw new IFT287Exception("Le procès ne sera pas débuté à cette date");

			if(proces == null) throw new IFT287Exception("Ce proces n'existe pas");			
			Seance seance = new Seance(idSeance, dateSeance, idProces);
			if(proces.isComplet()) throw new IFT287Exception("Ce proces est terminé");			
			
			if(seances.exist(seance)) throw new IFT287Exception("Cette seance existe déjà ");
			seances.ajouterSeance(seance, proces);
			
			cx.getConnection().commit();
		}catch(Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;
		}
	}

	//	â€” supprimerSeance <idSeance> 
	/**
	 * Permet de supprimer une séance future à  un procès non terminé.
	 *     
	 * @param idSeance			l'id de la seance à  supprimer
	 * @param cx 
	 * @throws Exception 
	 */
	public void supprimerSeance(int idSeance)
			throws SQLException, IFT287Exception
	{
		try {			
			Seance seance = seances.selectOne(idSeance);
			if(seance == null) throw new IFT287Exception("Cette séance n'exsite pas");
			
			Date now = new Date(Calendar.getInstance().getTimeInMillis());		
			if(seance.getDate().before(now)) throw new IFT287Exception("La date est passée");
				
			Proces proces = process.selectOne(seance.getIdProces());
			if(proces == null) throw new IFT287Exception("Ce proces n'existe pas et la Seance est invalide");
			if(proces.isComplet()) throw new IFT287Exception("On ne peut retirer une séance d'un procès terminé");
			seances.supprimmerSeance(seance);
			
			cx.getConnection().commit();
		}catch(Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;

		}
	}

	//	â€” terminerProces <idProces> <decision> 
	/**
	 * Permet dâ€™indiquer quâ€™un procès est terminé. Si la poursuite perd, la décision est à 0, si elle gagne, la décision est à  1.
	 *     
	 * @param idProces			l'id du proces à terminé
	 * @param decision			la decision du procès
	 * @param cx 
	 * @throws Exception 
	 */
	public void terminerProces(int idProces, int decision)
			throws SQLException, IFT287Exception
	{
		try { 
			if(decision != 1 && decision != 0) throw new IFT287Exception("Il s'agit d'un code de decision invalide (1 ou 0 seulement");
			
			Proces proces = process.selectOne(idProces);
			if(proces == null) throw new IFT287Exception("Ce procès n'existe pas");			
			proces.setComplet(true);
			proces.setDecision(decision);
			process.terminerProces(proces);
			
			for (Jury jury : proces.getJurys()) {
				jury.setActif(true);
				jurys.updateJury(jury, proces);
			}
						
			Date now = new Date(Calendar.getInstance().getTimeInMillis());
			
			for (Seance seance : proces.getSeances()) {
				if(seance.getDate().before(now)) {
					seances.supprimmerSeance(seance);
				}
			}
			
			cx.getConnection().commit();
		}catch(SQLException e ) {
			System.out.println(e);
			cx.rollback();
		}
		catch(IFT287Exception e ) {
			System.out.println(e);
			cx.rollback();
		}
	}


	//	â€” afficherProces <idProces>
	/** 
	 *  Aï¬ƒche les informations pour un procès.
	 *  
	 * @param idProces
	 * @param cx 
	 * @throws Exception 
	 */
	public void afficherProces(int idProces)
			throws Exception
	{
		try {
			Proces proces = process.selectOne(idProces); 	
		
			process.afficherProces(proces);
			
		}catch(Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;

		}
	}

	public ArrayList<Proces> getProces() throws SQLException, IFT287Exception {		
		return process.getProces();
	}
	
	public Proces getProces(int id) throws SQLException, IFT287Exception {		
		return process.selectOne(id);
	}
	
	public ArrayList<Seance> getSeances(int idProces) throws SQLException {
		return seances.selectAll(idProces);
		
	}
}
