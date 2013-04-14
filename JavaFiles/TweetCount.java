package com.satish.hadoop;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class TweetCount {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public final static String[] listOfWords = new String[]{ "can't","u","i","&","just","rt","$$","-","ya","x","s","i'd","ur","said","did","hi","te","n","e","yo","having","1","2","3","4","5","6","7","8","9","0","la","em","o","es","en","lo","w","t","h", "fucking","bitch","a", "about", "above", "above",
			"across", "after", "afterwards", "again", "against", "all",
			"almost", "alone", "along", "already", "also", "although",
			"always", "am", "among", "amongst", "amoungst", "amount", "an",
			"and", "another", "any", "anyhow", "anyone", "anything", "anyway",
			"anywhere", "are", "around", "as", "at", "back", "be", "became",
			"because", "become", "becomes", "becoming", "been", "before",
			"beforehand", "behind", "being", "below", "beside", "besides",
			"between", "beyond", "both", "bottom", "but", "by", "call",
			"can", "cannot", "cant", "co", "com", "con", "could", "couldnt", "cry",
			"de", "describe", "detail", "do", "done", "down", "due", "during",
			"each", "eg", "eight", "either", "eleven", "else", "elsewhere",
			"empty", "enough", "etc", "even", "ever", "every", "everyone",
			"everything", "everywhere", "except", "few", "fifteen", "fify",
			"fill", "find", "fire", "first", "five", "for", "former",
			"formerly", "forty", "found", "four", "free", "from", "front", "full",
			"further", "get", "give", "go", "had", "has", "hasnt", "have",
			"he", "hence", "her", "here", "hereafter", "hereby", "herein",
			"hereupon", "hers", "herself", "him", "himself", "his", "how",
			"however", "hundred", "ie", "if", "in", "inc", "indeed",
			"interest", "into", "is", "it", "its", "itself", "keep", "last",
			"latter", "latterly", "least", "less", "ltd", "made", "many",
			"may", "me", "meanwhile", "might", "mill", "mine", "more",
			"moreover", "most", "mostly", "move", "much", "must", "my",
			"myself", "name", "namely", "neither", "net", "never", "nevertheless",
			"next", "nine", "no", "nobody", "none", "noone", "nor", "not",
			"nothing", "now", "nowhere", "of", "off", "often", "on", "once",
			"one", "only", "onto", "or", "org", "other", "others", "otherwise", "our",
			"ours", "ourselves", "out", "over", "own", "part", "per",
			"perhaps", "please", "put", "rather", "re", "same", "see", "seem",
			"seemed", "seeming", "seems", "serious", "several", "she",
			"should", "show", "side", "since", "sincere", "six", "sixty", "so",
			"some", "somehow", "someone", "something", "sometime", "sometimes",
			"somewhere", "still", "such", "system", "take", "ten", "than",
			"that", "the", "their", "them", "themselves", "then", "thence",
			"there", "thereafter", "thereby", "therefore", "therein",
			"thereupon", "these", "they", "thickv", "thin", "third", "this",
			"those", "though", "three", "through", "throughout", "thru",
			"thus", "to", "together", "too", "top", "toward", "towards",
			"twelve", "twenty", "two", "un", "under", "until", "up", "upon",
			"us", "very", "via", "was", "we", "well", "were", "what",
			"whatever", "when", "whence", "whenever", "where", "whereafter",
			"whereas", "whereby", "wherein", "whereupon", "wherever",
			"whether", "which", "while", "whither", "who", "whoever", "whole",
			"whom", "whose", "why", "will", "wikipedia", "with", "within", "without",
			"would", "yet", "you", "your", "yours", "yourself", "yourselves",
		"the","i", };
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

			String line = value.toString();
			
   
			HashMap< Integer,String> stopWords=new HashMap< Integer,String>();
			int lengthOfList=listOfWords.length;
			
			for(int i=1;i<lengthOfList;i++)
			{
				stopWords.put(new Integer(i),listOfWords[i]);
			}

			StringTokenizer tokenizer = new StringTokenizer(line);
			
			for(int i=0;i<8;i++)
			{
				if(tokenizer.hasMoreTokens())
				{
				
					String s0 = tokenizer.nextToken().trim();
					
				}
				if(tokenizer.hasMoreElements()==false)break;
			}
			
			
			String currentWord;
			while (tokenizer.hasMoreTokens()) {
				currentWord=tokenizer.nextToken().toLowerCase();
				
				//currentWord=currentWord.replaceAll("^\"","");
				
				
				String currentOut=currentWord,previousOut=currentWord;
				
				do
				{
					previousOut=currentOut;
					currentOut=(currentOut.replaceAll("((\\?*)|(\\.*)|(!*)|(,*)|(\"*)|(\\|*)|(\\'*))$", ""));
					currentOut=currentOut.replaceAll("(^( (\")|(\\|)|(\\!)|(\\')|(\\.)|(,) ))", "");
					System.out.println("current="+currentOut);
				}while(previousOut.equals(currentOut)==false);
				
				currentWord=currentOut;
				
				//currentWord=currentWord.replaceAll("((\\?)|(\\.)|(!)|(,)|(\"*)|(\\|*))$", "");
			    
				if(stopWords.containsValue(new String(currentWord.trim()))==false)
				{
					word.set(currentWord);
					output.collect(word, one);
				}

			}
		}
	}

	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			output.collect(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {
		JobConf conf = new JobConf(TweetCount.class);
		conf.setJobName("tweetcount");

		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);

		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
	}
}
