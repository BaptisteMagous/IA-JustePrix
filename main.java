

public class main {

	public static void main(String[] args) {
		System.out.println("Debut");
		Couveuse poule = new Couveuse();
		for(int generation = 1; generation < 1000; generation++) {
			poule.couver(50, 10, 10);
		}
		System.out.println(poule.bestToString());
		poule.championEnLive();
		System.out.println("Fin");
	}

}
