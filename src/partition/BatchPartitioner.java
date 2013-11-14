package partition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BatchPartitioner extends Partitioner {

	//public ArrayList<Integer> sentenceRank; //sentence's type rank
	public HashMap<Integer, Integer> typeRank;//<sentence id, sentence type>
	public ArrayList<ArrayList<Integer>> invertedIndex;
	
	class RankValueComparator implements Comparator<Integer> {

	    HashMap<Integer, Integer> base;
	    public RankValueComparator(HashMap<Integer, Integer> base) {
	        this.base = base;
	    }
	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(Integer a, Integer b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
	
	public BatchPartitioner(Corpus corpus, int splits) {
		super(corpus, splits);
		typeRank = new HashMap<Integer, Integer>();
		
		invertedIndex = new ArrayList<ArrayList<Integer>>();
		createInvertedIndex();
	}

	@Override
	public void partition() {
        RankValueComparator rvc =  new RankValueComparator(typeRank);
        TreeMap<Integer,Integer> sorted_rankType = new TreeMap<Integer,Integer>(rvc);
		for(int i = 0 ; i < corpus.sentences.size(); i++){
			int type = corpus.sentences.get(i).type;
			typeRank.put(i, type);
		}
		sorted_rankType.putAll(typeRank);
		Set<Integer> keys = sorted_rankType.keySet();
		Iterator<Integer> iter = keys.iterator();
		for(int i = 0 ; i < splits; i++){
			Integer sId = (Integer) iter.next();
			int type = typeRank.get(sId);
			Sentence s = corpus.sentences.get(sId);
			clusters.get(i).insert(s);
			sorted_rankType.remove(sId);
		}
		System.out.println(sorted_rankType);
		//System.out.println(invertedIndex.size());
	}
	
	/**
	 * create inverted index for every word type
	 */
	public void createInvertedIndex(){
		for(int i = 0 ; i < corpus.typeCount; i++){ //for every word type
			invertedIndex.add(new ArrayList<Integer>());
		}
		for(int i = 0 ; i < corpus.sentences.size(); i++){ //for every sentence
			String[] types = corpus.sentences.get(i).content.split(" ");
			for(int j = 0; j < types.length; j++){//for every word type
				invertedIndex.get(Integer.parseInt(types[j])).add(i);//add sentence id to word type types[j]
			}
		}
	}

}
