package be.vdab.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "artikelgroepen")
public class Artikelgroep implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToMany(mappedBy = "artikelgroep")
	@OrderBy("naam")
	private Set<Artikel> artikels;
	private String naam;

	public long getId() {
		return id;
	}

	public Set<Artikel> getArtikels() {
		return Collections.unmodifiableSet(artikels);
	}

	public String getNaam() {
		return naam;
	}

	public void add(Artikel artikel) {
		artikels.add(artikel);
		if (artikel.getArtikelgroep() != this) {
			artikel.setArtikelgroep(this);
		}
	}

	public void remove(Artikel artikel) {
		artikels.remove(artikel);
		if (artikel.getArtikelgroep() == this) {
			artikel.setArtikelgroep(null);
		}
	}

	public Artikelgroep(String naam) {
		this.naam = naam;
		artikels = new LinkedHashSet<>();
	}

	public Artikelgroep() {
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Artikelgroep)) {
			return false;
		}
		Artikelgroep andereArtikelgroep = (Artikelgroep) object;
		return naam.equalsIgnoreCase(andereArtikelgroep.naam);
	}

	@Override
	public int hashCode() {
		return naam.toUpperCase().hashCode();
	}
}
