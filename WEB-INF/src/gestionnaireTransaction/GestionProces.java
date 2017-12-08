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

	//	— creerProces <idProces> <idJuge> <dateInitiale> <devantJury> <idPartieDefenderesse> <idPartiePoursuivante> 
	/**
	 * Permet de cr�er un nouveau proc�s en indiquant si le proc�s se tient devant jury (1) ou devant un juge seul (0).
	 *         
	 * @param idProces				l'id du proces que l'on souhaite cr�er 
	 * @param idJuge				l'id du juge que l'on souhaite assign�e au proc�s
	 * @param dateInitiale			la date initiale du proc�s (premi�re s�eance)
	 * @param devantJury			Si le proc�s se tient devant un jury 
	 * @param idPartieDefenderesse	l'id de la partie qui d�fend
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
			if(dateInitiale.before(now)) throw new IFT287Exception("La date initiale est pass�e");
			
			Juge juge = juges.selectOne(idJuge);
			if(juge == null) throw new IFT287Exception("Ce juge n'existe pas");
			if(!juge.isActif()) throw new IFT287Exception("Ce juge n'est pas disponible");
			
			Partie defense = parties.selectOne(idPartieDefenderesse);
			Partie poursuite = parties.selectOne(idPartiePoursuivante);
			
			if(defense == null) throw new IFT287Exception("Il n'y a pas de d�fenes");
			if(poursuite == null) throw new IFT287Exception("Il n'y a pas de poursuite");
			if(poursuite.getId() == defense.getId()) throw new IFT287Exception("La poursuite ne peut pas se poursuivre elle-m�me");
						
			Proces proces = new Proces(idProces, juge, dateInitiale, devantJury, defense, poursuite);
					
			if(process.exist(proces)) throw new IFT287Exception("Le proces existe d�j�");			
			process.creeProces(proces);		

			cx.getConnection().commit();
		}catch(IFT287Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;
		}
	}

	
	
	//	— assignerJury <nas> <idProces> 
	/**
	 * Permet d’assigner un jury � un proc�s.
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
			if(proces.isComplet()) throw new IFT287Exception("Le proces est termin�");
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

	//	— ajouterSeance <idSeance> <idProces> <dateSeance> 
	/**
	 * Permet d’ajouter une s�ance suppl�mentaire � un proc�s.
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
			if(dateSeance.before(now)) throw new IFT287Exception("La date est pass�e");
			Proces proces = process.selectOne(idProces);
			Date debut_proces = proces.getDebut();
			if(dateSeance.before(debut_proces)) throw new IFT287Exception("Le proc�s ne sera pas d�but� � cette date");

			if(proces == null) throw new IFT287Exception("Ce proces n'existe pas");			
			Seance seance = new Seance(idSeance, dateSeance, idProces);
			if(proces.isComplet()) throw new IFT287Exception("Ce proces est termin�");			
			
			if(seances.exist(seance)) throw new IFT287Exception("Cette seance existe d�j�");
			seances.ajouterSeance(seance, proces);
			
			cx.getConnection().commit();
		}catch(Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;
		}
	}

	//	— supprimerSeance <idSeance> 
	/**
	 * Permet de supprimer une s�ance future � un proc�s non termin�.
	 *     
	 * @param idSeance			l'id de la seance � supprimer
	 * @param cx 
	 * @throws Exception 
	 */
	public void supprimerSeance(int idSeance)
			throws SQLException, IFT287Exception
	{
		try {			
			Seance seance = seances.selectOne(idSeance);
			if(seance == null) throw new IFT287Exception("Cette s�ance n'exsite pas");
			
			Date now = new Date(Calendar.getInstance().getTimeInMillis());		
			if(seance.getDate().before(now)) throw new IFT287Exception("La date est pass�e");
				
			Proces proces = process.selectOne(seance.getIdProces());
			if(proces == null) throw new IFT287Exception("Ce proces n'existe pas et la Seance est invalide");
			if(proces.isComplet()) throw new IFT287Exception("On ne peut retirer une s�ance d'un proc�s termin�");
			seances.supprimmerSeance(seance);
			
			cx.getConnection().commit();
		}catch(Exception e ) {
			System.out.println(e);
			cx.rollback();
			throw e;

		}
	}

	//	— terminerProces <idProces> <decision> 
	/**
	 * Permet d’indiquer qu’un proc�s est termin�. Si la poursuite perd, la d�cision est �0, si elle gagne, la d�cision est � 1.
	 *     
	 * @param idProces			l'id du proces � termin�
	 * @param decision			la decision du proc�s
	 * @param cx 
	 * @throws Exception 
	 */
	public void terminerProces(int idProces, int decision)
			throws SQLException, IFT287Exception
	{
		try { 
			if(decision != 1 && decision != 0) throw new IFT287Exception("Il s'agit d'un code de decision invalide (1 ou 0 seulement");
			
			Proces proces = process.selectOne(idProces);
			if(proces == null) throw new IFT287Exception("Ce proc�s n'existe pas");			
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


	//	— afficherProces <idProces>
	/** 
	 *  Aﬃche les informations pour un proc�s.
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
