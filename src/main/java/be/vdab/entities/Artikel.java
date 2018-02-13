package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "artikels")
public class Artikel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	private String naam;
	private BigDecimal aankoopprijs;
	private BigDecimal verkoopprijs;

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
}
