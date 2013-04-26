package ihm;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import metier.Cartouche;
import ado.DAO_TonerExpress;

public class FenetreAjoutCartouche extends JDialog implements ActionListener
{
	private Container contenu;					// déclaration des composants
	private JLabel lblRef;
	private JLabel lblDesi;
	private JLabel lblPrix;
	private JTextField txtRef;
	private JTextField txtDesi;
	private JTextField txtPrix;
	private JButton btnAnnuler;
	private JButton btnValider;
	private Vector <Cartouche> lesCartouches;
	
	public FenetreAjoutCartouche(Vector <Cartouche> lesCartouches)
	{
		this.lesCartouches=lesCartouches;
		contenu = this.getContentPane();		// gestion du conteneur
		contenu.setLayout(null);

		lblRef = new JLabel("Référence");		// gestion des étiquettes
		lblDesi = new JLabel("Désignation");
		lblPrix = new JLabel("Prix neuve");
		lblRef.setBounds(50,50, 100, 20);
		lblDesi.setBounds(250,50, 100, 20);
		lblPrix.setBounds(450,50, 100, 20);
		contenu.add(lblRef);
		contenu.add(lblDesi);
		contenu.add(lblPrix);
		
		txtRef = new JTextField();				// gestion des zones de texte
		txtDesi = new JTextField();
		txtPrix = new JTextField();
		txtRef.setBounds(50,80,100,20);
		txtDesi.setBounds(200,80,200,20);
		txtPrix.setBounds(450,80,100,20);
		contenu.add(txtRef);
		contenu.add(txtDesi);
		contenu.add(txtPrix);
		
		btnAnnuler = new JButton("Annuler");	// gestion des boutons de commande
		btnValider = new JButton("Valider");
		btnAnnuler.setBounds(100,120,100,20);
		btnValider.setBounds(300,120,100,20);
		btnAnnuler.addActionListener(this);
		btnValider.addActionListener(this);
		contenu.add(btnAnnuler);
		contenu.add(btnValider);
	}
	
	private Boolean estPresent(String ref)
	{
		Boolean existe = false;
		int i = 0;
		while (! existe && i < lesCartouches.size())
		{
			if (ref.equals(lesCartouches.get(i).getRefCartouche()))
				existe = true;
			else
				i++;
		}
		return (existe);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnAnnuler)
			this.dispose();
		if (evt.getSource() == btnValider)
		{
			if (txtRef.getText().isEmpty() || txtDesi.getText().isEmpty() || txtPrix.getText().isEmpty())
				JOptionPane.showMessageDialog(null,"Vous devez renseigner tous les champs !!!");
			else
			{
				if (estPresent(txtRef.getText()))
					JOptionPane.showMessageDialog(null,"Cette référence de cartouche existe déjà !!!");
				else
				{
					Cartouche cart = new Cartouche(txtRef.getText(), txtDesi.getText(), Double.valueOf(txtPrix.getText()));
					int nb = DAO_TonerExpress.ajouterCartouche(cart);
					if (nb != 0)
						this.lesCartouches.add(cart);
				}
			}
		}
	}
}
