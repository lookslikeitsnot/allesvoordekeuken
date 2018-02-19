package be.vdab.servlets.artikels;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Artikel;
import be.vdab.services.ArtikelService;
import be.vdab.util.StringUtils;

@WebServlet("/artikels/kortingen.htm")
public class KortingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/artikels/artikelkortingen.jsp";
	private final transient ArtikelService artikelService = new ArtikelService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getQueryString() != null) {
			String id = request.getParameter("id");
			if (StringUtils.isLong(id)) {
				artikelService.read(Long.parseLong(id)).ifPresent(artikel -> request.setAttribute("artikel", artikel));
			} else {
				request.setAttribute("fouten", Collections.singletonMap("id", "tik een getal"));
			}
		} else {
			List<Artikel> artikels = artikelService.findAll();
			request.setAttribute("artikels", artikels);
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
