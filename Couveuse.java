import java.util.concurrent.ThreadLocalRandom;
public class Couveuse {
	/*Une couveuse va s'occuper de :
	- g�nerer plusieurs IA
	- les tester
	- les reproduires
	- les retester
	- les reproduires 
	... pendant un certain nombre de g�n�ration
	et va renvoyer les X meilleurs IA
	*/
	static int nombreIA = 50;
	public IA[] adultes = new IA[nombreIA];
	private IA[] enfants = new IA[nombreIA];
	
	public Couveuse() {
		for(int j=0; j<nombreIA; j++) { //On rempli le tableau d'IA avec des IA
			adultes[j] = new IA();
		}
	}
	
	public void couver(int nombreTests, int nombreElus, int remplirAleatoire) {
		///////////////////////////////////////
		//Premi�re �tape : tester nos sujets
		//Faire jouer nos IA durant X tours
//DEBUG	System.out.println(nombreTests);
		for(IA joueur : adultes) {
//DEBUG		System.out.println("\n\nNouvelle IA � test\n");
			for(int i = nombreTests; i>0; i--) {
				joueur.initialiserPartie();
				joueur.jouer();
				//DEBUG			System.out.println("L'IA a actuellement un score de "+joueur.getFitness()+". Plus que "+i+" tests !");
			}
			//DEBUG			System.out.println("Score final : "+joueur.getFitness());
			//DEBUG		System.out.println("\n\n\n");
		}
//DEBUG		System.out.println("Fini !!!!");
		int[] indexParents = new int[nombreElus];
		
		////////////////////////////////////////////
		//Deuxieme etape : recuperer les X meilleurs
		for(int nombreIaGarde = 0; nombreIaGarde < nombreElus; nombreIaGarde++) {
			
			int index = 0;
			double maxFitness = adultes[0].getFitness(); //Par d�faut, le premier est le meilleur, jusqu'a preuve du contraire
			
			for(int i = 0; i<nombreIA; i++) { //On cherche le meilleur element
				if (adultes[i].getFitness()<maxFitness){ 	//Fitness faible = bon     2 < 3; le 2 est une meilleur fitness 
					maxFitness = adultes[i].getFitness();
					index = i;
				}
			}
			//DEBUG			System.out.println("l'IA num�ro "+(nombreIaGarde+1) + " a une fitness de "+ (adultes[index].getFitness())/(nombreTests));
			indexParents[nombreIaGarde]=index;
			adultes[index].setFitnessToWorst(nombreTests);		//On mets le score du gagnant au pire possible pour ne pas le choisir � nouveau
				
		}

		///////////////////////////////////////////////////////////////////////////////
		//Troisieme etape : reproduire les meilleurs entre eux pour avoir des bebes
		for(int i = 0; i<nombreIA; i++) { //On parcour le tableau d'enfant
			
			if(i<(nombreIA-remplirAleatoire)) { //Si on est pas encore arriv� aux al�atoires, on accouple des IA
				int random1 = ThreadLocalRandom.current().nextInt(0, nombreElus); //On genere deux indexs al�atoires dans la liste des elues
				int random2 = ThreadLocalRandom.current().nextInt(0, nombreElus); //On genere deux indexs al�atoires dans la liste des elues
//DEBUG			System.out.println(random1 + " / "+ random2);
				enfants[i] = new IA(   adultes[indexParents[random1]]  ,  adultes[indexParents[random2]]   ); //On cr�e un b�b� � partir des deux parents al�atoire
			}else
				enfants[i] = new IA(); //IA al�atoire
		}
		
		///////////////////////////////////////////////////////////////////////////
		//Quatrieme etape : le cercle de la vie, la boucle est boucl�e
		adultes = enfants; //C'est si beau
		//DEBUG		System.out.println("Nouvelle g�n�ration");
	}
	
	public String bestToString() { //Renvoi la fitness du meilleur joueur actuel
		int index = 0;
		double maxFitness = adultes[0].getFitness();
		for(int i = 0; i<nombreIA; i++) { //On cherche le meilleur element
			if (adultes[i].getFitness()<maxFitness){ 	//Fitness faible = bon     2 < 3; le 2 est une meilleur fitness 
				maxFitness = adultes[i].getFitness();
				index = i;
			}
		}
		adultes[index].initialiserPartie();
		adultes[index].jouer();
		return 	("Le meilleur est � "+(adultes[index].getFitness()));
	}
	
	public void championEnLive() { //Fait jouer le meilleur joueur en live (on voit ses propositions et les r�ponses
		int index = 0;
		double maxFitness = adultes[0].getFitness();
		for(int i = 0; i<nombreIA; i++) { //On cherche le meilleur element
			if (adultes[i].getFitness()<maxFitness){ 	//Fitness faible = bon     2 < 3; le 2 est une meilleur fitness 
				maxFitness = adultes[i].getFitness();
				index = i;
			}
		}
		adultes[index].initialiserPartie();
		adultes[index].jouerEnLive();
	}
}