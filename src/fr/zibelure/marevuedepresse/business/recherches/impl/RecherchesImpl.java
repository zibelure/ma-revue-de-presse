package fr.zibelure.marevuedepresse.business.recherches.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import fr.zibelure.marevuedepresse.business.aggregations.AggregationsServices;
import fr.zibelure.marevuedepresse.business.aggregations.impl.BingAggregationImpl;
import fr.zibelure.marevuedepresse.business.aggregations.impl.GoogleAggregationImpl;
import fr.zibelure.marevuedepresse.business.aggregations.impl.GooglePlusAggregationImpl;
import fr.zibelure.marevuedepresse.business.aggregations.impl.TwitterAggregationImpl;
import fr.zibelure.marevuedepresse.business.collectes.CollectesServices;
import fr.zibelure.marevuedepresse.business.collectes.impl.BingCollectesImpl;
import fr.zibelure.marevuedepresse.business.collectes.impl.GoogleCollectesImpl;
import fr.zibelure.marevuedepresse.business.collectes.impl.GooglePlusCollectesImpl;
import fr.zibelure.marevuedepresse.business.collectes.impl.TwitterCollectesImpl;
import fr.zibelure.marevuedepresse.business.evaluations.EvaluationsServices;
import fr.zibelure.marevuedepresse.business.evaluations.impl.EvaluationsImpl;
import fr.zibelure.marevuedepresse.business.recherches.RecherchesServices;
import fr.zibelure.marevuedepresse.model.Reponse;
import fr.zibelure.marevuedepresse.tools.NormalizeURL;

public class RecherchesImpl implements RecherchesServices {

	@Override
	public Map<UUID, Reponse> rechercher(String requete) {
		
		String req = NormalizeURL.normalizer(requete);
		
		//String req = Pattern.compile("\\s+").matcher(requete).replaceAll(" ").trim();
		
		Map<UUID, Reponse> reponses = new HashMap<UUID, Reponse>();
		
		//Collecte des données
		CollectesServices googleCollectesServices = new GoogleCollectesImpl(); 
		JSONObject googleCollecte = googleCollectesServices.search(req);
//		System.out.println(googleCollecte.toString());
		
		CollectesServices bingCollectesServices = new BingCollectesImpl(); 
		JSONObject bingCollecte = bingCollectesServices.search(req);
//		System.out.println(bingCollecte.toString());
		
		CollectesServices twitterCollectesServices = new TwitterCollectesImpl(); 
		JSONObject twitterCollecte = twitterCollectesServices.search(req);
//		System.out.println(twitterCollecte.toString());

		//CollectesServices facebookCollectesServices = new FacebookCollectesImpl(); 
		//JSONObject facebookCollecte = facebookCollectesServices.search(req);
		//System.out.println(facebookCollecte.toString());

		CollectesServices googlePlusCollectesServices = new GooglePlusCollectesImpl(); 
		JSONObject googlePlusCollecte = googlePlusCollectesServices.search(req);
		//System.out.println(facebookCollecte.toString());
		
		//Aggregation des données
		AggregationsServices googleAggregation = new GoogleAggregationImpl();
		Map<UUID, Reponse> reponsesGoogle = googleAggregation.aggreger(googleCollecte);

		AggregationsServices bingAggregation = new BingAggregationImpl();
		Map<UUID, Reponse> reponsesBing = bingAggregation.aggreger(bingCollecte);

		AggregationsServices twitterAggregation = new TwitterAggregationImpl();
		Map<UUID, Reponse> reponsesTwitter = twitterAggregation.aggreger(twitterCollecte);

		//AggregationsServices facebookAggregation = new FacebookAggregationImpl();
		//Map<UUID, Reponse> reponsesFacebook = facebookAggregation.aggreger(facebookCollecte);
		
		AggregationsServices googlePlusAggregation = new GooglePlusAggregationImpl();
		Map<UUID, Reponse> reponsesGooglePlus = googlePlusAggregation.aggreger(googlePlusCollecte);
		
		reponses.putAll(reponsesGoogle);
		reponses.putAll(reponsesBing);
		reponses.putAll(reponsesTwitter);
		//reponses.putAll(reponsesFacebook);
		reponses.putAll(reponsesGooglePlus);
		
		//Evaluation des données
		EvaluationsServices evaluation = new EvaluationsImpl();
		
		//System.out.println("Question : " + requete);
		//System.out.println("Nombre de réponses : " + reponses.size());
		
		return evaluation.evaluer(reponses, requete);
	}

	
	
}
