package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {

	public StationaryBodyBuilder(String typeTag, String desc) {
		super("st_body", "StationaryBody");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		String id = data.getString("id"), gid = data.getString("gid");
		JSONArray _p = data.getJSONArray("p");
		
		Vector2D p = new Vector2D(_p.getDouble(0), _p.getDouble(1));
		
		double m = data.getDouble("m");
		
		return new StationaryBody(id, gid, p, m);
	}

}
