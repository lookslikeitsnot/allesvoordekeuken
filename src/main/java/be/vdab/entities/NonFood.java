package be.vdab.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NF")
public class NonFood extends Artikel {
	private static final long serialVersionUID = 1L;
	private int garantie;

	public NonFood(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int garantie) {
		super(naam, aankoopprijs, verkoopprijs);
		this.garantie = garantie;
	}

	protected NonFood() {
	}

	public int getGarantie() {
		return garantie;
	}

	public static boolean isGarantieValid(int garantie) {
		return garantie >= 0;
	}

	public void setGarantie(int garantie) {
		if (!isGarantieValid(garantie)) {
			throw new IllegalArgumentException();
		}
		this.garantie = garantie;
	}
}
