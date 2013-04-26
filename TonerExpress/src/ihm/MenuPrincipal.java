package ihm;

public class MenuPrincipal {

	// Point d'entrée du programme
	public static void main(String[] args) 
	{
        FenetreMenu f= new FenetreMenu();
        f.setBounds(20,20,350,300);
        f.setVisible(true);
        f.setTitle("Toner-Express");
	}
}
