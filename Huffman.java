import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Huffman {
	public static Map<Character, String> huffmanCodes= new HashMap<>();//pour stocker les caractères et leurs codes 
	public static Couple[] tabCouple;
	public static AbrHuffman arbrehuffman;//pour stocker l'arbre huffman 
	public static String texteEncoder;// pour stocker le texte encoder
	public static String texte;//pour stocker le texte original
	
	public static void main(String[] args) {
		menu();
	}
	
	public static void menu() {
		boolean boucle = true;
		boolean encoder = false;
		Scanner sc = new Scanner(System.in);
		while(boucle){
			int choix;
			System.out.println("Veuillez selectionner une action à effectuer :");
			System.out.println("-tapper 1 pour encoder un fichier");
			System.out.println("-tapper 2 pour afficher l'abre huffman du fichier encoder");
			System.out.println("-tapper 3 pour afficher les caractères et leurs code binaire");
			System.out.println("-tapper 4 pour decoder le code du fichier encoder précedemment");
			System.out.println("-tapper 5 pour quitter l'application");
			choix=sc.nextInt();
			
			switch(choix){
			case 1:
				System.out.println("Vous avez choisi :"+choix);
				System.out.println("Veuillez ecrire le chemin de votre fichier a encoder :");
				String chemin=sc.next();
				texte=lireFicher(chemin);
				System.out.println("Procedure d'encodage...");
				encoder(texte);
				encoder=true;
				break;
			case 2:
				System.out.println("Vous avez choisi :"+choix);
				if(encoder) {
					System.out.println("Voici l'arbre de Huffman généré :");
					arbrehuffman.affiche(0);
				}
				else
					System.out.println("Veuillez d'abord générer l'encodage du fichier pour generer l'arbre");
				break;
			case 3:
				if(encoder) {
				System.out.println("Vous avez choisi :"+choix);
				afficherCodageCaractères();
				}
				else
					System.out.println("Veuillez d'abord encoder un fichier");
				break;
			case 4:
				System.out.println("Vous avez choisi :"+choix);
				if(encoder) {
				System.out.println("Texte decoder :");
				System.out.println(decoder());
				}
				else
					System.out.println("Veuillez d'abord encoder un fichier");
				break;
			case 5:
				System.out.println("Vous avez choisi :"+choix);
				System.out.println("A bientot !");
				sc.close();
				boucle=false;
				break;
			default:
				System.out.println("Veuillez rentrer un numero valide");
			}
		}
	}
	
	public static void afficherCodageCaractères() {
		System.out.println("Voici les caractères et leurs codes binaire :");
		for (Map.Entry mapentry : huffmanCodes.entrySet()) {
			 System.out.println("caractère :"+mapentry.getKey() 
			 + " | code: " + mapentry.getValue());
		}
	}
	
	public static String lireFicher(String cheminTexte) {
		try {
			String texte = new String(Files.readAllBytes(Paths.get(cheminTexte)));
			return texte;
		}catch(IOException e) {
			System.out.println("Le fichier est inexistant");
			return null;
		}
	}
	
	public static void encoder(String texte) {
		try {
		ListeArbreHuffman test= creerListeHuffman(texte);//crere la liste d'abre huffman par rapport à un mot
		System.out.println("Liste des caractères dans le texte et leurs frequence:");
		test.afficher();
		arbrehuffman=algoHuffman(test);
		System.out.println("Arbre Huffman généré !");
		genererEncodage(arbrehuffman, "");//attention bien donner une string vide!!! car pas de code au debut et fonction récursive
		System.out.println("Code généré du fichier texte :");
		texteEncoder=getTexteEncoder(texte);
		System.out.println(texteEncoder);//affiche le texte encoder
		}catch(Exception e) {
			System.out.println("Il n'y a pas de texte ou le fichier texte est vide");
		}
	}
	
	public static String decoder() {//fonction pour decoder un texte encoder (stocker dans texteEncoder) par rapport a l'arbre huffman(arbrehuffman)
		try {
		StringBuilder sb = new StringBuilder();
		AbrHuffman current = arbrehuffman;
		for(char character : texteEncoder.toCharArray()) {
			if(character =='0')
				current = current.filsGauche();
			else
				current = current.filsDroit();
			
			if(current.info().getCaractere()!='\0') {
				sb.append(current.info().getCaractere());
				current = arbrehuffman;
			}
		}
		return sb.toString();
		}catch(Exception e) {
			System.out.println("Impossible de decodé");
			return null;
		}
	}
	
	
	public static AbrHuffman algoHuffman(ListeArbreHuffman l) {
		if(!l.reste().vide()) {
			AbrHuffman z = new AbrHuffman();
			AbrHuffman x = l.tete();
			z.setFilsGauche(x);
			AbrHuffman y = l.reste().tete();
			z.setFilsDroit(y);//senser etre setFilsDroit de x
			Couple a= new Couple(x.info().getFrequence()+ y.info().getFrequence());
			//
			z.setInfo(a);
			return algoHuffman(l.reste().reste().insererOrd(z));
		}
		else
			return l.tete();
	}
	
	public static ListeArbreHuffman creerListeHuffman(String mot) {//pour retourner la listeHuffman pour la donner a algoHuffman

        try {
            tabCouple = trierListe(creerCouples(mot));
            int taille = tabCouple.length;
            ListeArbreHuffman reste = new ListeArbreHuffman();
            ListeArbreHuffman l = reste;
            AbrHuffman vide = new AbrHuffman();
            for (int i= 0; i < taille; i ++) {
                if (!l.appartenance(tabCouple[i].getCaractere()) && tabCouple[i].getCaractere()!='\0'){
                    AbrHuffman a = new AbrHuffman(tabCouple[i], vide, vide);
                    l = new ListeArbreHuffman(a, reste);
                    reste = l;
                }
            }
            return reste;
        }
        catch(Exception e){
            System.out.println("le String est null, impossible de créer une liste");
        }
        return null;
    }
	
	public static int compterOccurrences(String maChaine, char recherche){
		int nb = 0;
		for (int i=0; i < maChaine.length(); i++){
			if (maChaine.charAt(i) == recherche)
				nb++;
		}
		return nb;
	}
	
	public static Couple[] creerCouples(String text) {
		Couple[] a=new Couple[text.length()];
		for(int j=0; j<a.length;j++) {
			a[j]= new Couple();
		}
		int i=0;
		for(char character : text.toCharArray()) {
			if(!check(a, character)) {
				int nbr;
				nbr = compterOccurrences(text, character);
				a[i]=new Couple(character, nbr);
				i++;
			}
		}
		return a;
	}
	//vérifie si un couple n'est pas déjà présent
	public static boolean check(Couple[] tab, char val) {
        boolean b = false;
    
        for(Couple i : tab){
            if(i.getCaractere() == val){
                b = true;
                break;
            }
        }
        return b;
    }
	
	//tri bulles du tableau de couples
	public static Couple[] trierListe(Couple[] liste) {
		for (int i = 0; i < liste.length; i++) {
			for (int j = i + 1; j < liste.length; j++) {
				if (liste[i].getFrequence() < liste[j].getFrequence()) {
					Couple p = liste[i];
					liste[i] = liste[j];
					liste[j] = p;
				}
			}
		}
		return liste;
	}

	public static void genererEncodage(AbrHuffman a, String code) {//génère l'encodage de chaque caractères
		if(a.info().getCaractere()!='\0') {
			huffmanCodes.put(a.info().getCaractere(), code);
			//System.out.println( a.info()); //pour afficher les caractères et leurs codes
			//System.out.println(code);
			return;
		}
		genererEncodage(a.filsGauche(), code.concat("0"));
		genererEncodage(a.filsDroit(), code.concat("1"));
	}
	public static String getTexteEncoder(String texte) {//retourne le texte encoder
		StringBuilder sb = new StringBuilder();
		for(char character : texte.toCharArray()) {
			sb.append(huffmanCodes.get(character));
		}
		return sb.toString();
	}
}
