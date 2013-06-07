package fr.zibelure.marevuedepresse.business.evaluations;

import java.util.Map;
import java.util.UUID;

import fr.zibelure.marevuedepresse.model.Reponse;

public interface EvaluationsServices {
	
	public Map<UUID, Reponse> evaluer(Map<UUID, Reponse> reponses, String request); 
 
}
