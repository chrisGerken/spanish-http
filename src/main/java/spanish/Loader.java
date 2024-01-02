package spanish;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Loader {

	private ArrayList<Verb> verbs = new ArrayList<>();
	
	public Loader() {
	
		
//		Question q = verbs.get(0).getQuestion("imperfecto", "1p");
// 		System.out.println(q);
//		System.out.println(q.getPrompt());
//		System.out.println("answer: "+q.getAnswerCode());

	}

	public void load(String resource) {
		JSONArray jarr = readFrom(resource);
		store(jarr);		
	}
	
	private void store(JSONArray jarr) {

		for (int index = 0; index < jarr.length(); index++) {
			try {
				new Verb(jarr.getJSONObject(index));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}

	private JSONArray readFrom(String resource) {
		try {
			InputStream is = Loader.class.getResourceAsStream(resource);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[5000];
			int len = is.read(b);
			while (len > -1) {
				baos.write(b, 0, len);
				len = is.read(b);
			}
			is.close();
			String buf = baos.toString();
			return new JSONArray(buf);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
