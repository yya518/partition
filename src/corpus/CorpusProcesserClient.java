package corpus;

public class CorpusProcesserClient {
	
	public static void main(String[] args) throws Exception{
		String file = "/home/yyyn/partition/dataset/brown/brown.corpus";
		
		CorpusProcesser processer = new CorpusProcesser(file);
		processer.createVocab();
		processer.indexCorpus();
	}
}
