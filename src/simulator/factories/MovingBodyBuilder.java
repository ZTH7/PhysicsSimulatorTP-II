package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {

	public MovingBodyBuilder(String typeTag, String desc) {
		super("mv_body", "MovingBody");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		String id = data.getString("id"), gid = data.getString("gid");
		JSONArray _v = data.getJSONArray("v"), _p = data.getJSONArray("p");
		
		Vector2D v = new Vector2D(_v.getDouble(0), _v.getDouble(1));
		Vector2D p = new Vector2D(_p.getDouble(0), _p.getDouble(1));
		
		double m = data.getDouble("m");
		
		return new MovingBody(id, gid, v, p, m);
	}
	
}
