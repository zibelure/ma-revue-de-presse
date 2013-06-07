package fr.zibelure.marevuedepresse.business.recherches;

import java.util.Map;
import java.util.UUID;

import fr.zibelure.marevuedepresse.model.Reponse;

public interface RecherchesServices {
	
	public Map<UUID, Reponse> rechercher(String requete);

}
