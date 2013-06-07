package fr.zibelure.marevuedepresse.business.collectes.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import fr.zibelure.marevuedepresse.business.collectes.CollectesServices;
import fr.zibelure.marevuedepresse.tools.HttpRequestTools;

public class FacebookCollectesImpl implements CollectesServices {

	@Override
	public JSONObject search(String requete) {
		// TODO Auto-generated method stub
		// The request also includes the userip parameter which provides the end
		// user's IP address. Doing so will help distinguish this legitimate
		// server-side traffic from traffic which doesn't come from an end-user.
		JSONObject json = null;
		try {
			String encodedRequest = URLEncoder.encode(requete, "UTF-8");
			String access_token = "CAACEdEose0cBANefXjSUfgSF0nKnsxZBEBwJbDV1az7AoGZCGt3lmrw7ZC95PnR44ZB01TdszIAe9GNSXggHyj9sewrnvvZAx4RJMvZA9OTPsDRZCu4TtFU9q1iIpPBBwrD52oz7JtVKpMC7M1gn8q4wYAT8wkbYTHb6uPm7P2KnwZDZD";

			//Génération du token
			//oauth/access_token?client_id={app-id}&client_secret={app-secret}&grant_type=client_credentials
			
			
			
			String url = "https://graph.facebook.com/search";
			String parameters = "q="
					+ requete
					+ "&type=post&access_token=" + access_token;

			Map<String, String> requestProperties = new HashMap<String, String>();
			
			requestProperties.put("Referer", "http://www.zibelure.fr");
			requestProperties.put("Accept-Language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");
			requestProperties.put("Connection", "keep-alive");
			//requestProperties.put("Cookie", "WT_NVR=0=/:1=dataset|checkout|account:2=dataset/bing; WT_FPC=id=28bfbdf66b2e0664f151368940691780:lv=1368941500502:ss=1368940691780");
			requestProperties.put("Referer", "http://www.zibelure.fr");
			//requestProperties.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");

			String response = HttpRequestTools.executeQuery(url, parameters,
					requestProperties);

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
