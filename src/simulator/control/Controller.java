package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	private PhysicsSimulator ps;
	private Factory<ForceLaws> ff;
	private Factory<Body> fb;

	public Controller(PhysicsSimulator ps, Factory<ForceLaws> ff, Factory<Body> fb) {
		this.ps = ps;
		this.ff = ff;
		this.fb = fb;
	}

	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		p.println(ps.toString());
		for (int i = 0; i < n; i++) {
			ps.advance();
			p.println(',' + ps.toString());
		}
		p.println("]");
		p.println("}");
	}

	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));

		if (!jsonInput.has("groups"))
			throw new IllegalArgumentException("Es obligatorio indicar los grupos.");

		if (!jsonInput.has("bodies"))
			throw new IllegalArgumentException("Es obligatorio indicar los cuerpos.");

		JSONArray groups = jsonInput.getJSONArray("groups");
		groups.forEach(group -> ps.addGroup(group.toString()));

		JSONArray bodies = jsonInput.getJSONArray("bodies");
		bodies.forEach(body -> ps.addBody(fb.createInstance((JSONObject) body)));

		if (jsonInput.has("laws")) {
			JSONArray laws = jsonInput.getJSONArray("laws");
			laws.forEach(law -> {
				ForceLaws fl = ff.createInstance(((JSONObject) law).getJSONObject("laws"));
				ps.setForceLaws(((JSONObject) law).getString("id"), fl);
			});
		}

	}

}
