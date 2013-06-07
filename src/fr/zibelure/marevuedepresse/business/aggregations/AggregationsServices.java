package fr.zibelure.marevuedepresse.business.aggregations;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import fr.zibelure.marevuedepresse.model.Reponse;

public interface AggregationsServices {
	
	public Map<UUID, Reponse> aggreger(List<JSONObject> collectes);
	
	public Map<UUID, Reponse> aggreger(JSONObject collecte);

}
