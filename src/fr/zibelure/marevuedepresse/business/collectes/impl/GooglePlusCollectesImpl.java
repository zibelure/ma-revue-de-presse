package fr.zibelure.marevuedepresse.business.collectes.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import fr.zibelure.marevuedepresse.business.collectes.CollectesServices;
import fr.zibelure.marevuedepresse.tools.HttpRequestTools;

public class GooglePlusCollectesImpl implements CollectesServices {

	@Override
	public JSONObject search(String requete) {

		JSONObject json = null;
		try {
			String encodedRequest = URLEncoder.encode(requete, "UTF-8");
			
			//https://www.googleapis.com/plus/v1/activities?key=AIzaSyA4A8qa-z561QXl6F4sLhBfwpLDL5S0Ox8&language=fr&maxResults=10&query=pagesjaunes
			
			String url = "https://www.googleapis.com/plus/v1/activities";
			String key="AIzaSyA4A8qa-z561QXl6F4sLhBfwpLDL5S0Ox8";
			int maxResults=10;
			String language = "fr";
			StringBuilder parameters = new StringBuilder();
			parameters.append("key=" + key);
			parameters.append("&maxResults=" + maxResults);
			parameters.append("&language=" + language);
			parameters.append("&query=" + encodedRequest);
			Map<String, String> requestProperties = new HashMap<String, String>();
			requestProperties.put("Referer", "http://www.zibelure.fr");
			
			String response = HttpRequestTools.executeQuery(url, parameters.toString(), requestProperties);
			
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
