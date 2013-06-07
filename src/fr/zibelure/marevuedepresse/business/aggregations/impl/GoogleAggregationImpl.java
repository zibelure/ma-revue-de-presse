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
public class GoogleAggregationImpl implements AggregationsServices {
	
	final static String GOOGLE = "GOOGLE";

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
			"responseData": {
			"results": [
			{
				"GsearchResultClass": "GnewsSearch",
				"clusterUrl": "http://news.google.com/news/story?ncl=d_ekY1W2rOpOyBMdWe9xHjzqGcYNM&hl=fr&ned=fr",
				"content": "Le ministre français de l&#39;<b>Economie</b>, Pierre Moscovici, a estimé samedi à Paris que la finance jouait &quot;un rôle crucial pour le développement de l&#39;<b>économie</b> réelle&quot;, estimant qu&#39;elle avait pris la place du charbon et de l&#39;acier des pères fondateurs de l <b>...</b>",
				"unescapedUrl": "http://www.europe1.fr/Economie/Moscovici-la-finance-cruciale-pour-l-economie-1529247/",
				"url": "http%3A%2F%2Fwww.europe1.fr%2FEconomie%2FMoscovici-la-finance-cruciale-pour-l-economie-1529247%2F",
				"title": "Moscovici: la finance, &quot;cruciale&quot; pour l&#39;<b>économie</b>",
				"titleNoFormatting": "Moscovici: la finance, &quot;cruciale&quot; pour l&#39;économie",
				"location": "",
				"publisher": "Europe1",
				"publishedDate": "Sat, 25 May 2013 07:21:48 -0700",
				"signedRedirectUrl": "http://news.google.com/news/url?sa=T&ct=fr/0-0-2&fd=S&url=http://www.europe1.fr/Economie/Moscovici-la-finance-cruciale-pour-l-economie-1529247/&cid=43982293070057&ei=8DGhUfjmJMaV8QPQ_gE&usg=AFQjCNE2dglq_W8FM-eB-jUn2WarjJL2OQ",
				"language": "fr",
				"relatedStories": [
				{
					"unescapedUrl": "http://www.cbanque.com/actu/37790/moscovici-la-finance-joue-un-role-crucial-dans-economie-reelle",
					"url": "http%3A%2F%2Fwww.cbanque.com%2Factu%2F37790%2Fmoscovici-la-finance-joue-un-role-crucial-dans-economie-reelle",
					"title": "Moscovici : la finance « joue un rôle crucial » dans l&#39;« <b>économie</b> <b>...</b>",
					"titleNoFormatting": "Moscovici : la finance « joue un rôle crucial » dans l&#39;« économie ...",
					"location": "",
					"publisher": "cBanque.com",
					"publishedDate": "Sat, 25 May 2013 07:23:19 -0700",
					"signedRedirectUrl": "http://news.google.com/news/url?sa=T&ct=fr/0-0-1&fd=S&url=http://www.cbanque.com/actu/37790/moscovici-la-finance-joue-un-role-crucial-dans-economie-reelle&cid=43982293070057&ei=8DGhUfjmJMaV8QPQ_gE&usg=AFQjCNHCy4LGD0PHqT6NG45cMWNPYXPGAA",
					"language": "fr"
				},
				{
					"unescapedUrl": "http://www.leparisien.fr/economie/la-finance-joue-un-role-crucial-pour-moscovici-25-05-2013-2834571.php",
					"url": "http%3A%2F%2Fwww.leparisien.fr%2Feconomie%2Fla-finance-joue-un-role-crucial-pour-moscovici-25-05-2013-2834571.php",
					"title": "La finance «joue un rôle crucial», estime Moscovici",
					"titleNoFormatting": "La finance «joue un rôle crucial», estime Moscovici",
					"location": "",
					"publisher": "Le Parisien",
					"publishedDate": "Sat, 25 May 2013 09:08:21 -0700",
					"signedRedirectUrl": "http://news.google.com/news/url?sa=T&ct=fr/0-0-0&fd=S&url=http://www.leparisien.fr/economie/la-finance-joue-un-role-crucial-pour-moscovici-25-05-2013-2834571.php&cid=43982293070057&ei=8DGhUfjmJMaV8QPQ_gE&usg=AFQjCNEhtKG8Pfmz_JqARjgOU9HMQIR0kA",
					"language": "fr"
				}
			]
			},
		 */
		 
		  for (Iterator iterator1 = collecte.keys(); iterator1.hasNext();) {
		    Object cle1 = iterator1.next();
		    if(cle1.equals("responseData")) {
		    	JSONObject responseData = (JSONObject) collecte.get(String.valueOf(cle1));
		    	//System.out.println("\"" + cle1 + "\":" + responseData);
		    	for (Iterator iterator2 = responseData.keys(); iterator2.hasNext();) {
		    		Object cle2 = iterator2.next();
				    if(cle2.equals("results")) {
				    	JSONArray results = (JSONArray) responseData.get(String.valueOf(cle2));
				    	//System.out.println("\"" + cle2 + "\":" + results);
				    	for(int i = 0 ; i < results.length(); i++){
				    		
				    		Reponse reponse = new Reponse();
				    		reponse.setFournisseur(GOOGLE);
				    		
				    		reponse.setTitre(results.getJSONObject(i).getString("titleNoFormatting"));
				    		reponse.setContenu(results.getJSONObject(i).getString("content"));
				    		reponse.setLienSite(results.getJSONObject(i).getString("unescapedUrl"));
				    		
				    		Format sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
				    		reponse.setDatePublication((Date) sdf.parseObject(results.getJSONObject(i).getString("publishedDate")));
				    		reponse.setLangue(results.getJSONObject(i).getString("language"));
				    		reponse.setSource(results.getJSONObject(i).getString("publisher"));
				    		
				    		if (results.getJSONObject(i).has("image"))  {
				    			JSONObject image = (JSONObject) results.getJSONObject(i).get("image");
				    			reponse.setLienImage(image.getString("url"));
				    		}
				    		
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
