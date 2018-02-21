package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Artikelgroep;

public class ArtikelgroepRepository extends AbstractRepository{
	public List<Artikelgroep> findAll(){
		return getEntityManager()
				.createNamedQuery("Artikelgroep.findAll", Artikelgroep.class)
				.getResultList();
	}
	
	public Optional<Artikelgroep> read (long id){
		return Optional.ofNullable(getEntityManager().find(Artikelgroep.class, id));
	}
}
