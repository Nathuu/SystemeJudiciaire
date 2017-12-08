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

public class TableauDeBord extends HttpServlet {
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
				request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());

				request.setAttribute("multiple", false);
				
				if (request.getParameter("IDproces") != null) {
					try {
						int idProces = Integer.parseInt(request.getParameter("IDproces"));
						request.setAttribute("lstJuryProces", gJudiciaireR.getGestionJury().getJurys(idProces));
						request.setAttribute("lstPartiDefense", gJudiciaireR.getGestionProces().getProces(idProces).getDefense());
						request.setAttribute("lstPartiPoursuite", gJudiciaireR.getGestionProces().getProces(idProces).getPoursuite());
						request.setAttribute("procesUnique", gJudiciaireR.getGestionProces().getProces(idProces));
						request.setAttribute("lstSeance", gJudiciaireR.getGestionProces().getSeances(idProces));
						request.setAttribute("selectedProces", idProces);
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de id proces incorrect");
					}
				}
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/tableauDeBord.jsp");
				dispatcher.forward(request, response);
				session.setAttribute("etat", new Integer(JudiciaireConstantes.MEMBRE_SELECTIONNE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}	 
		catch (IFT287Exception e) {
			List<String> listeMessageErreur = new LinkedList<String>();
			listeMessageErreur.add(e.toString());
			request.setAttribute("listeMessageErreur", listeMessageErreur);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/selectionMembre.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}