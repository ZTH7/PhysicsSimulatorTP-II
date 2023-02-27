package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected MovingTowardsFixedPoint createInstance(JSONObject data) {
		Vector2D c = new Vector2D();
		
		if (data.has("c")) {
			JSONArray _c = data.getJSONArray("c");
			c = new Vector2D(_c.getDouble(0), _c.getDouble(1));
		}

		return new MovingTowardsFixedPoint(c, data.has("g") ? data.getDouble("g") : 9.81);
	}

}
