package partition;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Corpus {

	ArrayList<Sentence> sentences;
	int tokenCount;
	int typeCount;
	String filebase;
	
	public Corpus(String indexFile) throws Exception{
		filebase = indexFile.substring(0, indexFile.lastIndexOf("."));
		
		sentences = new ArrayList<Sentence>();
		
		BufferedReader reader = new BufferedReader(new FileReader(indexFile));
		String line = reader.readLine();//indexFile first line records tokenCount and typeCount
		typeCount = Integer.parseInt(line.split(" ")[0]);
		tokenCount = Integer.parseInt(line.split(" ")[1]);
		
		int sId = 0;
		while( (line = reader.readLine()) != null){
			String[] sentence = line.split(":");
			int typecount = Integer.parseInt(sentence[0].split(" ")[0]);
			int tokencount = Integer.parseInt(sentence[0].split(" ")[1]);
			String content = sentence[1];
			sentences.add(new Sentence(content, sId, tokencount, typecount));
			sId++;
		}
		reader.close();
	}
}
