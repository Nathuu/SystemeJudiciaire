package judiciaireServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import general.IFT287Exception;
import gestionnaireTransaction.GestionJudiciaire;

public class Jurys extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// verification de l'�tat de la session
			HttpSession session = request.getSession();
			Integer etat = (Integer) session.getAttribute("etat");
			if (etat == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request, response);
			} else {

				session.setAttribute("etat", new Integer(JudiciaireConstantes.CONNECTE));
				GestionJudiciaire gJudiciaireR = (GestionJudiciaire) session.getAttribute("gJudiciaireR");
				GestionJudiciaire gJudiciaireW = (GestionJudiciaire) session.getAttribute("gJudiciaireW");
				request.setAttribute("lstJurys", gJudiciaireR.getGestionJury().getJurys());
				request.setAttribute("pageAction", "Jurys");
				request.setAttribute("multiple", true);
				request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());

				if (request.getParameter("id") != null) {
					int id;
					String prenom;
					String nom;
					int age;
					char sexe;

					try {
						// R�cupp�re mes valeurs
						id = Integer.parseInt(request.getParameter("id"));
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de id incorrect");
					}
					prenom = request.getParameter("prenom");
					nom = request.getParameter("nom");

					sexe = request.getParameter("sexe").charAt(0);
					try {
						age = Integer.parseInt(request.getParameter("age"));
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de age incorrect");
					}
					
					synchronized (nom) {
						gJudiciaireW.getGestionJury().inscrireJury(id, prenom, nom, sexe, age);
						request.setAttribute("lstJurys", gJudiciaireR.getGestionJury().getJurys());
					}
				}
				
				else if (request.getParameter("assigner_jury") != null){
					try {
						synchronized (gJudiciaireW) {
							int idJury = Integer.parseInt(request.getParameter("id_jury"));
							int idProces = Integer.parseInt(request.getParameter("proces_jury"));
							gJudiciaireW.getGestionProces().assignerJury(idJury, idProces);
							request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());
							request.setAttribute("lstJurys", gJudiciaireR.getGestionJury().getJurys());

						}
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de seance incorrect");
					} catch (IFT287Exception e) {
						throw e;
					}

				}
				
				request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());
				// transfert de la requ�te � la page JSP pour affichage
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jurys.jsp");
				dispatcher.forward(request, response);
				session.setAttribute("etat", new Integer(JudiciaireConstantes.MEMBRE_SELECTIONNE));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (IFT287Exception e) {
			List<String> listeMessageErreur = new LinkedList<String>();
			listeMessageErreur.add(e.toString());
			request.setAttribute("listeMessageErreur", listeMessageErreur);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jurys.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}