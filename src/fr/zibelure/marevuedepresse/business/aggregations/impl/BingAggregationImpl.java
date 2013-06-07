/**
 * 
 */
package fr.zibelure.marevuedepresse.business.aggregations.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.zibelure.marevuedepresse.business.aggregations.AggregationsServices;
import fr.zibelure.marevuedepresse.model.Reponse;

/**
 * @author anadot
 *
 */
public class BingAggregationImpl implements AggregationsServices {
	
	final static String BING = "BING";

	/* (non-Javadoc)
	 * @see fr.crapouille.services.aggregations.AggregationsServices#aggreger(java.util.List)
	 */
	@Override
	public Map<UUID, Reponse> aggreger(List<JSONObject> collectes) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.crapouille.services.aggregations.AggregationsServices#aggreger(org.json.JSONObject)
	 */
	@Override
	public Map<UUID, Reponse> aggreger(JSONObject collecte) {

		Map<UUID, Reponse> reponses = new HashMap<UUID, Reponse>();
		
		//
		 
		try {
		 
			
		/*
		 * 
		 * {
		"d": {
			"results": [{
				"Source": "actu.orange.fr",
				"Description": "Le titre PagesJaunes a terminé en forte hausse hier à la Bourse de Paris, gagnant 9,31 %, à 1,61 euro. Le cours a profité de l'annonce d'un accord avec les créanciers obligataires pour que le groupe puisse désigner un mandataire ad hoc. Il s'agit d ...",
				"Url": "http://actu.orange.fr/medias/le-cours-de-pagesjaunes-s-envole-sur-fond-de-possible-renegociation-de-la-dette-les-echos_1196508.html?display-posting=true",
				"Date": "2012-11-18T11:44:00Z",
				"ID": "970929f1-2f90-46d2-8e8e-bc4aa6aa3d3b",
				"__metadata": {
					"type": "NewsResult",
					"uri": "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/News?Query=':pagesjaunes'&Market='fr-FR'&Adult='Strict'&NewsSortBy='Date'&$skip=0&$top=1"
				},
				"Title": "Le cours de PagesJaunes s'envole sur fond de possible renégociation de la dette"
			}
		}
		 * 	
		 */
		
		  for (Iterator iterator1 = collecte.keys(); iterator1.hasNext();) {
		    Object cle1 = iterator1.next();
		    if(cle1.equals("d")) {
		    	JSONObject d = (JSONObject) collecte.get(String.valueOf(cle1));
		    	//System.out.println("\"" + cle1 + "\":" + d);
		    	for (Iterator iterator2 = d.keys(); iterator2.hasNext();) {
		    		Object cle2 = iterator2.next();
				    if(cle2.equals("results")) {
				    	JSONArray results = (JSONArray) d.get(String.valueOf(cle2));
				    	//System.out.println("\"" + cle2 + "\":" + results);
				    	for(int i = 0 ; i < results.length(); i++){
				    		
				    		Reponse reponse = new Reponse();
				    		reponse.setFournisseur(BING);
				    		
				    		reponse.setIdResultat(results.getJSONObject(i).getString("ID"));
				    		
				    		reponse.setTitre(results.getJSONObject(i).getString("Title"));
				    		reponse.setContenu(results.getJSONObject(i).getString("Description"));
				    		reponse.setLienSite(results.getJSONObject(i).getString("Url"));
				    		
				    		//2012-11-17T18:33:12Z
				    		Format sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
				    		reponse.setDatePublication((Date) sdf.parseObject(results.getJSONObject(i).getString("Date")));
				    		reponse.setLangue(null);
				    		reponse.setSource(results.getJSONObject(i).getString("Source"));
				    		
				    		reponses.put(reponse.getIdInterne(), reponse);
				    		
				    		//System.out.println(reponse.toString());
				    		
				    	}
				    }
		    	}
			}
		 }
		 
		} catch (JSONException e) { 
			e.printStackTrace(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reponses;
	}

}
