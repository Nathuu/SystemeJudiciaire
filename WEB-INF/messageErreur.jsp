<%@ page import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  // affichage de la liste des messages d'erreur
  if (request.getAttribute("listeMessageErreur") != null)
    {
%>
    <div style="background-color: rgb(255, 0, 0);">
<%
    ListIterator it = ((List) request.
      getAttribute("listeMessageErreur")).listIterator();
    while (it.hasNext())
      {
%>
      <BR>
      <%= it.next() %>
<%
      }
%>
    </div>
<%
    }
%>
