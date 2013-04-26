package ado;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import metier.Cartouche;
import metier.Imprimante;
import metier.TypeImprimante;

public class DAO_TonerExpress 
{
	private static Connection cnt;
	
	public static Vector <TypeImprimante> getLesTypes()
	{
		Vector <TypeImprimante> lesTypes = new Vector <TypeImprimante>();
		Statement traitPrincipal;
		ResultSet resType;
		try
		{
			cnt = OracleConnection.getConnection("test","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	// pour MySql
			traitPrincipal = cnt.createStatement();	
			resType = traitPrincipal.executeQuery("select * from Type");
			while (resType.next())
			{
				lesTypes.add(new TypeImprimante(resType.getString(1).charAt(0), resType.getString(2)));
			}
			resType.close();
			traitPrincipal.close();
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème d'accès aux données "+e.getMessage());
		}
		return lesTypes;
	}


	public static Vector <Imprimante> getLesImprimantes()
	{
		Vector <Imprimante> lesImp = new Vector <Imprimante>();
		ResultSet resImp, resType, resCart;
		TypeImprimante leType;
		ArrayList <Cartouche> lesCartouches;
		Statement traitPrincipal, traitement;
		String texte, ref, desi;
		boolean encore;
		
		try
		{
			cnt = OracleConnection.getConnection("test","scott","tiger");	// pour Oracle
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	// pour MySql
			traitPrincipal = cnt.createStatement();	
			traitement = cnt.createStatement();
			resImp = traitPrincipal.executeQuery("select * from Imprimante");
			encore = resImp.next();
			while (encore)
			{
				ref = resImp.getString(1);	// mémorisation des données avant la requête suivante  
				desi = resImp.getString(2);
				// Recherche du type
				leType=null;
				texte = "select * from TYPE where CodeType = '"+resImp.getString(3)+"'";
				//JOptionPane.showMessageDialog(null,texte);
				resType=traitement.executeQuery(texte);
				if (resType.next())
					leType=new TypeImprimante(resType.getString(1).charAt(0), resType.getString(2));
				
				// Recherche des cartouches
				lesCartouches=new ArrayList<Cartouche>();
				texte = "select CARTOUCHE.RefCartouche, DesCartouche, PrixCartouche from CARTOUCHE, ACCEPTER where CARTOUCHE.RefCartouche=ACCEPTER.RefCartouche and RefImprimante = '"+resImp.getString(1)+"'";
				//JOptionPane.showMessageDialog(null,texte);
				resCart=traitement.executeQuery(texte);
				while (resCart.next())
				{
					lesCartouches.add(new Cartouche(resCart.getString(1),resCart.getString(2),resCart.getDouble(3)));
				}
				
				// Création de l'imprimante avec les données mémorisées 
				lesImp.add(new Imprimante(ref,leType,desi,lesCartouches));
				encore = resImp.next();
			}	
			traitPrincipal.close();
			traitement.close();
			//JOptionPane.showMessageDialog(null,"Création de la collection des imprimantes");
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème d'accès aux données "+e.getMessage());
		}
		return lesImp;
	}
	
	public static Vector <Cartouche> getLesCartouches()
	{
		Vector <Cartouche> lesCartouches = new Vector <Cartouche>();
		Statement traitPrincipal;
		ResultSet resCart;
		try
		{
			cnt = OracleConnection.getConnection("test","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	// pour MySql
			traitPrincipal = cnt.createStatement();	
			resCart = traitPrincipal.executeQuery("select * from Cartouche");
			while (resCart.next())
			{
				lesCartouches.add(new Cartouche(resCart.getString(1), resCart.getString(2), resCart.getDouble(3)));
			}
			resCart.close();
			traitPrincipal.close();
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème d'accès aux données "+e.getMessage());
		}
		return lesCartouches;
	}
	
	public static int ajouterCartouche(Cartouche cart)
	{
		Statement traitement;
		String texte;
		int nb=0;
		try
		{
			cnt = OracleConnection.getConnection("test","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	
			traitement = cnt.createStatement();
			texte = "Insert into Cartouche values('"+cart.getRefCartouche()+"','"+cart.getDesCartouche()+"',"+cart.getPrixCartoucheNeuve()+")";
			nb=traitement.executeUpdate(texte);
			JOptionPane.showMessageDialog(null,nb+" cartouche ajoutée");
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème de d'accès aux données "+e.getMessage());
		}
		return nb;
	}
	public static int ajouterImprimante(Imprimante imp)
	{
		Statement traitement;
		String texte;
		int nb=0, i, nbc, nbca;
		Cartouche cart;
		
		try
		{	// On tente d'ajouter l'imprimante dans la base
			cnt = OracleConnection.getConnection("test","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	
			traitement = cnt.createStatement();
			texte = "Insert into Imprimante values('"+imp.getRefImprimante()+"','"+imp.getDesImprimante()+"','"+imp.getTypeImprimante().getCode()+"')";
			nb=traitement.executeUpdate(texte);
			nbc=imp.getNbCartouchesCompatibles();
			nbca=0;
			for (i=0; i<nbc; i++)
			{
				cart = imp.getLesCartouches().get(i);
				texte = "Insert into Accepter values('"+imp.getRefImprimante()+"','"+ cart.getRefCartouche() +"')";
				nbca+=traitement.executeUpdate(texte);
			}
			JOptionPane.showMessageDialog(null,nb+" imprimante ajoutée avec "+nbca+" cartouches");
			cnt.close();
		}
		catch (Exception e)
		{	// Message affiché si l'imprimante existe déjà
			JOptionPane.showMessageDialog(null,"Problème d'accès aux données : "+e.getMessage());
		}
		return nb;
	}	
	
	public static int supprimerCartouche(Cartouche cart)
	{
		Statement traitement;
		String texte;
		int nb=0;
		try
		{
			cnt = OracleConnection.getConnection("test","scott","tiger");
			//cnt = MySqlConnection.getConnection("TonerExpress","root","superchef"); 	
			traitement = cnt.createStatement();
			texte = "Delete from Accepter where RefCartouche='"+cart.getRefCartouche()+"'";
			nb=traitement.executeUpdate(texte);
			texte = "Delete from Cartouche where RefCartouche='"+cart.getRefCartouche()+"'";
			nb=traitement.executeUpdate(texte);
			JOptionPane.showMessageDialog(null,nb+" cartouche supprimée");
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème de d'accès aux données "+e.getMessage());
		}
		return nb;
	}
}
