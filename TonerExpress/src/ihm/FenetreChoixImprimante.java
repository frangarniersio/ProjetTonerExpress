package ihm;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import metier.Imprimante;
//import javax.swing.ComboBoxModel;

public class FenetreChoixImprimante extends JDialog implements ActionListener{
	
	private Container contenu ;
	private JButton btnValider;
	private JButton btnAnnuler;
	private JComboBox listeImprimante;

	
	public FenetreChoixImprimante( Vector <Imprimante> lesImprimantes)
    {
            super(); // initialise les attributs d’une JDialog 
            this.btnValider=new JButton("Valider");
            this.btnValider.addActionListener (this);
            this.btnAnnuler=new JButton("Annuler");
            this.btnAnnuler.addActionListener (this);
            this.listeImprimante=new JComboBox(lesImprimantes);
            
            this.contenu = this.getContentPane( ) ;

            this.contenu.add(btnValider);
            this.contenu.add(btnAnnuler);
            this.contenu.add(listeImprimante);
            
            this.contenu.setLayout(null);
            
            this.btnAnnuler.setBounds(340,20,80,20);
            this.btnValider.setBounds(430,20,80,20);
            this.listeImprimante.setBounds(20,20,300,20);
            
            // On remplit la liste des imprimantes avec la méthode statique de la classe de Test
       	 //	for (Imprimante uneImprimante : MenuPrincipal.getLesImprimantes())
       	 	//	this.listeImprimante.addItem(uneImprimante);
    }
	
	   public void actionPerformed(ActionEvent evt)
	     {
		   	         
		   if (evt.getSource() == btnAnnuler )
	        	 this.dispose();
		   
	        if (evt.getSource() == btnValider )
	        {
	        	Imprimante I = (Imprimante)this.listeImprimante.getSelectedItem();
	        	JOptionPane.showMessageDialog(this, "Pour l'imprimante "+I.toString()+", il existe "+I.getNbCartouchesCompatibles()+ " cartouches compatibles");
	        }
	     }
}
