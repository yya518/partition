package partition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomPartitioner extends Partitioner {

	public RandomPartitioner(Corpus corpus, int splits){
		super(corpus, splits);
	}
	
	@Override
	public void partition() {
		Collections.shuffle(corpus.sentences, new Random(0));
		int lineId = 0;
		int sentence_left = corpus.sentences.size();
		for(int i = 0; i < splits; i++){
			while(clusters.get(i).token < tokenPerCluster && sentence_left != 0){
				Sentence sentence = corpus.sentences.get(lineId);
				clusters.get(i).insert(sentence);
				sentence_left--;
				lineId++;
			}
		}
	}
}
