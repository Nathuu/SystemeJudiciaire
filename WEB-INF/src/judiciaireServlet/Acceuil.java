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

import Bibliotheque.BiblioException;
import Bibliotheque.GestionBibliotheque;

public class Acceuil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// verification de l'état de la session
		HttpSession session = request.getSession();
		Integer etat = (Integer) session.getAttribute("etat");
		if (etat == null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		} else {
			session.setAttribute("etat", new Integer(JudiciaireConstantes.CONNECTE));

			// transfert de la requête à la page JSP pour affichage
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/acceuil.jsp");
			dispatcher.forward(request, response);
			session.setAttribute("etat", new Integer(JudiciaireConstantes.MEMBRE_SELECTIONNE));
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
