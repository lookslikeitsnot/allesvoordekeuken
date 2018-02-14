package be.vdab.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;

import be.vdab.entities.Artikel;

public class ArtikelRepository {
	public Optional<Artikel> read(long id, EntityManager entityManager) {
		return Optional.ofNullable(entityManager.find(Artikel.class, id));
	}
	
	public void create(Artikel artikel, EntityManager entityManager) {
		entityManager.persist(artikel);
	}
}
