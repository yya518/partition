package corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class CorpusProcesser {

	protected String filebase;
	protected String corpusFile;
	
	public CorpusProcesser(String file){
		corpusFile = file;
		filebase = file.substring(0, file.lastIndexOf("."));
	}
	
	public void createVocab() throws Exception{
		String vocabFile = filebase + ".vocab";
		BufferedWriter writer = new BufferedWriter(new FileWriter(vocabFile));
		BufferedReader reader = new BufferedReader(new FileReader(corpusFile));
		HashMap<String, Integer> wordmap = new HashMap<String, Integer>();
		String line;
		int typeCount = 0;
		int tokenCount = 0;
		while((line = reader.readLine()) !=null ){
			String[] tokens = line.split(" ");
			tokenCount += tokens.length;
			for(int i = 0 ; i < tokens.length; i++){
				if(!wordmap.containsKey(tokens[i]))
					wordmap.put(tokens[i], wordmap.size());
			}
		}
		writer.write(wordmap.size() + " " + tokenCount + "\n");
		for(String word : wordmap.keySet())
			writer.write(word + " " + wordmap.get(word) + "\n");
		reader.close();
		writer.close();
	}
	
	public HashMap<String, Integer> readVocabFile() throws Exception{
		String vocabFile = filebase + ".vocab";
		BufferedReader reader = new BufferedReader(new FileReader(vocabFile));
		String line;
		HashMap<String, Integer> wordmap = new HashMap<String, Integer>();
		while((line = reader.readLine()) !=null ){
			String[] key_value = line.split(" ");
			wordmap.put(key_value[0], Integer.parseInt(key_value[1]));
		}
		reader.close();
		return wordmap;
	}
	
	public void indexCorpus() throws Exception{
		String indexFile = filebase + ".index";
		String vocabFile = filebase + ".vocab";
		BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile));
		BufferedReader reader = new BufferedReader(new FileReader(corpusFile));
		HashMap<String, Integer> wordmap = readVocabFile();
		
		BufferedReader vocabReader = new BufferedReader(new FileReader(vocabFile));
		String line = vocabReader.readLine(); //read in tokenCount and typeCount
		writer.write(line + "\n");
		while((line = reader.readLine()) !=null ){
			String[] tokens = line.split(" ");
			Set<String> types = new TreeSet<String>();
			for(int i = 0 ; i < tokens.length; i++){
				types.add(tokens[i]);
			}
			writer.write(types.size() + " " + tokens.length + ":");
			for(String type : types){
				int index = wordmap.get(type);
				writer.write(index + " ");
			}
			writer.write("\n");
		}
		reader.close();
		vocabReader.close();
		writer.close();
	}
}
