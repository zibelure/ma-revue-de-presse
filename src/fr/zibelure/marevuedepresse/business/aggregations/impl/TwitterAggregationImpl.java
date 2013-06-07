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
public class TwitterAggregationImpl implements AggregationsServices {

	final static String TWITTER = "TWITTER";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.crapouille.services.aggregations.AggregationsServices#aggreger(java
	 * .util.List)
	 */
	@Override
	public Map<UUID, Reponse> aggreger(List<JSONObject> collectes) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.crapouille.services.aggregations.AggregationsServices#aggreger(org
	 * .json.JSONObject)
	 */
	@Override
	public Map<UUID, Reponse> aggreger(JSONObject collecte) {

		Map<UUID, Reponse> reponses = new HashMap<UUID, Reponse>();
		/*
		 * "results": [
				{
				"created_at": "Fri, 24 May 2013 12:33:20 +0000",
				"entities": {
				"hashtags": [],
				"urls": [],
				"user_mentions": []
				},
				"from_user": "markknoller",
				"from_user_id": 31127446,
				"from_user_id_str": "31127446",
				"from_user_name": "Mark Knoller",
				"geo": null,
				"id": 337909178419007500,
				"id_str": "337909178419007488",
				"iso_language_code": "en",
				"metadata": {
				"recent_retweets": 5,
				"result_type": "popular"
				},
				"profile_image_url": "http://a0.twimg.com/profile_images/137394623/knoller_normal.jpg",
				"profile_image_url_https": "https://si0.twimg.com/profile_images/137394623/knoller_normal.jpg",
				"source": "&lt;a href=&quot;http://twitter.com/&quot;&gt;web&lt;/a&gt;",
				"text": "There'll be no traditional flyover at the Annapolis commencement by the Blue Angels. Their budget was cut by the sequester."
				},
		 * ...
		 */
		try {

			for (Iterator iterator1 = collecte.keys(); iterator1.hasNext();) {
				Object cle1 = iterator1.next();
				if (cle1.equals("results")) {
					JSONArray results = (JSONArray) collecte.get(String.valueOf(cle1));
					// System.out.println("\"" + cle2 + "\":" + results);
					for (int i = 0; i < results.length(); i++) {

						Reponse reponse = new Reponse();
						reponse.setFournisseur(TWITTER);

						reponse.setTitre(results.getJSONObject(i).getString("from_user_name"));
						reponse.setContenu(results.getJSONObject(i).getString("text"));
						//reponse.setLienSite(results.getJSONObject(i).getString("source"));
						reponse.setLienSite("");
						
						Format sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
						reponse.setDatePublication((Date) sdf.parseObject(results.getJSONObject(i).getString("created_at")));
						reponse.setLangue(results.getJSONObject(i).getString("iso_language_code"));
						reponse.setSource(results.getJSONObject(i).getString("from_user_name"));

						if (results.getJSONObject(i).has("profile_image_url")) {
							reponse.setLienImage(results.getJSONObject(i).getString("profile_image_url"));
						}

						/*
						 * 							
							"entities": {
								"hashtags": [],
								"urls": [
								{
									"url": "http://t.co/h5MFVplJSM",
									"expanded_url": "http://ow.ly/lriKH",
									"display_url": "ow.ly/lriKH",
									"indices": [83,	105]
									}
								],
								"user_mentions": []
							}
						 */
						if (results.getJSONObject(i).has("entities"))  {
							if (results.getJSONObject(i).has("urls"))  {
								JSONObject urls = (JSONObject) results.getJSONObject(i).get("urls");
				    			reponse.setLienImage(urls.getString("url"));
							}
			    		}
						
						reponses.put(reponse.getIdInterne(), reponse);

						// System.out.println(reponse.toString());
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
