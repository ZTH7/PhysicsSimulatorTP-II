package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {

	public StationaryBodyBuilder() {
		super("st_body", "StationaryBody");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m"))
			throw new IllegalArgumentException();
		
		String id = data.getString("id"), gid = data.getString("gid");
		JSONArray p = data.getJSONArray("p");
		double m = data.getDouble("m");
		
		if(p.length() != 2 ) throw new IllegalArgumentException();
		
		return new StationaryBody(id, gid, new Vector2D(p.getDouble(0), p.getDouble(1)), m);
	}

}
