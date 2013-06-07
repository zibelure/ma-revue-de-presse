package fr.zibelure.marevuedepresse.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpRequestTools {

	/*
	 * executeQuery
	 */
	public static String executeQuery(String url, String parameters,
			Map<String, String> requestProperties) {

		URL _url;
		HttpURLConnection connection = null;
		try {
			_url = new URL(url + "?" + parameters);

			connection = (HttpURLConnection) _url.openConnection();
			connection.setConnectTimeout(10000);

			for (String mapKey : requestProperties.keySet()) {
				connection.setRequestProperty(mapKey,
						requestProperties.get(mapKey));
				// System.out.println(mapKey + " : " +
				// requestProperties.get(mapKey));
			}

			return getInputStreamContent(connection.getInputStream());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				System.out.println("Code retour http: "
						+ connection.getResponseCode());
				System.out.println("Message d'erreur : "
						+ getInputStreamContent(connection.getErrorStream()));
				System.out.println("<=======");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			connection.disconnect();
		}
		return null;
	}

	/*
	 * decodeStream
	 */
	public static Bitmap decodeStream(String lienImage) {
		Bitmap image;
		try {
			URL aURL = new URL(lienImage);
			image = BitmapFactory.decodeStream(aURL.openStream());
			return image;
		} catch (Exception ex) {
			Log.w("MonApp", "Echec de chargement de l'image !", ex);
		}
		return null;
	}

	/*
	 * getInputStreamContent
	 */
	public static String getInputStreamContent(InputStream inputStream) {
		StringBuilder builder = new StringBuilder();
		if (inputStream != null) {
			InputStreamReader isr = null;
			BufferedReader reader = null;
			try {
				String line;
				isr = new InputStreamReader(inputStream);
				reader = new BufferedReader(isr);
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					isr.close();
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

}
