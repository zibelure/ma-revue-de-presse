/**
 * 
 */
package fr.zibelure.marevuedepresse.business.aggregations.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import fr.zibelure.marevuedepresse.business.aggregations.AggregationsServices;
import fr.zibelure.marevuedepresse.model.Reponse;

/**
 * @author anadot
 *
 */
public class AggregationsImpl implements AggregationsServices {

	/* (non-Javadoc)
	 * @see fr.crapouille.services.aggregations.AggregationsServices#aggreger(java.util.List)
	 */
	@Override
	public Map<UUID, Reponse> aggreger(List<JSONObject> collectes) {
		
		Map<UUID, Reponse> reponses = new HashMap<UUID, Reponse>();
		
		for (JSONObject jsonObject : collectes) {
			
		}
		
		
		return reponses;
	}

	@Override
	public Map<UUID, Reponse> aggreger(JSONObject collecte) {

		
		return null;
	}

}
