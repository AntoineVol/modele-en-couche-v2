package presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import model.Client;
import service.ClientService;
import service.ClientServiceImpl;

/**
 * Servlet permettant gérer toutes les requêtes HTTP GET se terminant par
 * ".html" (c.f. web.xml). La page JSP demandée est retrouvée grâce à
 * l'identifiant placé entre le dernier '/' et '.html' (ex:
 * /mon-appli/ma-super-page.html).
 */
public class ViewsServlet extends AutowiredServlet {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ViewsServlet.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	private ClientService service;

//	public ViewsServlet() {
//		this.service = new ClientServiceImpl();
//	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOGGER.debug("Passage dans doGet avec servletPath={}",
				request.getServletPath());
		final String view = request.getServletPath().replace(".html", "")
				.split("/")[1];
		LOGGER.debug("Nom de vue déterminé depuis servletPath -> {}", view);
		if (view != null && !view.isEmpty()) {
			String clientId = request.getParameter("clientId");
			switch (view) {
			case "show-all":
				request.setAttribute("clients", this.service.getAllClient());
				break;
			case "show-client":
			case "edit-client":
				request.setAttribute("client", this.service
						.getValidatedClient(Integer.parseInt(clientId)));
				break;
			}
			this.forwardToJsp(request, response, view);
		} else {
			LOGGER.debug(
					"Aucun nom de vue valide, génération de l'erreur HTTP 405.");
			// Utilisation de super pour déclencher un response.sendError(..),
			// voir le code soruce de HttpServlet.doGet(..).
			super.doGet(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String strId = request.getParameter("id");
		final String name = request.getParameter("name");
		this.service.saveClient(new Client(Integer.parseInt(strId), name));
		response.sendRedirect(request.getContextPath() + "/show-all.html");
	}

	private void forwardToJsp(HttpServletRequest request,
			HttpServletResponse response, String view)
			throws ServletException, IOException {
		LOGGER.debug("Forward vers la JSP correspondante.");
		final String jspPath = "/WEB-INF/views/" + view + ".jsp";
		this.getServletContext().getRequestDispatcher(jspPath).forward(request,
				response);
	}
}
