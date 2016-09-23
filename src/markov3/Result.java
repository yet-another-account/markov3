package markov3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class Result {
	private HashMap<String, MutableInteger> results = new HashMap<>();
	private int totaloccur = 0;
	
	public Result(String res) {
		results.put(res, new MutableInteger(1));
	}
	
	public int getOccur() {
		return totaloccur;
	}

	public Result addResult(String res) {

		MutableInteger i = results.get(res);
		if(i == null)
			results.put(res, new MutableInteger(1));
		else
			i.increment();
		totaloccur++;
		return this;
	}

	@Override
	public String toString() {
		return results.toString();
	}

	public String getRandom(Random rnd) {
		ArrayList<Entry<String, MutableInteger>> entries = new ArrayList<>(results.entrySet());

		ArrayList<Long> ints = new ArrayList<>();
		ArrayList<String> vals = new ArrayList<>();

		long cumulative = -1;

		for(Entry<String, MutableInteger> e : entries) {
			ints.add(cumulative += e.getValue().get());
			vals.add(e.getKey());
		}

		long l = Math.abs(rnd.nextLong()) % (cumulative == 0 ? 1 : cumulative);
		int index = Collections.binarySearch(ints, l);
		index = (index >= 0) ? index : -index-1;

		return vals.get(index);
	}
}
