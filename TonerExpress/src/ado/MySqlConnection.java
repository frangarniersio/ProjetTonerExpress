package ado;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class MySqlConnection {
	/* 	Driver pour MySql	*/
	private static final String pilote="org.gjt.mm.mysql.Driver";
	private static final String url ="jdbc:mysql://localhost/";
	private static Connection cnt;
	
	public static Connection getConnection(String base, String util, String mdp)
	{
		cnt=null;
		try
		{
			Class.forName(pilote);
			cnt = DriverManager.getConnection(url+base,util,mdp); 	
		
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème de connexion à la base "+e.getMessage());
		}
		return(cnt);
	}
	public static void close()
	{
		try
		{
			cnt.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"Problème de déconnexion à la base "+e.getMessage());
		}
	}
}
