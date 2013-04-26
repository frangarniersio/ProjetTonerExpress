package ihm;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import metier.Cartouche;
import metier.Imprimante;
import metier.TypeImprimante;
import ado.DAO_TonerExpress;

public class FenetreAjoutImprimante extends JDialog implements ActionListener
{
	private Container contenu;					// déclaration des composants
	private JLabel lblRef;
	private JLabel lblDesi;
	private JLabel lblType;
	private JLabel lblCartouche;
	private JTextField txtRef;
	private JTextField txtDesi;
	private JComboBox lstType;
	private JList lstCartouche;
	private JScrollPane jsCartouche;
	private JButton btnAjoutCartouche;
	private JButton btnAnnuler;
	private JButton btnValider;
	private Vector <TypeImprimante> lesTypes;
	private Vector <Imprimante> lesImprimantes;
	private Vector <Cartouche> lesCartouches;
	
	private Boolean estPresent(String ref)
	{
		Boolean existe = false;
		int i = 0;
		while (! existe && i < lesImprimantes.size())
		{
			if (ref.equals(lesImprimantes.get(i).getRefImprimante()))
				existe = true;
			else
				i++;
		}
		return (existe);
	}
	
	public FenetreAjoutImprimante(Vector <Imprimante> lesImprimantes, Vector <Cartouche> lesCartouches)
	{
		lesTypes=DAO_TonerExpress.getLesTypes();
		this.lesImprimantes = lesImprimantes;
		this.lesCartouches=lesCartouches;
		contenu = this.getContentPane();		// gestion du conteneur
		contenu.setLayout(null);

		lblRef = new JLabel("Référence");		// gestion des étiquettes
		lblDesi = new JLabel("Désignation");
		lblType = new JLabel("Type d'imprimante");
		lblCartouche = new JLabel("Cartouches possibles ");
		lblRef.setBounds(50,50, 100, 20);
		lblDesi.setBounds(250,50, 100, 20);
		lblType.setBounds(450,50, 150, 20);
		lblCartouche.setBounds(50,120, 150, 20);
 		contenu.add(lblRef);
		contenu.add(lblDesi);
		contenu.add(lblType);
		contenu.add(lblCartouche);
		
		txtRef = new JTextField();				// gestion des zones de texte
		txtDesi = new JTextField();
	    lstType = new JComboBox(lesTypes);
	    lstCartouche=new JList(lesCartouches); 
	    
	    jsCartouche=new JScrollPane(lstCartouche);
		txtRef.setBounds(50,80,100,20);
		txtDesi.setBounds(200,80,200,20);
		lstType.setBounds(450,80,150,20);
		//lstCartouche.setBounds(300,120,300,120);
		jsCartouche.setBounds(280,120,320,150);
		contenu.add(txtRef);
		contenu.add(txtDesi);
		contenu.add(lstType);
		//contenu.add(lstCartouche);	Pour éviter les conflits entre liste et panneau déroulant
		contenu.add(jsCartouche);
		
		btnAnnuler = new JButton("Annuler");	// gestion des boutons de commande
		btnValider = new JButton("Valider");
		btnAjoutCartouche = new JButton("Ajouter une cartouche");
		btnAnnuler.setBounds(100,320,100,20);
		btnValider.setBounds(300,320,100,20);
		btnAjoutCartouche.setBounds(50, 200, 180, 20);
		btnAnnuler.addActionListener(this);
		btnValider.addActionListener(this);
		btnAjoutCartouche.addActionListener(this);
		contenu.add(btnAnnuler);
		contenu.add(btnValider);
		contenu.add(btnAjoutCartouche);
	}
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getSource() == btnAnnuler)
			this.dispose();
		if (evt.getSource() == btnAjoutCartouche)
		{
	   		// Le click sur le bouton AjoutCartouche entraîne l'ouverture de la fenêtreChoixIimprimante
	   		FenetreAjoutCartouche fAjoutImp= new FenetreAjoutCartouche(lesCartouches);
	   		fAjoutImp.setBounds(20,20,650,300);
	   		fAjoutImp.setTitle("Ajout d'une cartouche");
	   		fAjoutImp.setModal(true);
	   		fAjoutImp.setLocation(100,100);
	   		fAjoutImp.setVisible(true);
	   		lstCartouche.setListData(lesCartouches);
		}
		if (evt.getSource() == btnValider)
		{	
			//JOptionPane.showMessageDialog(null,lstCartouche.getSelectedValues());
			if (txtRef.getText().isEmpty() || txtDesi.getText().isEmpty())
				JOptionPane.showMessageDialog(null,"Vous devez renseigner tous les champs !!!");
			else
			{	// On vérifie que la référence saisie n'existe pas déjà dans la collection,
				// on évite ainsi une incohérence entre la collection et la base de données.
				if (estPresent(txtRef.getText()))
					JOptionPane.showMessageDialog(null,"Cette référence d'imprimante existe déjà !!!");
				else
				{	// 	On récupère le type de l'imprimante
					TypeImprimante type = (TypeImprimante) lstType.getSelectedItem();
					//	On récupère les différentes cartouches
					ArrayList <Cartouche> sesCartouches;
					Cartouche cart;
					sesCartouches=new ArrayList <Cartouche>();
					int nbc,i;
					nbc = lstCartouche.getSelectedValues().length;
					for (i=0; i < nbc; i++)
					{
						cart = (Cartouche)lstCartouche.getSelectedValues()[i];
						sesCartouches.add(cart);
					}
					//	On peut enfin créer l'imprimante
					Imprimante imp = new Imprimante(txtRef.getText(), type, txtDesi.getText(),sesCartouches);
					int nb = DAO_TonerExpress.ajouterImprimante(imp);
					if (nb != 0)
						this.lesImprimantes.add(imp);
				}
			}
		}
	}
}
