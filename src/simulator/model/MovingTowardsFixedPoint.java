package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	double g;
	Vector2D c;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		if(g < 0 || c == null) throw new IllegalArgumentException();
		this.g = g;
		this.c = c;
	}
	
	@Override
	public void apply(List<Body> bs) {
		bs.forEach(body -> body.addForce((c.minus(body.getPosition()).scale(g).scale(body.getMass()))));
	}

}
