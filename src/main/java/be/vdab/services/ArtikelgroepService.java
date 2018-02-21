package be.vdab.services;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Artikelgroep;
import be.vdab.repositories.ArtikelgroepRepository;

public class ArtikelgroepService {
	private final ArtikelgroepRepository artikelgroepRepository = 
			new ArtikelgroepRepository();
	
	public List<Artikelgroep> findAll(){
		return artikelgroepRepository.findAll();
	}
	
	public Optional<Artikelgroep> read(long id){
		return artikelgroepRepository.read(id);
	}
}
