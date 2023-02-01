package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup {
	private String id;
	private ForceLaws laws;
	private List<Body> bodies;
	
	public BodiesGroup(String id, ForceLaws laws) {
		if(id == null || laws == null || id.trim().length() == 0) throw new IllegalArgumentException();
		this.id = id;
		this.laws = laws;
	}
	
	public String getId() { return id; }
	
	void setForceLaws(ForceLaws fl) {
		if(fl == null) throw new IllegalArgumentException();
		this.laws = fl;
	}
	
	void addBody(Body b) {
		if(b == null) throw new IllegalArgumentException();
		for(Body tmp : bodies) if(tmp.getId().equals(b.getId())) throw new IllegalArgumentException();
		
		bodies.add(b);
	}
	
	void advance(double dt) {
		bodies.forEach(body -> body.resetForce());
		laws.apply(bodies);
		bodies.forEach(body -> body.advance(dt));
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		bodies.forEach(body -> ja.put(body.getState()));
		jo.put("id", id);
		jo.put("bodies", ja);
		return jo;
	}
	
	@Override
	public String toString() {
		return getState().toString();
	}

}
