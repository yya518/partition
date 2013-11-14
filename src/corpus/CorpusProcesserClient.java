package corpus;

public class CorpusProcesserClient {
	
	public static void main(String[] args) throws Exception{
		String file = "/Users/yiyang/Documents/workspace/PartitionCode/dataset/brown/brown.corpus";
		
		CorpusProcesser processer = new CorpusProcesser(file);
		processer.createVocab();
		processer.indexCorpus();
	}
}
