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

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;

public class BatchPartitioner extends Partitioner {

	private final double MaxTokenThreshold = 0.03;
	private int BatchSize = 1;

	//public ArrayList<Integer> sentenceRank; //sentence's type rank
	public TreeMap<Integer, Integer> typeRankMap;//<sentence id, sentence type>
	public ArrayList<ArrayList<Integer>> invertedIndex;

	public BatchPartitioner(Corpus corpus, int splits) {
		super(corpus, splits);
		typeRankMap = new TreeMap<Integer, Integer>();
		invertedIndex = new ArrayList<ArrayList<Integer>>();
		createInvertedIndex();
	}

	/**
	 * in initialization step, we assign sentences with the maximum type
	 * to clusters. This heuristic method allows to have more options in 
	 * the future sentence selections
	 */
	public void initialSentenceAssignment(){
		//build type rank map
		typeRankMap = new ValueComparableMap<Integer, Integer>(Ordering.natural());
		for(int i = 0 ; i < corpus.sentences.size(); i++){
			int type = corpus.sentences.get(i).type;
			typeRankMap.put(i, type);
		}
		Set<Integer> keys = typeRankMap.descendingKeySet();
		Iterator<Integer> iter = keys.iterator();
		for(int i = 0 ; i < splits; i++){
			int sId = (Integer) iter.next();
			Sentence s = corpus.sentences.get(sId);
			clusters.get(i).insert(s);
			iter.remove();
		}

	}

	public void updateTypeRank(Sentence sentence){
		String[] types = sentence.content.split(" ");
		for(String type : types){ //for every word type in sentence
			ArrayList<Integer> sentenceIds = invertedIndex.get(Integer.parseInt(type));
			for(int i = 0 ; i < sentenceIds.size(); i++){
				int sId = sentenceIds.get(i);
				int sType = corpus.sentences.get(sId).type;
				int newValue = typeRankMap.get(sId) - 1;
				typeRankMap.put(sId, newValue);
			}
		}
	}

	@Override
	public void partition() {

		initialSentenceAssignment();

		Set<Integer> keys = typeRankMap.descendingKeySet();
		Iterator<Integer> iter = keys.iterator();
		while( !typeRankMap.isEmpty() ){
			iter = keys.iterator();
			System.out.println(typeRankMap.size());

			//find sentence id after batch_size
			Iterator<Integer> next_iter = keys.iterator(); //pointer to move batch size to get sentence id
			int next_sId = 0;
			if(BatchSize < typeRankMap.size()){
				for(int idx = 0 ; idx < BatchSize; idx++)
					next_iter.next();		
				next_sId = next_iter.next();
			} 

			for(int idx = 0; idx < BatchSize; idx++){
				if( !typeRankMap.isEmpty() ){
					int sId = iter.next();
					Sentence s = corpus.sentences.get(sId);
					int clusterId = clusterSelectionJaccard(s); 
					clusters.get(clusterId).insert(s);
					iter.remove();
				} else 
					break;
			}

			//update batch size
			if( !typeRankMap.isEmpty())
				if( iter.next() == next_sId )
					BatchSize++;
				else
					break; 
		}
	}

	public int clusterSelectionJaccard(Sentence sentence){
		double jaccardIndex = 0.0;
		double tempJaccard = 0.0;
		int clusterId = 0;

		int MaxTokenLimit = (int) (tokenPerCluster * (1.0 + MaxTokenThreshold));

		String[] types = sentence.content.split(" ");
		for(int i = 0 ; i < splits; i++){ //compare with every cluster
			if( clusters.get(i).token <= MaxTokenLimit ){
				Set<String> clusterWordSet = clusters.get(i).wordSet;
				int union = clusters.get(i).type;
				for(int j = 0 ; j < types.length; j++){
					if ( !clusterWordSet.contains(types[j]) )
						union++;
				}
				int intersection = clusters.get(i).type + types.length - union;
				tempJaccard = (double)intersection / union;
				if( tempJaccard > jaccardIndex ){
					jaccardIndex = tempJaccard;
					clusterId = i;
				}
			}
		}
		return clusterId;
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
