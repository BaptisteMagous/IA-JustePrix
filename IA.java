
public class IA {
	int prixMin, prixMax; //Correspond aux information que l'IA a sur le prix
	int prixMinFixe, prixMaxFixe; //Correspond aux prix max et min relatif � la partie
	int prix; //Correspond � la proposition
	double fitness;
	Justeprix jeu;
	
	double cell1, cell2, cell3;
	double W11, W12, W13, W21, W22, W23, W1, W2, W3;
	
	static double Wmin = -10, Wmax = 10; //Le poids min et max des synapses
	
	//////////////////////////////////////////////////////////////
	//Constructeur
	////////////////////////////////////////////////////////////
	public IA() { //Cr�er une IA random
		W11 = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W12 = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W13 = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W21 = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W22 = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W23 = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W1  = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W2  = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
		W3  = -1+2*Math.random(); //Cr�er un double al�atoire entre [-1,1[
	}
	
	public IA(IA Parent1, IA Parent2) {//Creer une IA � partir de deux parents
		W11 = IA.Accoupler(Parent1.getW11(),Parent2.getW11()); //Cr�er un double al�atoire � partir des deux parents
		W12 = IA.Accoupler(Parent1.getW12(),Parent2.getW12()); //Cr�er un double al�atoire � partir des deux parents
		W13 = IA.Accoupler(Parent1.getW13(),Parent2.getW13()); //Cr�er un double al�atoire � partir des deux parents
		W21 = IA.Accoupler(Parent1.getW21(),Parent2.getW21()); //Cr�er un double al�atoire � partir des deux parents
		W22 = IA.Accoupler(Parent1.getW22(),Parent2.getW22()); //Cr�er un double al�atoire � partir des deux parents
		W23 = IA.Accoupler(Parent1.getW23(),Parent2.getW23()); //Cr�er un double al�atoire � partir des deux parents
		W1  = IA.Accoupler(Parent1.getW1() ,Parent2.getW1() ); //Cr�er un double al�atoire � partir des deux parents
		W2  = IA.Accoupler(Parent1.getW2() ,Parent2.getW2() ); //Cr�er un double al�atoire � partir des deux parents
		W3  = IA.Accoupler(Parent1.getW3() ,Parent2.getW3() ); //Cr�er un double al�atoire � partir des deux parents
		
	}
	/////////
	//Sous-fonctions
	/////////
	private static double Accoupler(double W1, double W2) {
		double mutation = 0.1; //On met un peu d'al�atoire
		double moyenne = (W1+W2)/2; 							
		double difference = Math.abs(W1-W2)+mutation;//parents differents = petit different //parents identiques = petit identique
		return encadrerPoids(moyenne -difference + 2*(difference)*Math.random()); //W = moyenne + random[-difference;+difference[
	}
	private static double encadrerPoids(double poids) { //On verifie que le poids ne d�passe pas les bornes autoris�e
		if (poids>Wmax)
			return Wmax;
		else if (poids<Wmin)
			return Wmin;
		else
			return poids;
	}
	
	///////////////////////////////////////////////////////
	// Fonctions Parties
	//////////////////////////////////////////////////////
	public void initialiserPartie() {
		jeu = new Justeprix();
		prixMin = jeu.getPrixMin();
		prixMax = jeu.getPrixMax();
		prixMaxFixe = prixMax;
		prixMinFixe = prixMin;
	}
	public void initialiserPartie(int prix) {
		jeu = new Justeprix(prix);
		prixMin = jeu.getPrixMin();
		prixMax = jeu.getPrixMax();
		prixMaxFixe = prixMax;
		prixMinFixe = prixMin;
	}
	public void initialiserPartie(int prix, int prixMin, int prixMax) {
		jeu = new Justeprix(prix);
		jeu.setPrixMax(prixMax);
		jeu.setPrixMin(prixMin);
		this.prixMin = prixMin;
		this.prixMax = prixMax;
		this.prixMaxFixe = prixMax;
		this.prixMinFixe = prixMin;
	}
	
	public void jouer() {						
		prix = reflechir(); 				//On predit un prix
		int resultat = jeu.Essayer(prix);	//On demande si il est juste
		
		switch (resultat){
			case 0:							//Le vrai prix est inferieur � celui qu'on a pr�dit
				prixMin = prix;	
				break;					
			case 2:							//Le vrai prix est sup�rieur � celui qu'on a pr�dit
				prixMax = prix;
				break;
		}
//DEBUG		System.out.println("J'ai essay� "+prix+"�, on m'a r�pondut "+resultat);
		if(resultat==1 || jeu.getTentatives()==jeu.getTentativesMax()) //On a juste ou on a perdu (trop de tentatives)
			resultat(prix);
		else
			jouer(); 		//UNE FONCTION RECURSIVE !!!!!!!!!!!!
	}	
	
	public void jouerEnLive() {	//Decrit ce que l'IA fait		
		prix = reflechir(); 				//On predit un prix
		int resultat = jeu.Essayer(prix);	//On demande si il est juste
		System.out.println("Est ce que le prix est de "+prix+"� ?");
		switch (resultat){
			case 0:							//Le vrai prix est sup�rieur � celui qu'on a pr�dit
				prixMin = prix;	
				System.out.println("R�ponse : Plus cher");
				break;					
			case 2:							//Le vrai prix est inferieur � celui qu'on a pr�dit
				prixMax = prix;
				System.out.println("R�ponse : Moins cher");
				break;
		}
//DEBUG		System.out.println("J'ai essay� "+prix+"�, on m'a r�pondut "+resultat);
		if(resultat==1 || jeu.getTentatives()==jeu.getTentativesMax()) //On a juste ou on a perdu (trop de tentatives)
			{resultat(prix);
			System.out.println("Le jeu est termin� ! Ton score : "+fitness);}
		else
			jouerEnLive(); 		//UNE FONCTION RECURSIVE !!!!!!!!!!!!
	}	

	
	private int reflechir() { //On calcul toutes les valeurs des cellules ainsi la valeur de sortie
		cell1 = prixMin*W11 + prixMax*W21;
		cell2 = prixMin*W12 + prixMax*W22;
		cell3 = prixMin*W13 + prixMax*W23;
		return redimensionner(cell1*W1 + cell2*W2 + cell3*W3);
	}
	////////////////
	//Sous-fonction
	///////////////
	private int redimensionner(double activation) { //Un operation super complexe pour retomber sur un prix compris entre le prix max autoris� et le prix min
		double activationMax = 3*Wmax*Wmax*(prixMinFixe+prixMaxFixe); //Dans le pire des cas, l'activation sera � tant
		double activationMin = 3*Wmin*Wmax*(prixMinFixe+prixMaxFixe); //Dans le meilleur des cas ...
		
		return (int) 	(prixMax - (activationMax-activation)*(prixMax-prixMin)
										/(activationMax-activationMin)
						);
	}
	
	private void resultat(int prix) {
		//Le score se fait par : fitness = tentatives + distanceDuVraiPrix(�gal � 0 si on a gagn�)
		//!\\ Le score de fitness est incr�ment� sur le precedent !
		this.fitness += jeu.getTentatives()+Math.abs(prix-jeu.getPrix()); 
	}
	
	
	////////////////////////////////////////////////////
	//Accesseur Mutateurs
	///////////////////////////////////////////
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public void setFitnessToWorst(int multiplicateur) {
		this.fitness = multiplicateur*(50+prixMaxFixe-prixMinFixe);
	}

	public double getW11() {
		return W11;
	}

	public void setW11(double w11) {
		W11 = w11;
	}

	public double getW12() {
		return W12;
	}

	public void setW12(double w12) {
		W12 = w12;
	}

	public double getW13() {
		return W13;
	}

	public void setW13(double w13) {
		W13 = w13;
	}

	public double getW21() {
		return W21;
	}

	public void setW21(double w21) {
		W21 = w21;
	}

	public double getW22() {
		return W22;
	}

	public void setW22(double w22) {
		W22 = w22;
	}

	public double getW23() {
		return W23;
	}

	public void setW23(double w23) {
		W23 = w23;
	}

	public double getW1() {
		return W1;
	}

	public void setW1(double w1) {
		W1 = w1;
	}

	public double getW2() {
		return W2;
	}

	public void setW2(double w2) {
		W2 = w2;
	}

	public double getW3() {
		return W3;
	}

	public void setW3(double w3) {
		W3 = w3;
	}
	
}
