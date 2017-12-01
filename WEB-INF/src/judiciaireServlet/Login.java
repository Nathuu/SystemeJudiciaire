package judiciaireServlet;

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import general.IFT287Exception;
import gestionnaireTransaction.GestionJudiciaire;

/**
 * Classe pour login syst�me de gestion judiciare
 * <P>
 * Syst�me de gestion judiciaire &copy; 2017 Marc Dupuis, Francis Vachon,  Universit� de
 * Remerciement Marc Frappier pour l'exemple de Biblioth�que &copy; 2004
 * Sherbrooke
 */

public class Login extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            HttpSession session = request.getSession();
            // fermer la session si elle a d�j� �t� ouverte lors d'un appel pr�c�dent
            // survient lorsque l'usager recharge la page login.jsp
            if (session.getAttribute("etat") != null)
            {
                // pour d�boggage seulement : afficher no session et information
                System.out.println("GestionJudiciaire: session d�ja cr�e; id=" + session.getId());
                // la m�thode invalidate appelle le listener BiblioSessionListener; cette classe est charg�e lors du
                // d�marrage de
                // l'application par le serveur (voir le fichier web.xml)
                session.invalidate();
                session = request.getSession();
                System.out.println("GestionJudiciaire: session invalid�e");
            }

            // lecture des param�tres du formulaire login.jsp
            String userId = request.getParameter("userIdBD");
            String motDePasse = request.getParameter("motDePasseBD");
            String serveur = request.getParameter("serveur");
            String bd = request.getParameter("bd");

            if (serveur != null)
            {
                /*
                 * ouvrir une connexion avec la BD et cr�er les gestionnaires et
                 * stocker dans la session. On ouvre une session en lecture
                 * readcommited pour les interrogations seulement et une autre
                 * en mode serialisable, pour les transactions
                 */
                System.out.println("Login: session id=" + session.getId());
                GestionJudiciaire gJudiciaire = new GestionJudiciaire(serveur, bd, userId, motDePasse);                
                gJudiciaire.getConnexion().setIsolationReadCommited();
                session.setAttribute("gJudiciaire", gJudiciaire);
                
                // TODO Do we need this?
                //GestionBibliotheque biblioUpdate = new GestionBibliotheque(serveur, bd, userId, motDePasse);
                //session.setAttribute("biblioUpdate", biblioUpdate);
                //biblioInterrogation
                response.sendRedirect(request.getContextPath() + "/Acceuil");
//                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/acceuil.jsp");
//                dispatcher.forward(request, response);
                session.setAttribute("etat", new Integer(JudiciaireConstantes.CONNECTE));
            }
            else
            {
                throw new SQLException("Vous devez vous connecter au serveur.");
            }
        }
        catch (SQLException e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add("Erreur de connexion au serveur");
            listeMessageErreur.add(e.toString());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
            // pour d�boggage seulement : afficher tout le contenu de
            // l'exception
            e.printStackTrace();
        }
        catch (IFT287Exception e)
        {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    // Dans les formulaires, on utilise la m�thode POSTdonc, si le servlet est appel� avec la m�thode GET
    // on appelle POST
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

}
