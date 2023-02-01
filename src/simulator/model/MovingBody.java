package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {

	public MovingBody() {}
	
	public MovingBody(String id, String gid, Vector2D v, Vector2D p, double m) {
		super(id, gid, v, p, m);
	}
	
	@Override
	void advance(double dt) {
		Vector2D a = m == 0 ? new Vector2D() : f.scale(1/m);
		
		p.plus(v.scale(dt));
		p.plus(a.scale(dt * dt / 2));
		v.plus(a.scale(dt));
	}
	
	

}
