package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import be.vdab.valueobjects.Korting;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "artikels")
@DiscriminatorColumn(name = "soort")
public abstract class Artikel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	private String naam;
	private BigDecimal aankoopprijs;
	private BigDecimal verkoopprijs;
	@ElementCollection
	@CollectionTable(name="kortingen", joinColumns=@JoinColumn(name="artikelId"))
	@OrderBy("vanafAantal")
	private Set<Korting> kortingen;
	
	protected Artikel() {
	}

	public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
		setNaam(naam);
		setAankoopprijs(aankoopprijs);
		setVerkoopprijs(aankoopprijs, verkoopprijs);
		kortingen = new LinkedHashSet<>();
	}
	
	public static boolean isNaamValid(String naam) {
		return naam!=null && !naam.trim().isEmpty();
	}
	
	public void setNaam(String naam) {
		if(!isNaamValid(naam)) {
			throw new IllegalArgumentException();
		}
		this.naam = naam;
	}
	
	public static boolean isAankoopprijsValid(BigDecimal aankoopprijs) {
		return aankoopprijs != null && aankoopprijs.compareTo(BigDecimal.valueOf(0.01))>=0;
	}
	
	public void setAankoopprijs(BigDecimal aankoopprijs) {
		if(!isAankoopprijsValid(aankoopprijs)) {
			throw new IllegalArgumentException();
		}
		this.aankoopprijs = aankoopprijs;
	}
	
	public static boolean isVerkoopprijsValid(BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
		return verkoopprijs != null && verkoopprijs.compareTo(aankoopprijs)>=0;
	}
	
	public void setVerkoopprijs(BigDecimal aankoopprijs, BigDecimal verkoopprijs) {
		if(!isVerkoopprijsValid(aankoopprijs, verkoopprijs)) {
			throw new IllegalArgumentException();
		}
		this.verkoopprijs = verkoopprijs;
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public BigDecimal getAankoopprijs() {
		return aankoopprijs;
	}

	public BigDecimal getVerkoopprijs() {
		return verkoopprijs;
	}

	public BigDecimal getWinstPercentage() {
		return verkoopprijs.subtract(aankoopprijs).divide(aankoopprijs, 2, RoundingMode.HALF_UP)
				.multiply(BigDecimal.valueOf(100));
	}
	
	public Set<Korting> getKortingen(){
		return Collections.unmodifiableSet(kortingen);
	}
}
