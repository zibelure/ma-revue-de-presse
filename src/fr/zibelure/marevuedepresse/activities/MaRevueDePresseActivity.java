package fr.zibelure.marevuedepresse.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import fr.zibelure.marevuedepresse.adapter.ListeItemsAdapter;
import fr.zibelure.marevuedepresse.business.recherches.RecherchesServices;
import fr.zibelure.marevuedepresse.business.recherches.impl.RecherchesImpl;
import fr.zibelure.marevuedepresse.business.restitution.RestitutionServices;
import fr.zibelure.marevuedepresse.business.restitution.impl.RestitutionServicesImpl;
import fr.zibelure.marevuedepresse.dao.SujetDAO;
import fr.zibelure.marevuedepresse.model.Item;
import fr.zibelure.marevuedepresse.model.Reponse;
import fr.zibelure.marevuedepresse.model.Sujet;
import fr.zibelure.mesnouvelles.R;

public class MaRevueDePresseActivity extends Activity {

	private static final String LOG_TAG = "Log : ";
	private final String mimeType = "text/html";
	private final String encoding = "utf-8";
	private WebView webView;
	private EditText editText;
	private Button buttonAjout;
	private ListView listeSujets;
	private ListView listeNouvelles;

	private ListeItemsAdapter listeSujetsAdapter;
	private ListeItemsAdapter listeReponsesAdapter;

	private ProgressDialog myProgressDialog;
	final Handler uiThreadCallback = new Handler();

	private SujetDAO sujetDAO;
	private Context context;
	private String themeEnCours;
	private int positionSujetASupprimer;

	private ChargerListeSujetsAsyncTask chargerListeSujetsAsyncTask;
	private ChargerListeNouvellesAsyncTask chargerListeNouvellesAsyncTask;
	private ChargerNouvelleAsyncTask chargerNouvelleAsyncTask;

	private final String MESSAGE_CHARGEMENT_SUJETS = "Chargement de vos sujets...";
	private final String MESSAGE_RECHERCHE_NOUVELLES = "Recherche en cours...";
	private final String MESSAGE_AFFICHER_NOUVELLE = "Affichage du site en cours...";
	private final String MESSAGE_RETOUR_NOUVELLES = "Retour aux nouvelles...";
	private final String MESSAGE_RETOUR_SUJETS = "Retour aux sujets...";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myProgressDialog = ProgressDialog.show(MaRevueDePresseActivity.this,
				"", MESSAGE_CHARGEMENT_SUJETS, true);

		// On récupère l'EditText, le WebView, et le Button grâce au ID
		editText = (EditText) findViewById(R.id.EditText);
		webView = (WebView) findViewById(R.id.WebView);
		buttonAjout = (Button) findViewById(R.id.ButtonAjout);

		editText.setVisibility(View.GONE);
		buttonAjout.setVisibility(View.VISIBLE);

		// Récupération de la listview des thémes créée dans le fichier main.xml
		listeSujets = (ListView) findViewById(R.id.listesujets);

		// Récupération de la listview des nouvelles créée dans le fichier
		// main.xml
		listeNouvelles = (ListView) findViewById(R.id.listenouvelles);

		// Récupération du context
		context = this.getBaseContext();

		// Récupération du DAO des objets Sujets
		sujetDAO = new SujetDAO(context);

		// Chargement de la page d'accueil
		// new ChargerListeSujetsAsyncTask().execute("");
		chargerListeSujetsAsyncTask = new ChargerListeSujetsAsyncTask();
		chargerListeSujetsAsyncTask.execute("");

		// on met un écouteur d'évènement sur notre listView des sujets de
		// l'utilisateur
		listeSujets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				myProgressDialog = ProgressDialog.show(
						MaRevueDePresseActivity.this, "",
						MESSAGE_RECHERCHE_NOUVELLES, true);

				// on récupère la HashMap contenant les infos de notre item
				// (titre, description, img)
				Sujet sujet = (Sujet) listeSujets.getItemAtPosition(position);

				themeEnCours = (String) sujet.getTitre();

				// On charge la liste des nouvelles
				// new ChargerListeNouvellesAsyncTask().execute(themeEnCours);
				chargerListeNouvellesAsyncTask = new ChargerListeNouvellesAsyncTask();
				chargerListeNouvellesAsyncTask.execute(themeEnCours);
			}
		});

		// Enfin on met un écouteur d'évènement sur notre listView des nouvelles
		listeNouvelles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				myProgressDialog = ProgressDialog.show(
						MaRevueDePresseActivity.this, "",
						MESSAGE_AFFICHER_NOUVELLE, true);

				// on récupère la HashMap contenant les infos de notre item
				// (titre, description, img)
				Reponse reponse = (Reponse) listeNouvelles
						.getItemAtPosition(position);

				// Chargement de la liste des nouvelles
				if (reponse.getLienSite() != null
						&& reponse.getLienSite().length() > 0) {
					// new ChargerNouvelleAsyncTask().execute((String)
					// reponse.getLienSite());
					chargerNouvelleAsyncTask = new ChargerNouvelleAsyncTask();
					chargerNouvelleAsyncTask.execute((String) reponse
							.getLienSite());

				} else {
					infoAucunLienSite();
					myProgressDialog.dismiss();
				}
			}
		});

		// Suppression d'un thème de la liste
		listeSujets
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
					@Override
					@SuppressWarnings("unchecked")
					public boolean onItemLongClick(AdapterView<?> arg0, View v,
							int position, long id) {

						positionSujetASupprimer = position;

						ListView myList = (ListView) findViewById(R.id.listesujets);

						// Toast.makeText(context,
						// myList.getItemAtPosition(position).toString(),
						// Toast.LENGTH_LONG).show();
						supprimerSujet();
						return false;
					}
				});

		// on met un écouteur d'évènement sur notre bouton d'ajout de thèmes
		buttonAjout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ajouterSujet();
			}
		});

	}

	/** Called when user press the back button */
	@Override
	public void onBackPressed() {
		if (chargerListeSujetsAsyncTask != null) {
			chargerListeSujetsAsyncTask.cancel(true);
		}
		if (chargerListeNouvellesAsyncTask != null) {
			chargerListeNouvellesAsyncTask.cancel(true);
		}
		if (chargerNouvelleAsyncTask != null) {
			chargerNouvelleAsyncTask.cancel(true);
		}
	}

	/*
	 * ChargerListeSujetsAsyncTask
	 */
	public class ChargerListeSujetsAsyncTask extends
			AsyncTask<String, Void, List<Item>> {

		@Override
		protected List<Item> doInBackground(String... params) {

			// Création de la ArrayList qui nous permettra de remplir la
			// listView
			List<Item> listItem = new ArrayList<Item>();

			// on récupère l'ensemble des sujets disponibles en base
			List<Sujet> sujets = sujetDAO.getSujets();
			listItem.addAll(sujets);

			return listItem;
		}

		@Override
		protected void onPostExecute(List<Item> listItem) {
			super.onPostExecute(listItem);

			listeSujetsAdapter = new ListeItemsAdapter(context,
					R.layout.activity_list_item, listItem);

			afficherSujets();
		}
	}

	/*
	 * afficherSujets
	 */
	private void afficherSujets() {
		// On attribue à notre listView l'adapter que l'on vient de créer
		listeSujets.setAdapter(listeSujetsAdapter);

		listeNouvelles.setVisibility(View.GONE);
		listeSujets.setVisibility(View.VISIBLE);
		webView.setVisibility(View.GONE);
		editText.setVisibility(View.GONE);
		buttonAjout.setVisibility(View.VISIBLE);

		myProgressDialog.dismiss();
	}

	/*
	 * chargerPageActualiteAsyncTask
	 */
	public class ChargerListeNouvellesAsyncTask extends
			AsyncTask<String, Void, List<Item>> {

		@Override
		protected List<Item> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String theme = params[0];
			// Création de la ArrayList qui nous permettra de remplir la
			// listView
			List<Item> listItem = new ArrayList<Item>();
			try {

				//
				RecherchesServices recherchesServices = new RecherchesImpl();
				Map<UUID, Reponse> reponses = recherchesServices
						.rechercher(theme);

				for (Iterator it = reponses.keySet().iterator(); it.hasNext();) {
					UUID uuid = (UUID) it.next();
					Item reponse = (Reponse) reponses.get(uuid);
					listItem.add(reponse);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return listItem;
		}

		@Override
		protected void onPostExecute(List<Item> listItem) {
			super.onPostExecute(listItem);

			// @TODO
			listeReponsesAdapter = new ListeItemsAdapter(context,
					R.layout.activity_list_item, listItem);

			afficherListeNouvelles();
		}

	}

	/*
	 * afficherListeNouvelles
	 */
	private void afficherListeNouvelles() {
		listeNouvelles.setAdapter(listeReponsesAdapter);

		listeSujets.setVisibility(View.GONE);
		listeNouvelles.setVisibility(View.VISIBLE);
		webView.setVisibility(View.GONE);
		editText.setVisibility(View.GONE);
		buttonAjout.setVisibility(View.GONE);

		myProgressDialog.dismiss();
	}

	/*
	 * ChargerNouvelleAsyncTask
	 */
	public class ChargerNouvelleAsyncTask extends
			AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = params[0];
			String pageWeb = null;
			// on récupère le code HTML associé à l'URL que l'on a indiqué dans
			// l'EditText
			RestitutionServices restitutionServices = new RestitutionServicesImpl();
			pageWeb = restitutionServices.chargerPageWeb(url);
			return pageWeb;
		}

		@Override
		protected void onPostExecute(String pageWeb) {
			super.onPostExecute(pageWeb);
			// on autorise le JavaScript dans la WebView
			webView.getSettings().setJavaScriptEnabled(true);
			// on charge les données récupérées dans la WebView
			webView.loadDataWithBaseURL("fake://not/needed", pageWeb, mimeType,
					encoding, "");

			listeSujets.setVisibility(View.GONE);
			listeNouvelles.setVisibility(View.GONE);
			webView.setVisibility(View.VISIBLE);
			editText.setVisibility(View.GONE);
			buttonAjout.setVisibility(View.GONE);

			myProgressDialog.dismiss();
		}
	}

	// Boite d'alerte de confirmation de suppression d'un sujet
	public void supprimerSujet() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Etes-vous sûr de supprimer ce sujet ?")
				.setCancelable(false)
				.setPositiveButton("Oui",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Sujet sujet = (Sujet) listeSujets
										.getItemAtPosition(positionSujetASupprimer);
								listeSujetsAdapter.remove(sujet);
								sujetDAO.supprimer(sujet.getId());
								dialog.cancel();
							}
						})
				.setNegativeButton("Non",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// Boite d'alerte d'ajout d'un sujet
	public void ajouterSujet() {
		final EditText input = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Ajouter un nouveau sujet")
				.setView(input)
				.setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Sujet sujet = new Sujet();
						sujet.setTitre(input.getText().toString());
						listeSujetsAdapter.add(sujet);
						sujetDAO.ajouter(sujet);
						dialog.cancel();
					}
				})
				.setNegativeButton("Annuler",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// Boite d'info indiquant qu'il n'existe pas de lien
	public void infoAucunLienSite() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Aucun lien disponible")
				.setCancelable(false)
				.setPositiveButton("Fermer",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			event.startTracking();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
				&& !event.isCanceled()) {
			if (listeSujets.isShown()) {
				System.exit(0);
			} else if (listeNouvelles.isShown()) {
				myProgressDialog = ProgressDialog.show(
						MaRevueDePresseActivity.this, "",
						MESSAGE_RETOUR_SUJETS, true);
				// Chargement de la page d'accueil
				afficherSujets();
				// new ChargerListeSujetsAsyncTask().execute("");
			} else if (webView.isShown()) {
				myProgressDialog = ProgressDialog.show(
						MaRevueDePresseActivity.this, "",
						MESSAGE_RETOUR_NOUVELLES, true);
				// On charge la liste des nouvelles définie en cache
				afficherListeNouvelles();
				// new ChargerListeNouvellesAsyncTask().execute(themeEnCours);
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

}
