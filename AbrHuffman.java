
public class AbrHuffman{
	private Couple couple;
	private AbrHuffman filsGauche;
	private AbrHuffman filsDroit;
	private boolean vide=true;

	
	public AbrHuffman() {//cas ou on veut créé un AbrHuffman binaire vide
	}
	public AbrHuffman(Couple val, AbrHuffman droit, AbrHuffman gauche) {//AbrHuffman binaire avec ses fils droit et gauche
		this.couple=val;
		this.vide=false;
		this.filsDroit=droit;
		this.filsGauche=gauche;
	}
	public AbrHuffman(Couple val) {//cas ou on veut créé un AbrHuffman binaire sans fils
		this.couple=val;
		this.vide=false;
		this.filsDroit=new AbrHuffman();
		this.filsGauche=new AbrHuffman();
	}
	//
	public AbrHuffman(AbrHuffman gauche, AbrHuffman droit) {
		this.couple.setFrequence(gauche.info().getFrequence()+ droit.info().getFrequence());
		this.couple.setCaractere('\0');
		this.filsDroit=droit;
		this.filsGauche=gauche;
		this.vide=false;
	}
	public AbrHuffman filsDroit() {
		return this.filsDroit;
	}
	public AbrHuffman filsGauche() {
		return this.filsGauche;
	}
	public boolean vide() {
		return this.vide;
	}
	public Couple info() {
		return this.couple;
	}
	public void afficherInfixe() {
		if(!this.vide()) {
			this.filsGauche().afficherInfixe();
			System.out.print(this.info()+ ", ");
			this.filsDroit().afficherInfixe();
		}
	}
	public void aficherPrefixe() {
		if(!this.vide()) {
			System.out.print(this.info()+ ", ");
			this.filsGauche().aficherPrefixe();
			this.filsDroit().aficherPrefixe();
		}
	}
	public void affiche(int decalage) {
		if(!vide()) {
			filsDroit().affiche(decalage +1);
			for(int i=0; i<decalage; i++) {
				System.out.print("    ");
			}
				System.out.println(info());
			
			filsGauche().affiche(decalage+1);
		}
    }
	
	public void setFilsGauche(AbrHuffman filsGauche) {
		this.filsGauche = filsGauche;
		this.vide=false;
	}
	public void setFilsDroit(AbrHuffman filsDroit) {
		this.filsDroit = filsDroit;
		this.vide=false;
	}
	public void setVide(boolean a) {
		this.vide=a;
	}
	public void setInfo(Couple a) {
		this.couple=a;
	}
}
