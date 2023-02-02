package simulator.model;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	double realtime, actualtime = 0;
	ForceLaws laws;
	Map<String,BodiesGroup> groups;
	
	public PhysicsSimulator(ForceLaws laws, double time) {
		if(time < 0 || laws == null) throw new IllegalArgumentException();
		this.realtime = time;
		this.laws = laws;
		this.groups = new HashMap<>();
	}
	
	public void advance() {
		groups.values().forEach(group -> group.advance(realtime));
		actualtime += realtime;
	}
	
	public void addGroup(String id) {
		if (groups.get(id) != null) throw new IllegalArgumentException();
		groups.put(id, new BodiesGroup(id, this.laws));
	}
	
	public void addBody(Body b) {
		BodiesGroup bg = groups.get(b.getgId());
		if(bg == null) throw new IllegalArgumentException();
		bg.addBody(b);
	}
	
	public void setForceLaws(String id, ForceLaws fl) {
		BodiesGroup bg = groups.get(id);
		if(bg == null) throw new IllegalArgumentException();
		bg.setForceLaws(fl);
	}
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		groups.values().forEach(group -> ja.put(group.getState()));
		jo.put("time", actualtime);
		jo.put("groups", ja);
		return jo;
	}
	
	@Override
	public String toString() {
		return getState().toString();
	}
	
}
