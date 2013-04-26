package ihm;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import metier.Cartouche;
import ado.DAO_TonerExpress;
//import javax.swing.ComboBoxModel;

public class FenetreListeCartouche extends JDialog implements ActionListener{
	
	private Container contenu ;
	private JButton btnValider;
	private JButton btnAnnuler;
	private JButton btnSupprimer;
	private JTable listeCartouche;
	private JScrollPane jsCartouche;
	private Object[][] lignes;
	private Vector <Cartouche> lesCartouches;
	
	public FenetreListeCartouche( Vector <Cartouche> lesCartouches)
    {
            super(); // initialise les attributs d’une JDialog 
            this.btnValider=new JButton("Valider");
            this.btnValider.addActionListener (this);
            this.btnAnnuler=new JButton("Annuler");
            this.btnAnnuler.addActionListener (this);
            this.btnSupprimer=new JButton("Supprimer la sélection");
            this.btnSupprimer.addActionListener (this);
            this.lesCartouches=lesCartouches;
            String titres[]={"Référence","Désignation","Prix"};
            lignes=new Object[lesCartouches.size()][3];
            int i = 0;
            for (Cartouche c : lesCartouches)
            {
            	lignes[i]=c.valeurs();
            	i++;
            }
            this.listeCartouche=new JTable(lignes,titres);
            // Sélection de la première ligne par défaut
            this.listeCartouche.addRowSelectionInterval(0, 0);

    	    this.jsCartouche=new JScrollPane(listeCartouche);
            this.contenu = this.getContentPane( ) ;

            this.contenu.add(btnValider);
            this.contenu.add(btnAnnuler);
            this.contenu.add(btnSupprimer);
            this.contenu.add(jsCartouche);
            
            this.contenu.setLayout(null);
            
            this.btnAnnuler.setBounds(100,300,80,20);
            this.btnValider.setBounds(200,300,80,20);
            this.btnSupprimer.setBounds(100,330,180,20);
            this.jsCartouche.setBounds(20,20,400,200);
    }
	
	   public void actionPerformed(ActionEvent evt)
	     {
		   	         
		   if (evt.getSource() == btnAnnuler )
	        	 this.dispose();
		   
	        if (evt.getSource() == btnValider )
	        {
	        	for(int i=0; i<this.listeCartouche.getSelectedRowCount(); i++)
	        	{
	        		Cartouche C = lesCartouches.elementAt(this.listeCartouche.getSelectedRows()[i]);
	        		JOptionPane.showMessageDialog(this, "Cartouche sélectionnée : "+C.toString());
	        	}
	        }
	        if (evt.getSource() == btnSupprimer )
	        {
	        	int nb=0;
	        	for(int i=0; i<this.listeCartouche.getSelectedRowCount(); i++)
	        	{
	        		Cartouche c = lesCartouches.elementAt(this.listeCartouche.getSelectedRows()[i]);
	        		nb=DAO_TonerExpress.supprimerCartouche(c);
	        		if (nb != 0)
	        			lesCartouches.remove(this.listeCartouche.getSelectedRows()[i]);
	        		        	}
	        	//listeCartouche.removeAll();
	        	this.dispose();
	        }
	        
	     }
}
