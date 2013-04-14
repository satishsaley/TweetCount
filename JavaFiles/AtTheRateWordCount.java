package com.satish.hadoop;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class AtTheRateWordCount {

	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

			String line = value.toString();
			
   
			HashMap< Integer,String> stopWords=new HashMap< Integer,String>();
			
		
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
			while (tokenizer.hasMoreTokens())
			{
				currentWord=tokenizer.nextToken().toLowerCase();
				if(currentWord.contains("@"))
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
		JobConf conf = new JobConf(AtTheRateWordCount.class);
		conf.setJobName("AtTheRateWordCount");

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