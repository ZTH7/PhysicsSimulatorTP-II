package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	private double G;

	public NewtonUniversalGravitation(double G) {
		if (G <= 0)
			throw new IllegalArgumentException("El valor de G no es positivo");
		this.G = G;
	}

	@Override
	public void apply(List<Body> bs) {
		for (Body body1 : bs) {
			for (Body body2 : bs) {
				if (body1 != body2) {
					double dis = body1.getPosition().distanceTo(body2.getPosition());

					if (dis > 0) {
						Vector2D d = body2.getPosition().minus(body1.getPosition()).direction();
						double f = G * body1.getMass() * body2.getMass() / (dis * dis);
						body1.addForce(d.scale(f));
					}
				}
			}
		}
	}
}
