package partition;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Cluster {

	public int Id;
	public int token;
	public int type;
	public ArrayList<Sentence> sentenceSet;
	public Set<String> wordSet;
	
	public Cluster(int id){
		Id = id;
		token = 0;
		type = 0;
		sentenceSet = new ArrayList<Sentence>();
		wordSet = new TreeSet<String>();
	}
	
	public void insert(Sentence sentence){
		token += sentence.token;
		String[] words = sentence.content.split(" ");
		for(int i = 0 ; i < words.length; i++){
			wordSet.add(words[i]);
		}
		sentenceSet.add(sentence);
	}
}
