
public class Couple {
	public char caractere;
	public int frequence;
	
	public Couple(char c, int f) {
		this.caractere=c;
		this.frequence=f;
	}
	public char getCaractere() {
		return caractere;
	}
	public int getFrequence() {
		return frequence;
	}
	public Couple() {
		// TODO Auto-generated constructor stub
		this.caractere='\0';
	}
	public Couple(int f) {
		frequence=f;
	}
	
	public void setCaractere(char caractere) {
		this.caractere = caractere;
	}
	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(caractere!='\0')
		return ("("+caractere +" : "+frequence + ") ");
		else
			return("="+frequence);
	}
}
