package metier;


public class TypeImprimante {
	private char code;
	private String libelle;

	public TypeImprimante(char code ,String libelle) {
		this.libelle = libelle;
		this.code = code;
	}
	public String getLibelle() {
		return libelle;
	}
	public char getCode() {
		return code;
	}
	public String toString()
	{
		String msg = this.code+" - "+this.libelle;
		return msg;
	}
}
