package metier;

import java.util.Vector;


public class Cartouche {
	private String refCartouche;
	private String desCartouche;
	private double prixCartoucheNeuve;
	
	// constructeur
	public Cartouche(String refCartouche, String desCartouche, double prixCartoucheNeuve) {
		this.refCartouche = refCartouche;
		this.desCartouche = desCartouche;
		this.prixCartoucheNeuve = prixCartoucheNeuve;
	}
	public String getDesCartouche() {
		return desCartouche;
	}
	public void setDesCartouche(String desCartouche) {
		this.desCartouche = desCartouche;
	}
	public double getPrixCartoucheNeuve() {
		return prixCartoucheNeuve;
	}
	public void setPrixCartoucheNeuve(double prixCartoucheNeuve) {
		this.prixCartoucheNeuve = prixCartoucheNeuve;
	}
	public String getRefCartouche() {
		return refCartouche;
	}
	public void setRefCartouche(String refCartouche) {
		this.refCartouche = refCartouche;
	}
	public String toString(){
		String msg = this.refCartouche+" - "+this.desCartouche;
		msg = msg+" - "+this.prixCartoucheNeuve+" €";
		return msg;
	}
	public String[] valeurs(){
		String[] res={this.refCartouche,this.desCartouche,Double.toString(this.prixCartoucheNeuve)};
		return res;
	}
}
