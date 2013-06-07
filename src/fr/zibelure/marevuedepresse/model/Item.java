package fr.zibelure.marevuedepresse.model;

import java.util.Date;

import android.graphics.Bitmap;

public class Item {
	
	private String titre;
	private String contenu;
	private Date datePublication;
	private String fournisseur;
	private String source;
	private Bitmap image;
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Date getDatePublication() {
		return datePublication;
	}
	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}
	public String getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
}
