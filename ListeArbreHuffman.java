public class ListeArbreHuffman {
	
	private AbrHuffman tete;
	private ListeArbreHuffman reste;
	private boolean vide;
	
	public ListeArbreHuffman() {
		this.vide = true;
	}
	
	public ListeArbreHuffman(AbrHuffman a, ListeArbreHuffman l) {
		this.tete = a;
		this.reste = l;
		this.vide = false;
	}
	
	public AbrHuffman tete(){
		return this.tete;
	}
	
	public ListeArbreHuffman reste(){
		return this.reste;
	}
	
	public boolean vide(){
		return this.vide;
	}
	
	public ListeArbreHuffman prefixer(AbrHuffman a){
		ListeArbreHuffman lh = new ListeArbreHuffman(a, this);
		return lh;
	}
	
	public ListeArbreHuffman insererOrd(AbrHuffman a) {
		if(!vide()) {
			return this.reste().insererOrd(a).prefixer(this.tete());
		}
		return this.prefixer(a);
	}
	
	public boolean appartenance(char c) {
		if (!vide()) {
			if (tete().info().getCaractere() == c) {
				return true;
			}
			else {
				return reste().appartenance(c);
			}
		}
		return false;
	}
	
	 public void afficher() {
			tete.affiche(0);
			if(reste !=null && !reste.vide())reste.afficher();
		}
	 
}