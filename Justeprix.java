import java.util.Random;

public class Justeprix {
	Random generator = new Random();
	private int prixMin = 0, prixMax = 10000; // Définition de l'interval du prix (inclus - inclus)
	private int prix; // prixMin<prix<prixMax
	private int tentatives;
	private static int tentativesMax = 50;
	
	public Justeprix() {
		tentatives = 0;
		prix = prixMin + generator.nextInt(prixMax-prixMin+1);
	}
	public Justeprix(int prix2) {
		tentatives = 0;	
		if(prixMin <= prix2 && prix2 <= prixMax)
			prix = prix2;
		else
			prix = prixMin + generator.nextInt(prixMax-prixMin+1);
	}
	
	//Propose un entier, et renvoi 0 si <prix / 1 si =prix / 2 si >prix
	int Essayer(int proposition) {
		tentatives++;
		if(proposition<prix)
			return 0;
		else if(proposition==prix)
			return 1;
		else
			return 2;
	}
	/**
	 * @return the tentatives
	 */
	public int getTentatives() {
		return tentatives;
	}
	/**
	 * @return the tentatives
	 */
	public int getTentativesMax() {
		return tentativesMax;
	}
	/**
	 * @return the prixMin
	 */
	public  int getPrixMin() {
		return prixMin;
	}
	/**
	 * @return the prixMax
	 */
	public  int getPrixMax() {
		return prixMax;
	}
	/**
	 * @return the prix
	 */
	public int getPrix() {
		return prix;
	}
	/**
	 * @param prixMin the prixMin to set
	 */
	public void setPrixMin(int prixMin) {
		this.prixMin = prixMin;
	}
	/**
	 * @param prixMax the prixMax to set
	 */
	public void setPrixMax(int prixMax) {
		this.prixMax = prixMax;
	}

	
	
}
