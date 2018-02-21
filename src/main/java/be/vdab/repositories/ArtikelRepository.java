package be.vdab.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import be.vdab.entities.Artikel;

public class ArtikelRepository extends AbstractRepository {
	public Optional<Artikel> read(long id) {
		return Optional.ofNullable(getEntityManager().find(Artikel.class, id));
	}

	public void create(Artikel artikel) {
		getEntityManager().persist(artikel);
	}

	public Optional<List<Artikel>> findByNaam(String naam, int vanafRij, int aantalRijen) {
		try {
			return Optional
					.of(getEntityManager()
							.createNamedQuery("Artikel.findByNaam", Artikel.class)
							.setParameter("naam", '%' + naam + '%')
							.setFirstResult(vanafRij).setMaxResults(aantalRijen)
							.getResultList());
		} catch (NoResultException ex) {
			return Optional.empty();
		}
	}

	public void prijsverhoging(BigDecimal factor) {
		getEntityManager().createNamedQuery("Artikel.prijsverhoging").setParameter("factor", factor).executeUpdate();
	}
	
	public List<Artikel> findAll(){
		return getEntityManager().createNamedQuery("Artikel.findAll", Artikel.class).getResultList();
	}
	
	public List<Artikel> findAllMetGroep(){
		return getEntityManager()
					.createNamedQuery("Artikel.findAll", Artikel.class)
					.setHint("javax.persistence.loadgraph", getEntityManager().createEntityGraph(Artikel.MET_ARTIKELGROEP))
					.getResultList();
	}
}
