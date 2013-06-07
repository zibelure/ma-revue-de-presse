package fr.zibelure.marevuedepresse.model;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Reponse extends Item {

	private UUID idInterne;
	private String idResultat;
	private String titre;
	private String contenu;
	private String lienSite;
	private String lienImage;
	private Bitmap image;
	private String langue;
	private int score;

	/**
	 * @param idInterne
	 */
	public Reponse() {
		this.idInterne = UUID.randomUUID();
	}

	public UUID getIdInterne() {
		return idInterne;
	}

	public String getIdResultat() {
		return idResultat;
	}

	public void setIdResultat(String idResultat) {
		this.idResultat = idResultat;
	}

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

	public String getLienSite() {
		return lienSite;
	}

	public void setLienSite(String lienSite) {
		this.lienSite = lienSite;
	}

	public String getLienImage() {
		return lienImage;
	}

	public void setLienImage(String lienImage) {
		this.lienImage = lienImage;
		
		if (this.lienImage != null) {
			try {
				URL aURL = new URL(this.lienImage);
				this.image = BitmapFactory.decodeStream(aURL.openStream());
			} catch (Exception ex) {
				Log.w("MonApp", "Echec de chargement de l'image !", ex);
			}
		}
		
	}

	public Bitmap getImage() {
		return this.image;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Reponse [idInterne=" + idInterne + ", fournisseur="
				+ super.getFournisseur() + ", source=" + super.getSource() + ", idResultat="
				+ idResultat + ", titre=" + titre + ", contenu=" + contenu
				+ ", lienSite=" + lienSite + ", lienImage=" + lienImage
				+ ", datePublication=" + super.getDatePublication() + ", langue=" + langue
				+ ", score=" + score + "]";
	}

}
