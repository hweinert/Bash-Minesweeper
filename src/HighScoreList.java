import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreList {
	private String name;
	private int maxEntries;
	private String measurementUnit;
	private boolean lowestIsBest;
	private List<Entry> entries;
	
	public HighScoreList(String name, int maxEntries, String measurementUnit, boolean lowestIsBest) {
		this.name = name;
		this.maxEntries =  maxEntries;
		this.measurementUnit = measurementUnit;
		this.lowestIsBest = lowestIsBest;
		entries = new ArrayList<>();
	}
	
	public boolean add(String name, double value) {
		if (entries.size() < maxEntries) {
			entries.add(new Entry(name, value));
			if (!lowestIsBest) {
				entries.sort((e1, e2) -> Double.compare(e2.getScore(), e1.getScore()));
			} else {
				entries.sort((e1, e2) -> Double.compare(e1.getScore(), e2.getScore()));	
			}
			return true;
		}
		
		if (!lowestIsBest) {
			if (value > entries.get(entries.size() - 1).getScore()) {
				entries.add(new Entry(name, value));
				entries.sort((e1, e2) -> Double.compare(e2.getScore(), e1.getScore()));
				entries.remove(entries.size() - 1);
				return true;
			}
		}  else {
			if (value < entries.get(entries.size() - 1).getScore()) {
				entries.add(new Entry(name, value));
				entries.sort((e1, e2) -> Double.compare(e1.getScore(), e2.getScore()));
				entries.remove(entries.size() - 1);
				return true;
			}
		}
		
		return false;
	}
	
	public Entry[] getEntries() {
		return (Entry[])entries.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name + "(" + measurementUnit + ")\n");
		
		int i = 1;
		for (Entry e : entries) {
			sb.append(i + ": " + e.getName() + " " + e.getScore() + " (" + e.getCreationDate() + ")\n");
			i++;
		}
		
		return sb.toString();
	}
}