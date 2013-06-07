package fr.zibelure.marevuedepresse.business.restitution.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;
import fr.zibelure.marevuedepresse.business.restitution.RestitutionServices;

public class RestitutionServicesImpl implements RestitutionServices {
	
	private static final String LOG_TAG = "Log : ";
    private final String mimeType = "text/html";
    private final String encoding = "utf-8";

	@Override
	public String chargerPageWeb(String url) {
		String pageWeb = "Oups... Page inaccessible";
		try {
			pageWeb = getPage(url);
		} catch (ClientProtocolException e) {
			Log.e(LOG_TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		return pageWeb;
	}


	/*
	 * getPage
	 */
    public static String getPage(String url) throws ClientProtocolException, IOException{
    	StringBuffer stringBuffer = new StringBuffer("");
    	BufferedReader bufferedReader = null;
    	
    	try{
    		//Cr�ation d'un DefaultHttpClient et un HttpGet permettant d'effectuer une requ�te HTTP de type GET
    		HttpClient httpClient = new DefaultHttpClient();
    		HttpGet httpGet = new HttpGet();
    		
    		//Cr�ation de l'URI et on l'affecte au HttpGet
    		URI uri = new URI(url);
    		httpGet.setURI(uri);
    		
    		//Execution du client HTTP avec le HttpGet
    		HttpResponse httpResponse = httpClient.execute(httpGet);
    		
    		//On r�cup�re la r�ponse dans un InputStream
    		InputStream inputStream = httpResponse.getEntity().getContent();
    		
    		//On cr�e un bufferedReader pour pouvoir stocker le r�sultat dans un string
    		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    		
    		//On lit ligne � ligne le bufferedReader pour le stocker dans le stringBuffer
    		String ligneCodeHTML = bufferedReader.readLine();
    		while (ligneCodeHTML != null){
    			stringBuffer.append(ligneCodeHTML);
    			stringBuffer.append("\n");
    			ligneCodeHTML = bufferedReader.readLine();
    		}    		
    		
    	}catch (Exception e){
    		Log.e(LOG_TAG, e.getMessage());
    	}finally{
    		//Dans tous les cas on ferme le bufferedReader s'il n'est pas null
    		if (bufferedReader != null){
    			try{
    				bufferedReader.close();
    			}catch(IOException e){
    	    		Log.e(LOG_TAG, e.getMessage());    				
    			}
    		}
    	}
    	
    	//On retourne le stringBuffer
    	return stringBuffer.toString();
    }
}
