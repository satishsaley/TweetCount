package com.satish.hadoop;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.IdentityReducer;
import org.apache.hadoop.util.*;

import com.satish.hadoop.SortingResults.Map;
import com.satish.hadoop.SortingResults.Reduce;

public class SortingResults {

	public static class Map extends MapReduceBase implements Mapper<LongWritable,Text,IntWritable,Text> {
		private  static IntWritable one=new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key,Text value, OutputCollector<IntWritable,Text> output, Reporter reporter) throws IOException {


			String line = value.toString();

			StringTokenizer tokenizer = new StringTokenizer(line);

			int number = 999999; 
			String word = "null";

			if(tokenizer.hasMoreTokens())
			{
				word = tokenizer.nextToken().trim();
			}

			if(tokenizer.hasMoreElements())
			{
				number = Integer.parseInt(tokenizer.nextToken().trim());
			}
			output.collect(new IntWritable(number), new Text(word));

		}
	}



	public static class Reduce extends MapReduceBase implements Reducer<IntWritable ,Text, IntWritable,Text> {

		@Override
		public void reduce(IntWritable key, Iterator<Text> values,
				OutputCollector<IntWritable,Text> output, Reporter arg3)
		throws IOException {

			while((values.hasNext()))
			{

				output.collect(key, values.next());
			}




		}
	}


	public static void main(String[] args) throws Exception {


		JobConf conf = new JobConf(SortingResults.class);
		conf.setJobName("SortingResults");

		conf.setOutputKeyClass(IntWritable.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(IdentityReducer.class);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);

	}
}


