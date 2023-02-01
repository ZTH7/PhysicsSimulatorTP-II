package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	private double G;
	
	public NewtonUniversalGravitation(double G) {
		if(G < 0) throw new IllegalArgumentException();
		this.G = G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		double distance, ftmp;
		Body body1, body2;
		Vector2D dist, f;
		for(int i = 0; i < bs.size(); i++) {
			body1 = bs.get(i);
			
			if(body1.getMass() == 0) continue;
			
			for(int j = 0; j < bs.size(); j++) {
				body2 = bs.get(j);
				
				if(body1 != body2) {
					if((distance = (dist = body1.getPosition().minus(body2.getPosition())).magnitude()) > 0) {
						ftmp = G * body1.getMass() * body2.getMass() / (distance * distance);
						f = dist.scale(ftmp);
					} else f = new Vector2D();
					
					body1.addForce(f);
				}
			}
		}
	}

}
