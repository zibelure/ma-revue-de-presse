/**
 * 
 */
package fr.zibelure.marevuedepresse.business.aggregations.impl;

import java.text.Format;
import java.text.ParseException;
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
public class GooglePlusAggregationImpl implements AggregationsServices {
	
	final static String GOOGLEPLUS = "GOOGLE+";

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
		 
		try {
			
		/*
		 * {
			"kind": "plus#activityFeed",
			"etag": "\"uYrzoZsxQM1LGjix0pfxxKDsvgg/oth-Gq31Rl3pXjS7D7mIark4NOA\"",
			"nextPageToken": "CikIq84vEiGJ2hSJ2hShpB77pif7pieb2SjYnV-bz4YCte69ArnWxgIYAgooEibezgHnoHGx_ZAB3aepAt2nqQLEosACwuOEA4S-pwP-rbEDoa7tAwonEiXQzw_olyjK1lqW5MsBl97ZAajC5gLWnogDl-DJA5fgyQO8ipcECioSKOqstATqrLQEjNH7BIzR-wTihskFmJDmBZiQ5gWZ_rkImf65CIGQgQkKMAjblw8SKIfc7wHio_gB-ICrAviAqwLii8YCuc3yA6-BgwSeuZoE1NGdBOOEzQQYARCI8ZeNBRih_aiNBSIA",
			"selfLink": "https://www.googleapis.com/plus/v1/activities?key=AIzaSyA4A8qa-z561QXl6F4sLhBfwpLDL5S0Ox8&language=fr&maxResults=10&query=pagesjaunes",
			"title": "Google+ Activities Search Results",
			"updated": "2013-06-01T18:34:09.312Z",
			"items": [
				{
					"kind": "plus#activity",
					"etag": "\"uYrzoZsxQM1LGjix0pfxxKDsvgg/kuq3EDcjDKp-sRJTlN317nsj5n0\"",
					"title": "Comme PagesJaunes, Google assure la présence des commerces sur son réseau. Restaurants, hôtels et magasins...",
					"published": "2013-06-01T18:34:09.312Z",
					"updated": "2013-06-01T18:34:09.312Z",
					"id": "z13eh1wpspaphjb2z221wfa4yqvbibhpa04",
					"url": "https://plus.google.com/109303741445540613219/posts/GvnzBfjmZoh",
					"actor": {
						"id": "109303741445540613219",
						"displayName": "Netcreative",
						"url": "https://plus.google.com/109303741445540613219",
						"image": {
							"url": "https://lh3.googleusercontent.com/--eJ_a14jzXY/AAAAAAAAAAI/AAAAAAAAABs/gYlMqYsFWr0/photo.jpg?sz=50"
						}
					},
					"verb": "post",
					"object": {
						"objectType": "note",
						"content": "Comme PagesJaunes, Google assure la présence des commerces sur son réseau. Restaurants, hôtels et magasins disposent d&#39;une carte de visite comportant nom, adresse et numéro de téléphone dans le réseau social Google +.",
						"url": "https://plus.google.com/109303741445540613219/posts/GvnzBfjmZoh",
						"replies": {
							"totalItems": 0,
							"selfLink": "https://www.googleapis.com/plus/v1/activities/z13eh1wpspaphjb2z221wfa4yqvbibhpa04/comments"
						},
						"plusoners": {
							"totalItems": 1,
							"selfLink": "https://www.googleapis.com/plus/v1/activities/z13eh1wpspaphjb2z221wfa4yqvbibhpa04/people/plusoners"
						},
						"resharers": {
							"totalItems": 0,
							"selfLink": "https://www.googleapis.com/plus/v1/activities/z13eh1wpspaphjb2z221wfa4yqvbibhpa04/people/resharers"
						},
						"attachments": [
						{
							"objectType": "article",
							"displayName": "La guerre des commerciaux fait rage entre Pages Jaunes et Google",
							"url": "http://mobile.lemonde.fr/technologies/article/2013/05/28/la-guerre-des-commerciaux-fait-rage-entre-pages-jaunes-et-google_3419416_651865.html",
							"image": {
								"url": "https://lh6.googleusercontent.com/proxy/xzyitWlwEtl3o1yJ1a3QEBkpUOpZIo_k4oZYzSD1V5yM1NuKxOR80mf-i0o5c265X-ltFfrJfGVuUid2SEdqdZvCvuXhquZ09-B8DrFkUv3Js0Nm0agzVvboflwN7q3HcLrvHYZAxpmSGxUEhd_dIXut6Cx7Q7cuaYUCEk6KlSRr5VGHlWl3sUCVIQ=w125-h125",
								"type": "image/jpeg",
								"height": 125,
								"width": 125
							},
							"fullImage": {
								"url": "http://s1.lemde.fr/image/2013/05/27/310x155/3417871_3_0313_le-siege-de-google-a-dublin_bc0e6b14d4cf6944837a34ca8f4e0215.jpg",
								"type": "image/jpeg"
							}
						}
					]
				},
				"provider": {
					"title": "Google+"
				},
				"access": {
					"kind": "plus#acl"
				}
			}, ...
		 */
		 
		  for (Iterator iterator1 = collecte.keys(); iterator1.hasNext();) {
		    Object cle1 = iterator1.next();
		    if(cle1.equals("items")) {
		    	JSONArray results = (JSONArray) collecte.get(String.valueOf(cle1));
		    	//System.out.println("\"" + cle2 + "\":" + results);
		    	for(int i = 0 ; i < results.length(); i++){
		    		Reponse reponse = new Reponse();
		    		reponse.setFournisseur(GOOGLEPLUS);
		    		
		    		reponse.setTitre(results.getJSONObject(i).getString("title"));
		    		//Format sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		    		Format sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.FRANCE);
		    		//2013-06-01T18:34:09.312Z
		    		String datePublication = results.getJSONObject(i).getString("updated");
		    		if (datePublication != null) {
		    			try {
		    	            reponse.setDatePublication((Date) sdf.parseObject(datePublication));	    	            
		    	        } catch (ParseException e) {
		    	            e.printStackTrace();
		    	        }
		    		}
		    		if (results.getJSONObject(i).has("actor"))  {
		    			JSONObject actor = (JSONObject) results.getJSONObject(i).get("actor");
		    			reponse.setSource(actor.getString("displayName"));
		    			if (actor.getJSONObject("image") != null) {
		    				JSONObject image = (JSONObject) actor.getJSONObject("image");
		    				reponse.setLienImage(image.getString("url"));
		    			}
		    		}
		    		
		    		if (results.getJSONObject(i).has("object"))  {
		    			JSONObject object = (JSONObject) results.getJSONObject(i).get("object");
		    			reponse.setContenu(object.getString("content"));
		    			reponse.setLienSite(results.getJSONObject(i).getString("url"));
		    		}

		    		reponse.setLangue("fr");
		    		
		    		reponses.put(reponse.getIdInterne(), reponse);
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
