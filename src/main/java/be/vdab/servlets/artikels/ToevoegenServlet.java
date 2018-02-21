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
import be.vdab.entities.Food;
import be.vdab.entities.NonFood;
import be.vdab.exceptions.ArtikelBestaatAlException;
import be.vdab.services.ArtikelService;
import be.vdab.services.ArtikelgroepService;
import be.vdab.util.StringUtils;

@WebServlet("/artikels/toevoegen.htm")
public class ToevoegenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/artikels/toevoegen.jsp";
	private static final String REDIRECT_URL = "%s/artikels/zoeken.htm?id=%d";
	private final transient ArtikelService artikelService = new ArtikelService();
	private final transient ArtikelgroepService artikelgroepService = 
			new ArtikelgroepService();
	private Artikel artikel;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("artikelgroepen", artikelgroepService.findAll());
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> fouten = new HashMap<>();
		String naam = request.getParameter("naam");
		if (!Artikel.isNaamValid(naam)) {
			fouten.put("naam", "verplicht");
		}
		String aankoopprijsString = request.getParameter("aankoopprijs");
		BigDecimal aankoopprijs = null;
		if (!StringUtils.isBigDecimal(aankoopprijsString)) {
			fouten.put("aankoopprijs", "verplicht");
		} else {
			aankoopprijs = new BigDecimal(aankoopprijsString);
			if (!Artikel.isAankoopprijsValid(aankoopprijs)) {
				fouten.put("aankoopprijs", "verplicht");
			}
		}

		String verkoopprijsString = request.getParameter("verkoopprijs");
		BigDecimal verkoopprijs = null;
		if (!StringUtils.isBigDecimal(verkoopprijsString)) {
			fouten.put("verkoopprijs", "verplicht");
		} else {
			verkoopprijs = new BigDecimal(verkoopprijsString);
			if (!(Artikel.isAankoopprijsValid(aankoopprijs)
					&& Artikel.isVerkoopprijsValid(aankoopprijs, verkoopprijs))) {
				fouten.put("verkoopprijs", "moet groter dan aankoopprijs");
			}
		}
		
		String artikelgroepIdString = request.getParameter("artikelgroepen");
		if(artikelgroepIdString == null) {
			fouten.put("artikelgroepen", "verplicht");
		}
		else if(!StringUtils.isLong(artikelgroepIdString)) {
			fouten.put("artikelgroepen", "ongeldig");
		}
		else if (!artikelgroepService.read(Long.parseLong(artikelgroepIdString)).isPresent()) {
			fouten.put("artikelgroepen", "ongeldig");
		}

		if (fouten.isEmpty()) {
			String artikelType = request.getParameter("artikeltype");
			if (artikelType != null && !artikelType.trim().isEmpty()) {
				if (artikelType.equals("food")) {
					String houdbaarheid = request.getParameter("houdbaarheid");
					if (StringUtils.isInt(houdbaarheid)) {
						artikel = new Food(naam, aankoopprijs, verkoopprijs, Integer.parseInt(houdbaarheid));
					} else {
						fouten.put("houdbaarheid", "moet een nummer zijn");
					}

				} else if (artikelType.equals("nonfood")) {
					String garantie = request.getParameter("garantie");
					if (StringUtils.isInt(garantie)) {
						artikel = new NonFood(naam, aankoopprijs, verkoopprijs, Integer.parseInt(garantie));
					} else {
						fouten.put("garantie", "moet een nummer zijn");
					}
				} else {
					fouten.put("artikeltype", "verplicht");
				}
			}
			else {
				fouten.put("artikeltype", "verplicht");
			}
			if (artikel != null) {
				artikelgroepService
					.read(Long.parseLong(artikelgroepIdString))
					.ifPresent(artikelgroep->artikel.setArtikelgroep(artikelgroep));
				try {
					artikelService.create(artikel);
					response.sendRedirect(response
							.encodeRedirectURL(String.format(REDIRECT_URL, request.getContextPath(), artikel.getId())));
				} catch (ArtikelBestaatAlException ex){
					fouten.put("naam", "bestaat al");
				}
				
				
			}
			

		}
		if (!fouten.isEmpty()) {
			request.setAttribute("fouten", fouten);
			request.setAttribute("artikelgroepen", artikelgroepService.findAll());
			request.getRequestDispatcher(VIEW).forward(request, response);
		}

	}
}
