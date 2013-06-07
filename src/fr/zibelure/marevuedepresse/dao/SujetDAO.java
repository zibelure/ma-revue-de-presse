package fr.zibelure.marevuedepresse.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import fr.zibelure.marevuedepresse.model.Sujet;

public class SujetDAO extends DAOBase {
	
	public SujetDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	public static final String SUJET_KEY = "id";
	public static final String SUJET_TITRE = "titre";

	public static final String SUJET_TABLE_NAME = "sujet";
	public static final String SUJET_TABLE_CREATE = "CREATE TABLE "
			+ SUJET_TABLE_NAME + " (" + SUJET_KEY
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUJET_TITRE + " TEXT);";

	public static final String SUJET_TABLE_DROP = "DROP TABLE IF EXISTS " + SUJET_TABLE_NAME + ";";
	

	/**
	 * @param sujet
	 *            le métier à ajouter à la base
	 */
	public void ajouter(Sujet sujet) {
		this.open();
		ContentValues value = new ContentValues();
		value.put(SUJET_TITRE, sujet.getTitre());
		mDb.insert(SUJET_TABLE_NAME, null, value);
		this.close();
	}

	/**
	 * @param id
	 *            l'identifiant du sujet à supprimer
	 */
	public void supprimer(long id) {
		this.open();
		mDb.delete(SUJET_TABLE_NAME, SUJET_KEY + " = ?", new String[] {String.valueOf(id)});
		this.close();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Sujet> getSujets() {
		List<Sujet> sujets = new ArrayList<Sujet>();
		Sujet sujet;
		this.open();
	    Cursor cursor = mDb.query(SUJET_TABLE_NAME, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			try {
				sujet = new Sujet();
				sujet.setId(Long.parseLong(cursor.getString(cursor.getColumnIndex(SUJET_KEY))));
				sujet.setTitre(cursor.getString(cursor.getColumnIndex(SUJET_TITRE)));
				sujets.add(sujet);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} 
		}
		this.close();
		return sujets;
	}
}
