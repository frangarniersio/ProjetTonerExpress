package metier;

import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Garnier
 *
 */
public class Imprimante {
	private String refImprimante;
	private TypeImprimante typeImprimante;
	private String desImprimante;
	private ArrayList<Cartouche> lesCartouches;
 
	// constructeur
	public Imprimante(String refImprimante, TypeImprimante typeImprimante, String desImprimante, ArrayList<Cartouche> lesCartouches) 
	{
		this.refImprimante = refImprimante;
		this.typeImprimante = typeImprimante;
		this.desImprimante = desImprimante;
		this.lesCartouches = lesCartouches;
	}

	public Imprimante(String refImprimante, TypeImprimante typeImprimante, String desImprimante) 
	{
		this.refImprimante = refImprimante;
		this.typeImprimante = typeImprimante;
		this.desImprimante = desImprimante;
		this.lesCartouches = new ArrayList<Cartouche>();
	}
	
	public void setLesCartouches(ArrayList<Cartouche> uneListe) 
	{
		this.lesCartouches = uneListe;
	}
	
	public String getRefImprimante()
	{
		return this.refImprimante;
	}
	public String getDesImprimante()
	{
		return this.desImprimante;
	}	
	public TypeImprimante getTypeImprimante()
	{
		return this.typeImprimante;
	}
	

	public ArrayList<Cartouche> getLesCartouches() 
	{
		return this.lesCartouches;
	}
	
	public int getNbCartouchesCompatibles() 
	{
		return this.lesCartouches.size();
	}
	
	public String toString()
	{
		String msg = this.refImprimante+" - "+this.desImprimante+" - "+this.typeImprimante.getLibelle();
		return msg;
	}
}
