package be.vdab.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import be.vdab.entities.Artikel;
import be.vdab.exceptions.ArtikelBestaatAlException;
import be.vdab.repositories.ArtikelRepository;

public class ArtikelService extends AbstractService {
	private final ArtikelRepository artikelRepository = new ArtikelRepository();
//	private final ArtikelgroepRepository artikelgroepRepository 
//		= new ArtikelgroepRepository();
	

	public Optional<Artikel> read(long id) {
		return artikelRepository.read(id);
	}

	public void create(Artikel artikel) {
		if(artikelRepository.findAll().contains(artikel)) {
			throw new ArtikelBestaatAlException();
		}
		beginTransaction();
		try {
			artikelRepository.create(artikel);
			commit();
		} catch (PersistenceException ex) {
			rollback();
			throw ex;
		}
	}

	public List<Artikel> findByNaam(String naam, int vanafRij, int aantalRijen) {
		return artikelRepository.findByNaam(naam, vanafRij, aantalRijen).get();
	}

	public void prijsvehoging(BigDecimal percentage) {
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
		try {
			beginTransaction();
			artikelRepository.prijsverhoging(factor);
			commit();
		} catch (PersistenceException ex) {
			rollback();
			throw ex;
		}
	}
	public List<Artikel> findAll(){
		return artikelRepository.findAll();
	}
	public List<Artikel> findAllMetGroep(){
		return artikelRepository.findAllMetGroep();
	}
}
