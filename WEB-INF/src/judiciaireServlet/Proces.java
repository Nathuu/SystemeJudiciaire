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
			// verification de l'état de la session
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
				request.setAttribute("pageAction", "Proces");
				request.setAttribute("multiple", true);

				request.setAttribute("lstSeance", gJudiciaireR.getGestionProces().getSeances(-1));

				// this is idJudge from frmJudge
				if (request.getParameter("id") != null) {
					int idProces;
					int id_juge;
					String dateInitiale;
					Date dateSql;
					int devant_jury;
					int id_defense;
					int id_poursuite;
					try {
						// Récuppère mes valeurs - ints
						idProces = Integer.parseInt(request.getParameter("id"));
						id_juge = Integer.parseInt(request.getParameter("id_juge"));
						devant_jury = Integer.parseInt(request.getParameter("devant_jury"));
						id_defense = Integer.parseInt(request.getParameter("id_defense"));
						id_poursuite = Integer.parseInt(request.getParameter("id_poursuite"));
					} catch (NumberFormatException e) {
						throw new IFT287Exception(
								"ID proces, ID poursuite/Defense, ID juge et devant jury doivent être des entiers");

					}

					try {
						dateInitiale = request.getParameter("date_debut");
						dateSql = java.sql.Date.valueOf(dateInitiale);
					} catch (Exception e) {
						throw new IFT287Exception("La date doit être formattée aaaa-mm-dd");

					}

					synchronized (gJudiciaireW) {
						gJudiciaireW.getGestionProces().creerProces(idProces, id_juge, dateSql, devant_jury == 1,
								id_defense, id_poursuite);
						request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());
					}

				} else if (request.getParameter("idSeance") != null) {
					int idSeance;
					String strDate;
					int id_proces;
					try {
						// Récuppère mes valeurs - ints
						id_proces = Integer.parseInt(request.getParameter("id_proces"));
						idSeance = Integer.parseInt(request.getParameter("idSeance"));
					} catch (NumberFormatException e) {
						throw new IFT287Exception("ID seance doit etre un entier");

					}
					strDate = request.getParameter("date");
					synchronized (gJudiciaireW) {
						gJudiciaireW.getGestionProces().ajouterSeance(idSeance, id_proces,
								java.sql.Date.valueOf(strDate));
					}

					request.setAttribute("lstSeance", gJudiciaireR.getGestionProces().getSeances(1));

				}

				else if (request.getParameter("supprimer_session") != null) {
					try {
						synchronized (gJudiciaireW) {
							int idProces = Integer.parseInt(request.getParameter("session_id"));
							gJudiciaireW.getGestionProces().supprimerSeance(idProces);
							request.setAttribute("lstSeance", gJudiciaireR.getGestionProces().getSeances(-1));
						}
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Session incorrect");
					}
				}

				else if (request.getParameter("terminer_proces") != null) {
					try {
						synchronized (gJudiciaireW) {
							gJudiciaireW.getGestionProces().terminerProces(
									Integer.parseInt(request.getParameter("idproces_terminer")),
									Integer.parseInt(request.getParameter("decision")));
							request.setAttribute("lstProces", gJudiciaireR.getGestionProces().getProces());

						}
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de id incorrect");
					}
				}

				else if (request.getParameter("afficher_seances") != null) {
					try {
						synchronized (gJudiciaireW) {
							int idProces = Integer.parseInt(request.getParameter("proces_seance"));
							request.setAttribute("lstSeance",
									gJudiciaireR.getGestionProces().getSeances(idProces));
							request.setAttribute("selectedProces",idProces);

						}
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de seance incorrect");
					}
				}
				else if (request.getParameter("assigner_jury") != null){
					try {
						synchronized (gJudiciaireW) {
							int idJury = Integer.parseInt(request.getParameter("id_jury"));
							int idProces = Integer.parseInt(request.getParameter("proces_jury"));
							gJudiciaireW.getGestionProces().assignerJury(idJury, idProces);
						}
					} catch (NumberFormatException e) {
						throw new IFT287Exception("Format de seance incorrect");
					} catch (IFT287Exception e) {
						throw e;
					}
				}
				

				// transfert de la requête à la page JSP pour affichage
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/proces.jsp");
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/proces.jsp");
			dispatcher.forward(request, response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}