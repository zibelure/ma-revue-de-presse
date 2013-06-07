package fr.zibelure.marevuedepresse.business.evaluations.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import fr.zibelure.marevuedepresse.business.evaluations.EvaluationsServices;
import fr.zibelure.marevuedepresse.model.Reponse;
import fr.zibelure.marevuedepresse.tools.StringTools;

public class EvaluationsImpl implements EvaluationsServices {

//	@Override
//	public Map<UUID, Reponse> evaluer(Map<UUID, Reponse> reponses, String request) {
//		
//		Map<UUID, Reponse> reponsesEvaluees = new HashMap<UUID, Reponse>();
//		int score;
//		
//		for (Iterator it = reponses.keySet().iterator(); it.hasNext();) {
//			score = 0;
//			UUID uuid = (UUID) it.next();
//			Reponse reponseEvaluee = (Reponse) reponses.get(uuid);
//			String titre = reponseEvaluee.getTitre();
//			String contenu = reponseEvaluee.getContenu();
//
//			if(titre != null && Pattern.compile(".*" + request.toUpperCase() + ".*").matcher(titre.toUpperCase()).find()) {
//				score=score+2;
//			}
//			if(contenu != null && Pattern.compile(".*" + request.toUpperCase() + ".*").matcher(contenu.toUpperCase()).find()) {
//				score=score+1;
//			}
//			reponseEvaluee.setScore(score);
//			//System.out.println(reponseEvaluee.toString());
//			reponsesEvaluees.put(uuid, reponseEvaluee);
//		}		
//		return reponsesEvaluees;
//	}
	
	HashSet<String> reponsesDedoublonnees;
	
	@Override
	public Map<UUID, Reponse> evaluer(Map<UUID, Reponse> reponses, String request) {
		Map<UUID, Reponse> reponsesEvaluees = new HashMap<UUID, Reponse>();
		reponsesDedoublonnees = new HashSet<String>();
		for (Iterator it = reponses.keySet().iterator(); it.hasNext();) {
			UUID uuid = (UUID) it.next();
			//Récupération de la réponse
			Reponse reponseEvaluee = (Reponse) reponses.get(uuid);
			//Dédoublonnage de la réponse en cours
			reponseEvaluee = dedoublonner(reponseEvaluee);
			//Calcul du score de la réponse en cours
			if (reponseEvaluee != null) reponseEvaluee = calculerScore(reponseEvaluee, request);
			if (reponseEvaluee != null) reponsesEvaluees.put(uuid, reponseEvaluee);
		}
		if(reponsesEvaluees.isEmpty()) {
			Reponse pasDeReponse = new Reponse();
			pasDeReponse.setTitre("Aucune réponse trouvée");
			pasDeReponse.setContenu("Sujet recherché : " + request);
			reponsesEvaluees.put(pasDeReponse.getIdInterne(), pasDeReponse);
		}
		return reponsesEvaluees;
	}
	
	
	/*
	 * calculerScore
	 */
	private Reponse calculerScore(Reponse reponseScoreCalcule, String request) {
		int score = 0;
		String titre = reponseScoreCalcule.getTitre();
		String contenu = reponseScoreCalcule.getContenu();

		if(titre != null && Pattern.compile(".*" + StringTools.formatStringManual(request.toLowerCase()) + ".*").matcher(StringTools.formatStringManual(titre.toLowerCase())).find()) {
			score=score+2;
		}
		if(contenu != null && Pattern.compile(".*" + StringTools.formatStringManual(request.toLowerCase()) + ".*").matcher(StringTools.formatStringManual(contenu.toLowerCase())).find()) {
			score=score+1;
		}
		//Si le score est à zéro, on élimine la réponse
		if(score == 0) {
			reponseScoreCalcule = null;
		} else { //Sinon on prend en compte la réponse
			reponseScoreCalcule.setScore(score);
		}
		//System.out.println(reponseEvaluee.toString());
		return reponseScoreCalcule;
	}

	
	/*
	 * dedoublonner
	 */
	private Reponse dedoublonner(Reponse reponseADedoublonner) {
		if(!reponsesDedoublonnees.contains(reponseADedoublonner.getTitre())) {
			reponsesDedoublonnees.add(reponseADedoublonner.getTitre());
			return reponseADedoublonner;
		}
		return null;
	}

}
