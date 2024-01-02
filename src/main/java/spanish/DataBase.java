package spanish;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBase {

	public static DataBase common = new DataBase();
	
	private ArrayList<DataBaseEntry> entries = new ArrayList<>();
	
	private DataBase() {

	}

	public void add(DataBaseEntry dataBaseEntry) {
		entries.add(dataBaseEntry);
	}

	public ArrayList<DataBaseEntry> query(HashMap<String, String> filter) {

		ArrayList<DataBaseEntry> result = new ArrayList<>();
		for (DataBaseEntry dbe: entries) {
			if (dbe.matches(filter)) {
				result.add(dbe);
			}
		}

		return result;
	}

}
