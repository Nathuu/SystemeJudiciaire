package judiciaireServlet;

import java.io.IOException;
import java.sql.Date;
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

public class Proces extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// verification de l'état de la session
		HttpSession session = request.getSession();
		Integer etat = (Integer) session.getAttribute("etat");
		if (etat == null)
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        }

        else {
			session.setAttribute("etat", new Integer(JudiciaireConstantes.CONNECTE));
			GestionJudiciaire gJudiciaire = (GestionJudiciaire) session.getAttribute("gJudiciaire");
			try {
				request.setAttribute("lstProces", gJudiciaire.getGestionProces().getProces());
				request.setAttribute("lstJurys", gJudiciaire.getGestionJury().getJurys());
				//TODO: for now always checking idProces1... will want a dropbown of proces ID and matching that selection
				request.setAttribute("lstSeance", gJudiciaire.getGestionProces().getSeances(1));

			} catch (SQLException e1) {
				//TODO: why do I need a try/catch here when in not inother servelt?
				e1.printStackTrace();
			}
			
			// transfert de la requête à la page JSP pour affichage
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/proces.jsp");
			dispatcher.forward(request, response);
			session.setAttribute("etat", new Integer(JudiciaireConstantes.MEMBRE_SELECTIONNE));
	}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}