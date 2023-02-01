package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String,Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	
	public BuilderBasedFactory() {
		_builders = new HashMap<>();
		_buildersInfo = new LinkedList<>();
	}
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		builders.forEach(builder -> addBuilder(builder));
	}
	
	public void addBuilder(Builder<T> b) {
		_builders.put(b.getTypeTag(), b);
		_buildersInfo.add(b.getInfo());
	}
	
	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		
		Builder<T> builder= _builders.get(info.getString ("type"));
		if(builder != null) return builder.createInstance((info.has("data") ? info.getJSONObject("data") : new JSONObject()));

		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
	}
		
	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}
}
