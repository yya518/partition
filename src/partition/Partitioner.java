package partition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Partitioner {
	
	Corpus corpus;
	int splits;
	ArrayList<Cluster> clusters;

	int tokenPerCluster;
	
	public Partitioner(Corpus corpus, int splits){
		this.corpus = corpus;
		this.splits = splits;
		clusters = new ArrayList<Cluster>();
		for(int i = 0; i < splits; i++)
			clusters.add(new Cluster(i));
		this.tokenPerCluster = corpus.tokenCount / splits + 1; //get the ceiling
	}
	
	public abstract void partition();
	
	public void writeToDisk() throws Exception{
		String directory = corpus.filebase.substring(0, corpus.filebase.lastIndexOf("/")) + "/splits/";
		File dir = new File(directory);
		dir.mkdir(); //make directory for output files
		
		String corpusFile = corpus.filebase + ".corpus";
		BufferedReader reader = new BufferedReader(new FileReader(corpusFile));
		String line;
		ArrayList<String> origin_sentences = new ArrayList<String>();
		while((line=reader.readLine()) != null){
			origin_sentences.add(line);
		}
		for(int i = 0 ; i < clusters.size(); i++){
			BufferedWriter writer = new BufferedWriter(new FileWriter(directory+Integer.toString(i)));
			ArrayList<Sentence> sentences = clusters.get(i).sentenceSet;
			for(int j = 0 ; j < sentences.size(); j++){
				int sId = sentences.get(j).sId;
				writer.write(origin_sentences.get(sId) + "\n");
			}
			writer.close();
		}
		reader.close();
	}
	
	public void countType(){
		ArrayList<Integer> typeCounts = new ArrayList<Integer>();
		int countsSum = 0;
		for(int i = 0 ; i < clusters.size(); i++){
			typeCounts.add(clusters.get(i).wordSet.size());
			countsSum += clusters.get(i).wordSet.size();
		}
		System.out.println("maximum type counts is: " + Collections.max(typeCounts));
		System.out.println("average type counts is: " + countsSum/splits);
	}
}
