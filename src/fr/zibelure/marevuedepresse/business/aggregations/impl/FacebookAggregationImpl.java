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
public class FacebookAggregationImpl implements AggregationsServices {

	final static String FACEBOOK = "FACEBOOK";

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
		 * "data": [
				{
				"id": "100003604555263_311069512356531",
				"from": {
					"name": "Thomas Paice",
					"id": "100003604555263"
				},
				"message": "To Nick Flay Happy Birthday The Beatles.",
				"picture": "https://fbexternal-a.akamaihd.net/safe_image.php?d=AQAwV2gocmOANSxR&w=130&h=130&url=http%3A%2F%2Fi1.ytimg.com%2Fvi%2FPov2sK_C3sY%2Fhqdefault.jpg%3Ffeature%3Dog",
				"link": "http://youtu.be/Pov2sK_C3sY",
				"source": "http://www.youtube.com/v/Pov2sK_C3sY?autohide=1&version=3&autoplay=1",
				"name": "The Beatles- Happy Birthday",
				"description": "The Beatles- Happy Birthday Lyrics: You say it's your birthday It's my birthday too, yeah They say it's your birthday We're gonna have a good time I'm glad i...",
				"icon": "https://fbstatic-a.akamaihd.net/rsrc.php/v2/yj/r/v2OnaTyTQZE.gif",
				"privacy": {
					"value": ""
				},
				"type": "video",
				"application": {
					"name": "iOS",
					"id": "213546525407071"
				},
				"created_time": "2013-05-16T09:34:53+0000",
				"updated_time": "2013-05-16T11:12:42+0000",
				"comments": {
					"data": [
					{
					"id": "311069512356531_1453423",
					"from": {
						"name": "Thomas Paice",
						"id": "100003604555263"
					},
					"message": "Nick Flay",
					"message_tags": [
						{
						"id": "1257826808",
						"name": "Nick Flay",
						"type": "user",
						"offset": 0,
						"length": 9
						}
					],
					"can_remove": false,
					"created_time": "2013-05-16T10:30:33+0000",
					"like_count": 0,
					"user_likes": false
					},
					{
						"id": "311069512356531_1453518",
						"from": {
						"name": "Nick Flay",
						"id": "1257826808"
					},
					"message": "Very nice ta. Just sat in the sun.",
					"can_remove": false,
					"created_time": "2013-05-16T11:12:42+0000",
					"like_count": 0,
					"user_likes": false
					}
					],
					"paging": {
						"cursors": {
							"after": "Mg==",
							"before": "MQ=="
						}
					}
				}
		},
		 * ...
		 */
		try {

			for (Iterator iterator1 = collecte.keys(); iterator1.hasNext();) {
				Object cle1 = iterator1.next();
				if (cle1.equals("data")) {
					JSONArray results = (JSONArray) collecte.get(String.valueOf(cle1));
					// System.out.println("\"" + cle2 + "\":" + results);
					for (int i = 0; i < results.length(); i++) {

						Reponse reponse = new Reponse();
						reponse.setFournisseur(FACEBOOK);

						reponse.setTitre(results.getJSONObject(i).getString("name"));
						reponse.setContenu(results.getJSONObject(i).getString("description"));
						reponse.setLienSite(results.getJSONObject(i).getString("link"));

						Format sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
						reponse.setDatePublication((Date) sdf.parseObject(results.getJSONObject(i).getString("created_at")));
						reponse.setLangue(results.getJSONObject(i).getString("iso_language_code"));

						if (results.getJSONObject(i).has("from"))  {
			    			JSONObject from = (JSONObject) results.getJSONObject(i).get("from");
			    			reponse.setSource(from.getString("name"));
			    		}

						if (results.getJSONObject(i).has("picture")) {
							reponse.setLienImage(results.getJSONObject(i).getString("picture"));
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
