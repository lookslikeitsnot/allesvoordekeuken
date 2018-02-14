package be.vdab.servlets.artikels;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Artikel;
import be.vdab.services.ArtikelService;
import be.vdab.util.StringUtils;

@WebServlet("/artikels/toevoegen.htm")
public class ToevoegenServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/artikels/toevoegen.jsp";
	private static final String REDIRECT_URL = "%s/artikels/zoeken.htm?id=%d";
	private final transient ArtikelService artikelService = new ArtikelService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fouten = new HashMap<>();
		String naam = request.getParameter("naam");
		if(!Artikel.isNaamValid(naam)) {
			fouten.put("naam", "verplicht");
		}
		String aankoopprijsString = request.getParameter("aankoopprijs");
		BigDecimal aankoopprijs = null;
		if(!StringUtils.isBigDecimal(aankoopprijsString)) {
			fouten.put("aankoopprijs", "verplicht");
		} else {
			aankoopprijs = new BigDecimal(aankoopprijsString);
			if(!Artikel.isAankoopprijsValid(aankoopprijs)) {
				fouten.put("aankoopprijs", "verplicht");
			} 
		}
		
		String verkoopprijsString = request.getParameter("verkoopprijs");
		BigDecimal verkoopprijs = null;
		if(!StringUtils.isBigDecimal(verkoopprijsString)) {
			fouten.put("verkoopprijs", "verplicht");
		} else {
			verkoopprijs = new BigDecimal(verkoopprijsString);
			if(!(Artikel.isAankoopprijsValid(aankoopprijs) && 
					Artikel.isVerkoopprijsValid(aankoopprijs, verkoopprijs))) {
				fouten.put("verkoopprijs", "moet groter dan aankoopprijs");
			}
		}
		
		if(fouten.isEmpty()) {
			Artikel artikel = new Artikel(naam, aankoopprijs, verkoopprijs);
			artikelService.create(artikel);
			response.sendRedirect(
					response.encodeRedirectURL(
							String.format(REDIRECT_URL, request.getContextPath(), artikel.getId())));
		} else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}
