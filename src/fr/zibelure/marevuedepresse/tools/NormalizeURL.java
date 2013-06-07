package fr.zibelure.marevuedepresse.tools;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

	
/**
 * - Covert the scheme and host to lowercase (done by java.net.URL)
 * - Add the port number.
 * - Remove the fragment (the part after the #).
 * - Remove trailing slash.
 * - Sort the query string params.
 * - Remove some query string params like "utm_*" and "*session*".
 */
public class NormalizeURL
{
	
	public static String normalizer(String raw) {
	    StringBuilder sb = new StringBuilder();
	    Scanner scanner = new Scanner(raw);
	    while (scanner.hasNext()) {
	        sb.append(scanner.next());
	        if(scanner.hasNext()) {
	        	sb.append('+');
	        }
	    }
//	    sb.deleteCharAt(sb.length() - 1);
	    return sb.toString();
	}
	
    public static String normalize(final String taintedURL)
    {
        final URL url;
        try
        {
            url = new URL(taintedURL);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException("Invalid URL: " + taintedURL);
        }

        final String path = url.getPath().replace("/$", "");
        final SortedMap<String, String> params = createParameterMap(url.getQuery());
        final int port = url.getPort();
        final String queryString;

        if (params != null)
        {
            // Some params are only relevant for user tracking, so remove the most commons ones.
            for (final Map.Entry<String, String> entry : params.entrySet())
            {
                final String key = entry.getKey();
                if (key.startsWith("utm_") || key.contains("session"))
                {
                    params.remove(key);
                }
            }
            queryString = "?" + canonicalize(params);
        }
        else
        {
            queryString = "";
        }

        return url.getProtocol() + "://" + url.getHost()
            + (port != -1 && port != 80 ? ":" + port : "")
            + path + queryString;
    }

    /**
     * Takes a query string, separates the constituent name-value pairs, and
     * stores them in a SortedMap ordered by lexicographical order.
     * @return Null if there is no query string.
     */
    private static SortedMap<String, String> createParameterMap(final String queryString)
    {
        if (queryString == null || "".equals(queryString))
        {
            return null;
        }

        final String[] pairs = queryString.split("&");
        final Map<String, String> params = new HashMap<String, String>(pairs.length);

        for (final String pair : pairs)
        {
            if (pair.length() < 1)
            {
                continue;
            }

            String[] tokens = pair.split("=", 2);
            for (int j = 0; j < tokens.length; j++)
            {
                try
                {
                    tokens[j] = URLDecoder.decode(tokens[j], "UTF-8");
                }
                catch (UnsupportedEncodingException ex)
                {
                    ex.printStackTrace();
                }
            }
            switch (tokens.length)
            {
                case 1:
                {
                    if (pair.charAt(0) == '=')
                    {
                        params.put("", tokens[0]);
                    }
                    else
                    {
                        params.put(tokens[0], "");
                    }
                    break;
                }
                case 2:
                {
                    params.put(tokens[0], tokens[1]);
                    break;
                }
            }
        }

        return new TreeMap<String, String>(params);
    }

    /**
     * Canonicalize the query string.
     *
     * @param sortedParamMap Parameter name-value pairs in lexicographical order.
     * @return Canonical form of query string.
     */
    private static String canonicalize(final SortedMap<String, String> sortedParamMap)
    {
        if (sortedParamMap == null || sortedParamMap.isEmpty())
        {
            return "";
        }

        final StringBuffer sb = new StringBuffer(350);
        final Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet().iterator();

        while (iter.hasNext())
        {
            final Map.Entry<String, String> pair = iter.next();
            sb.append(percentEncodeRfc3986(pair.getKey()));
            sb.append('=');
            sb.append(percentEncodeRfc3986(pair.getValue()));
            if (iter.hasNext())
            {
                sb.append('&');
            }
        }

        return sb.toString();
    }

    /**
     * Percent-encode values according the RFC 3986. The built-in Java URLEncoder does not encode
     * according to the RFC, so we make the extra replacements.
     *
     * @param string Decoded string.
     * @return Encoded string per RFC 3986.
     */
    private static String percentEncodeRfc3986(final String string)
    {
        try
        {
            return URLEncoder.encode(string, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        }
        catch (UnsupportedEncodingException e)
        {
            return string;
        }
    }
}