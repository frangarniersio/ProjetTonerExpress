package ihm;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import metier.Cartouche;
import metier.Imprimante;
import ado.DAO_TonerExpress;
//import javax.swing.JOptionPane;

// Héritage de la classe JDialog
public class FenetreMenu extends JDialog implements ActionListener 
{
	// attributs privés de la fenêtre
	private Container contenu ;
	private JLabel lblTitre ;
	private JLabel lblStitre ;
	private JRadioButton rbAjoutImprimante;
	private JRadioButton rbAjoutCartouche;
	private JRadioButton rbListeImprimante;
	private JRadioButton rbListeCartouche;
	private JButton btnValider;
	private JButton btnAnnuler;
	private ButtonGroup groupe;  
	private Vector <Imprimante> lesImprimantes;
	private Vector <Cartouche> lesCartouches;

	
	public FenetreMenu( )
    {
            super(); // initialise les attributs d’une JDialog 
            
            creationComposants();
            
            ajoutComposantsConteneur();
            
            placementComposants();
            
            lesImprimantes=DAO_TonerExpress.getLesImprimantes();
            lesCartouches=DAO_TonerExpress.getLesCartouches();           
            
    }
	
	public Vector <Imprimante> getLesImprimantes()
	{
		return this.lesImprimantes;
	}
	
	private void creationComposants()
	{
		this.lblTitre = new JLabel ("Entreprise Toner Express");
        this.lblStitre = new JLabel ("------------------------------------");
        
        this.groupe=new ButtonGroup();
        this.rbAjoutImprimante=new JRadioButton("Ajouter une imprimante",true);  
        this.rbAjoutCartouche=new JRadioButton("Ajouter une cartouche",false);  
        this.rbListeImprimante=new JRadioButton("Lister les imprimantes",false);  
        this.rbListeCartouche=new JRadioButton("Lister les cartouches",false);  
        
        this.btnValider=new JButton("Valider");
        this.btnValider.addActionListener (this);
        this.btnAnnuler=new JButton("Annuler");
        this.btnAnnuler.addActionListener (this);
        
	}
	
	private void ajoutComposantsConteneur()
	{
		this.groupe.add(rbAjoutImprimante);
        this.groupe.add(rbAjoutCartouche);
        this.groupe.add(rbListeImprimante);
        this.groupe.add(rbListeCartouche);
        
        this.contenu = this.getContentPane( ) ;
        this.contenu.add (lblTitre ) ;
        this.contenu.add (lblStitre ) ;
        this.contenu.add (rbAjoutImprimante) ;
        this.contenu.add (rbAjoutCartouche) ;
        this.contenu.add (rbListeImprimante) ;
        this.contenu.add (rbListeCartouche) ;
        
        this.contenu.add (btnValider);
        this.contenu.add (btnAnnuler);
	}
	
	private void placementComposants()
	{
        
        this.contenu.setLayout(null);
        
        this.lblTitre.setBounds( 100, 20 , 200, 20 ) ;
        this.lblStitre.setBounds( 100, 25 , 200, 20 ) ;
        this.rbAjoutImprimante.setBounds(30,70,200,20);
        this.rbAjoutCartouche.setBounds(30,90,200,20);
        this.rbListeImprimante.setBounds(30,110,200,20);
        this.rbListeCartouche.setBounds(30,130,200,20);
        this.btnAnnuler.setBounds(30,160,100,20);
        this.btnValider.setBounds(210,160,100,20);
	}
	
	public void actionPerformed(ActionEvent evt)
	     {
		   	 // gestion des évènements
	         if (evt.getSource() == btnAnnuler )
	        	 System.exit(0); 
	         
	         if (evt.getSource() == btnValider )
	         {
	        	 if ( this.rbListeImprimante.isSelected())
	        	 {
	        		 // Le click sur le bouton Valider entraîne l'ouverture de la fenêtreChoixIimprimante
	        		 FenetreChoixImprimante fChoixImp= new FenetreChoixImprimante(lesImprimantes);
	        		 fChoixImp.setBounds(20,20,550,100);
	        		 fChoixImp.setTitle("Choix de l'imprimante");
	        		 fChoixImp.setModal(true);
	        		 fChoixImp.setLocation(100,100);
	        		 fChoixImp.setVisible(true);
	        	 }
	        	 if ( this.rbAjoutImprimante.isSelected())
	        	 {
	        		 // Le click sur le bouton Valider entraîne l'ouverture de la fenêtreChoixIimprimante
	        		 FenetreAjoutImprimante fAjoutImp= new FenetreAjoutImprimante(lesImprimantes, lesCartouches);
	        		 fAjoutImp.setBounds(20,20,650,500);
	        		 fAjoutImp.setTitle("Ajout d'une imprimante");
	        		 fAjoutImp.setModal(true);
	        		 fAjoutImp.setLocation(100,100);
	        		 fAjoutImp.setVisible(true);
		         }
	        	 if ( this.rbAjoutCartouche.isSelected())
	        	 {
	        		 // Le click sur le bouton Valider entraîne l'ouverture de la fenêtreChoixIimprimante
	        		 FenetreAjoutCartouche fAjoutImp= new FenetreAjoutCartouche(lesCartouches);
	        		 fAjoutImp.setBounds(20,20,650,300);
	        		 fAjoutImp.setTitle("Ajout d'une cartouche");
	        		 fAjoutImp.setModal(true);
	        		 fAjoutImp.setLocation(100,100);
	        		 fAjoutImp.setVisible(true);
		         }
	        	 if ( this.rbListeCartouche.isSelected())
	        	 {
	        		 // Le click sur le bouton Valider entraîne l'ouverture de la fenêtreChoixIimprimante
	        		 FenetreListeCartouche fChoixImp= new FenetreListeCartouche(lesCartouches);
	        		 fChoixImp.setBounds(20,20,500,400);
	        		 fChoixImp.setTitle("Liste des cartouches");
	        		 fChoixImp.setModal(true);
	        		 fChoixImp.setLocation(100,100);
	        		 fChoixImp.setVisible(true);
	        	 }
	         }
	     }
	   
}
