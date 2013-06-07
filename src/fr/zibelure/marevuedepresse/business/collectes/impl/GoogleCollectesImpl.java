package fr.zibelure.marevuedepresse.business.collectes.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import fr.zibelure.marevuedepresse.business.collectes.CollectesServices;
import fr.zibelure.marevuedepresse.tools.HttpRequestTools;

public class GoogleCollectesImpl implements CollectesServices {

	@Override
	public JSONObject search(String requete) {

		JSONObject json = null;
		try {
			String encodedRequest = URLEncoder.encode(requete, "UTF-8");
			//String url = "https://ajax.googleapis.com/ajax/services/search/web";
			//String url = "https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=pagesjaunes";
			String url = "http://ajax.googleapis.com/ajax/services/search/news";
			String parameters = "v=1.0&hl=fr&q=" + encodedRequest;
			//ned=fr <-- édition française
			Map<String, String> requestProperties = new HashMap<String, String>();
			requestProperties.put("Referer", "http://www.zibelure.fr");
			
			String response = HttpRequestTools.executeQuery(url, parameters, requestProperties);
			
			json = new JSONObject(response);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now have some fun with the results...
		return json;
	}

	
	
}
