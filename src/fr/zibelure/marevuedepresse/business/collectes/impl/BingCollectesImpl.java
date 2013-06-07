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

public class BingCollectesImpl implements CollectesServices {

	@Override
	public JSONObject search(String requete) {
		// TODO Auto-generated method stub
		// The request also includes the userip parameter which provides the end
		// user's IP address. Doing so will help distinguish this legitimate
		// server-side traffic from traffic which doesn't come from an end-user.
		JSONObject json = null;
		try {
			String encodedRequest = URLEncoder.encode(requete, "UTF-8");

			String url = "https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/News";
			String parameters = "Query=%27%3a"
					+ requete
					+ "%27&Market=%27fr-FR%27&Adult=%27Strict%27&NewsSortBy=%27Date%27&$top=15&$format=json";

			String accountKey = "0odmlt3zonzEfhr2nYj0mHjPQKpABSuaHLAFWXXEdao=";
			byte[] accountKeyBytes = Base64.encode(
					("ignored:" + accountKey).getBytes(), Base64.DEFAULT);
			String accountKeyEnc = new String(accountKeyBytes);

			Map<String, String> requestProperties = new HashMap<String, String>();
			requestProperties.put("Referer", "http://www.zibelure.fr");
			requestProperties.put("Authorization", "Basic " + accountKeyEnc);
			//requestProperties.put("Authorization", "Basic NzRjZDUwMjktNWFkYy00ZDVmLTliYjgtY2UxODY0YTliMWI5OjBvZG1sdDN6b256RWZocjJuWWowbUhqUFFLcEFCU3VhSExBRldYWEVkYW89");
			
			
			
			//System.out.println("non calculé ==> Basic NzRjZDUwMjktNWFkYy00ZDVmLTliYjgtY2UxODY0YTliMWI5OjBvZG1sdDN6b256RWZocjJuWWowbUhqUFFLcEFCU3VhSExBRldYWEVkYW89");
			//System.out.println("    calculé ==> Basic " + accountKeyEnc);

			//requestProperties.put("Accept","application/atomsvc+xml;q=0.8, application/json;q=0.5, */*;q=0.1");
			//requestProperties.put("Accept-Charset", "utf-8");
			//requestProperties.put("Accept-Encoding", "gzip,deflate,sdch");
			
			
			requestProperties.put("Accept-Language", "fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4");

			requestProperties.put("Connection", "keep-alive");
			//requestProperties.put("Cookie", "WT_NVR=0=/:1=dataset|checkout|account:2=dataset/bing; WT_FPC=id=28bfbdf66b2e0664f151368940691780:lv=1368941500502:ss=1368940691780");
			
			requestProperties.put("Host", "api.datamarket.azure.com");
			requestProperties.put("Origin", "https://datamarket.azure.com");
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
