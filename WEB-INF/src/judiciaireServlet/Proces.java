package judiciaireServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general.IFT287Exception;
import gestionnaireTransaction.GestionJudiciaire;


public class Proces extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// verification de l'�tat de la session
			HttpSession session = request.getSession();
			Integer etat = (Integer) session.getAttribute("etat");
			if (etat == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			}

			else {
				session.setAttribute("etat", new Integer(JudiciaireConstantes.CONNECTE));
				GestionJudiciaire gJudiciaireR = (GestionJudiciaire) session.getAttribute("gJudiciaireR");
				GestionJudiciaire gJudiciaireW = (GestionJudiciaire) session.getAttribute("gJudiciaireW");
				
				request.setAttribute("lstJugesActifs", gJudiciaireR.getGestionJuge().getJugesActifs());
				request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());
				request.setAttribute("lstJurys", gJudiciaireR.getGestionJury().getJurys());
				request.setAttribute("lstParties", gJudiciaireR.getGestionPartie().getParties());

				
				// TODO: for now always checking idProces1... will want a
				// dropbown of proces ID and matching that selection
				request.setAttribute("lstSeance", gJudiciaireR.getGestionProces().getSeances(1));
				
				//this is idJudge from frmJudge
				if (request.getParameter("id") != null) {
					int idProces;
					int id_juge;
					String dateInitiale;
					int devant_jury;
					int id_defense;
					int id_poursuite;
					try {
						// R�cupp�re mes valeurs - ints
						idProces = Integer.parseInt(request.getParameter("id"));
						id_juge = Integer.parseInt(request.getParameter("id_juge"));
						devant_jury = Integer.parseInt(request.getParameter("devant_jury"));
						id_defense = Integer.parseInt(request.getParameter("id_defense"));
						id_poursuite = Integer.parseInt(request.getParameter("id_poursuite"));
					} catch (NumberFormatException e) {
						 throw new IFT287Exception("ID proces, ID poursuite/Defense, ID juge et devant jury doivent �tre des entiers");

					}
					dateInitiale = request.getParameter("date_debut");
					
					synchronized (dateInitiale) {
						gJudiciaireW.getGestionProces().creerProces(idProces, id_juge, java.sql.Date.valueOf(dateInitiale), devant_jury==1, id_defense, id_poursuite);
						request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());	
					}
					

				} else if (request.getParameter("idSeance") != null) {
					int idSeance;
					String strDate;
					int id_proces;
					try {
						// R�cupp�re mes valeurs - ints
						id_proces = Integer.parseInt(request.getParameter("id_proces"));
						idSeance = Integer.parseInt(request.getParameter("idSeance"));
					} catch (NumberFormatException e) {
						 throw new IFT287Exception("ID proces, ID poursuite/Defense, ID juge et devant jury doivent �tre des entiers");

					}
					strDate = request.getParameter("date");
					
					synchronized (strDate) {
						gJudiciaireW.getGestionProces().ajouterSeance(idSeance, id_proces, java.sql.Date.valueOf(strDate));	
					}
					
				}

				// transfert de la requ�te � la page JSP pour affichage
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/proces.jsp");
				dispatcher.forward(request, response);
				session.setAttribute("etat", new Integer(JudiciaireConstantes.MEMBRE_SELECTIONNE));

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
		 catch (IFT287Exception e) {
			List<String> listeMessageErreur = new LinkedList<String>();
			listeMessageErreur.add(e.toString());
			request.setAttribute("listeMessageErreur", listeMessageErreur);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/proces.jsp");
			dispatcher.forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}