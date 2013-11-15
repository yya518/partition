**First step: Pre-processing corpus**

we use the following file name conventions:

- corpus file: 		*.corpus
- vocabulary file:	*.vocab
- indexed file:		*.index (this is the file we use in partitioning)

File formats:

- *.corpus:	the raw documents collection, each line is a document.
- *.vocab:	the first line is consisted of total word types and total word tokens, and the rest of lines is a word type and its index mapping.
- *.index: 	the first line is consisted of total word types and total word tokens, and the rest of lines are indexed documents. For each line, two numbers before colon : is the word type size and word token size for that document, and the	rest part is the bag of words for that sentence. Also, each raw word is translated to its index.
		For example, the following line represents a document with 16 word types and 17 word tokens. The indexed bag of words are shown after colon : 

		16 17:18 24 226 1 15 69 231 229 230 227 109 23 232 80 58 228
		
You can create your own index file, or you can use CorpusProcesserClient to process it automatically for you.

command line sample:

	CorpusProcesserClient /absolute_path/dataset/brown/brown.corpus


**Second step: Partition corpus, i.e., *.index file**
command line arguments:

- -h:					print help information
- -n:					partition size
- -t:					partition algorithm type, currently support **random** and **batch**
- -f:					target corpus file absolute path, use the *.index file generated in the first step

command sample:

	PartitionClient -n 50 -t batch -f /absolute_path/dataset/brown/brown.index
	
The above command partition brown corpus in 50 splits using batch algorithm.
