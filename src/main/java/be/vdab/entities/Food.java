package be.vdab.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
public class Food extends Artikel {
	private static final long serialVersionUID = 1L;
	private int houdbaarheid;

	public Food(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int houdbaarheid) {
		super(naam, aankoopprijs, verkoopprijs);
		this.houdbaarheid = houdbaarheid;
	}

	protected Food() {
	}

	public static boolean isHoudbaarheidValid(int houdbaarheid) {
		return houdbaarheid >= 1;
	}

	public int getHoudbaarheid() {
		return houdbaarheid;
	}

	public void setHoudbaarheid(int houdbaarheid) {
		if (!isHoudbaarheidValid(houdbaarheid)) {
			throw new IllegalArgumentException();
		}
		this.houdbaarheid = houdbaarheid;
	}
}
