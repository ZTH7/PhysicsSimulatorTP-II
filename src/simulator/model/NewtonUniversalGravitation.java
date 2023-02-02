package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	private double G;
	
	public NewtonUniversalGravitation(double G) {
		if(G <= 0) throw new IllegalArgumentException();
		this.G = G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		double dist, f;
		Vector2D distance;
		for(Body body1 : bs) {
			for(Body body2 : bs) {
				if(body1 != body2) {
					if ((dist = (distance = body2.getPosition().minus(body1.getPosition())).magnitude()) > 0) {
						f = G * body1.getMass() * body2.getMass() / (dist * dist);
						body1.addForce(distance.scale(1.0 / dist).scale(f));
					}
				}
			}
		}
	}

}
