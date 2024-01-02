package spanish;

import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JsonUtils {

	public static String tiempo[] = { "presente", "preterito", "imperfecto", "futuro immediato", "futuro" }; 
	public static String use[] = { "present", "single past event with start and end", "multiple past event", "be going to", "future" }; 
	public static String persona[] = { "yo" , "tú", "él" , "nosotros", "vosotros" , "ellas" };

	private static HashMap<String,String> meanings = null;
	
	public JsonUtils() {

	}

	public static void main(String[] args) {

		try {
			JSONArray jarr = new JSONArray();
			
			jarr.put(forVerb("pedir", "to ask"));
			jarr.put(forVerb("perder", "to miss or lose"));
			jarr.put(forVerb("contestar", "to answer"));
			System.out.println(jarr.toString(4));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private static JSONObject forVerb(String verb, String meaning) throws JSONException {
		
		
		JSONObject cla = new JSONObject();
		cla.put("verb", verb);
		cla.put("meaning", meaning);
		
		JSONObject tiempos = new JSONObject();
		cla.put("tiempos", tiempos);
		for (String t: tiempo) {
			JSONObject jobj = new JSONObject();
			tiempos.put(t, jobj);
			
			for (String p: persona) {
				jobj.put(p, verb);
			}
			
		}
		
		return cla;
	
	}

	public static String meaning(String tense) {
		if (meanings == null) {
			meanings = new HashMap<>();
			for (int index = 0; index < tiempo.length; index++) {
				meanings.put(tiempo[index], use[index]);
			}
		}
		return meanings.get(tense);
	}
}	
