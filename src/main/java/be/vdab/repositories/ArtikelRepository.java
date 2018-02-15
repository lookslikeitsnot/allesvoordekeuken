package be.vdab.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.entities.Artikel;

public class ArtikelRepository extends AbstractRepository{
	public Optional<Artikel> read(long id) {
		return Optional.ofNullable(getEntityManager().find(Artikel.class, id));
	}
	
	public void create(Artikel artikel) {
		getEntityManager().persist(artikel);
	}
	
	public List<Artikel> findByNaam(String naam, int vanafRij, int aantalRijen){
		return getEntityManager()
				.createNamedQuery("Artikel.findByNaam", Artikel.class)
				.setParameter("naam", '%'+naam+'%')
				.setFirstResult(vanafRij)
				.setMaxResults(aantalRijen)
				.getResultList();
	}
}
