/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.0.M10
 * Generated at: 2017-12-08 22:37:01 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class jurys_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/css/style.css", Long.valueOf(1512157975544L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> \r\n");
      out.write("<title>Jurys</title>\r\n");
      out.write("<style>\r\n");
      out.write(" \t");
      out.write("@charset \"ISO-8859-1\";\n");
      out.write("\n");
      out.write("html, body, div#main-panel {\n");
      out.write("\theight: 100%;\n");
      out.write("}\n");
      out.write("\n");
      out.write("div#inline-left{\n");
      out.write("\tfloat: left;\n");
      out.write("}\n");
      out.write("\n");
      out.write(".container-inline {\n");
      out.write("  display: flex;\n");
      out.write("}\n");
      out.write(".container-inline > div {\n");
      out.write("  flex: 1; /*grow*/\n");
      out.write("}\n");
      out.write("\n");
      out.write("div.panel {\n");
      out.write("\tborder: 1px solid black;\n");
      out.write("\tpadding: 10px;\n");
      out.write("\tmargin: 10px;\n");
      out.write("\twidth: auto;\n");
      out.write("\tdisplay: inline-block;\n");
      out.write("\tborder-radius: 5px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("table {\n");
      out.write("\tborder-radius: 5px;\n");
      out.write("\tborder-collapse: collapse;\n");
      out.write("\tborder: solid 2px black;\n");
      out.write("}\n");
      out.write("\n");
      out.write("table th {\n");
      out.write("\tbackground-color: #111111;\n");
      out.write("\tcolor: white;\n");
      out.write("}\n");
      out.write("\n");
      out.write("table td, table th {\n");
      out.write("\tborder: solid 1px black;\n");
      out.write("\tborder-collapse: collapse;\n");
      out.write("\tpadding: 10px;\n");
      out.write("\twidth: 75px;\n");
      out.write("\tfont-size: 18px;\n");
      out.write("}\n");
      out.write("\n");
      out.write(".btn_black {\n");
      out.write("\tbackground-color: black;\n");
      out.write("\tcolor: white;\n");
      out.write("}\n");
      out.write("\n");
      out.write("div.row {\n");
      out.write("\twidth: 100%;\n");
      out.write("\tpadding-top: 10px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("div.col-xs-6 {\n");
      out.write("\twidth: 50%;\n");
      out.write("\tdisplay: inline;\n");
      out.write("\tpadding: 5px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("label {\n");
      out.write("\twidth: 100px;\n");
      out.write("\tdisplay: inline-block;\n");
      out.write("}\n");
      out.write("\n");
      out.write("caption {\n");
      out.write("\tfont-size:2em;\n");
      out.write("\tfont-weight:bold;\n");
      out.write("}\n");
      out.write("\n");
      out.write("div.select-wrap {\n");
      out.write("\tdisplay: inline-block;\n");
      out.write("\twidth: 200px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("legend {\n");
      out.write("\tmargin:0 auto;\n");
      out.write("}\n");
      out.write("\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/header.jsp", out, false);
      out.write('\r');
      out.write('\n');
      out.write('	');
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/messageErreur.jsp", out, false);
      out.write("\r\n");
      out.write("\t<div id=\"main-panel\">\r\n");
      out.write("\t\t<h1>Jurys</h1>\t\t\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/Components/lstJury.jsp", out, false);
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div>\r\n");
      out.write("\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/Components/frmJury.jsp", out, false);
      out.write("\r\n");
      out.write("\t\t</div>\t\t\r\n");
      out.write("\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/WEB-INF/footer.jsp", out, false);
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
