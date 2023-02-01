package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super("mtfp", "MovingTowardsFixedPoint");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		JSONArray _c = data.getJSONArray("c");
		Vector2D c = new Vector2D(_c.getDouble(0), _c.getDouble(1));
		
		return new MovingTowardsFixedPoint(c, data.getDouble("g"));
	}
	
}
