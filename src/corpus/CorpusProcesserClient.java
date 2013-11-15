package corpus;

public class CorpusProcesserClient {
	
	public static void main(String[] args) throws Exception{
		//String file = "/Users/yiyang/Documents/workspace/PartitionCode/dataset/brown/brown.corpus";
		String corpus_file = args[1];
		CorpusProcesser processer = new CorpusProcesser(corpus_file);
		processer.createVocab();
		processer.indexCorpus();
	}
}
