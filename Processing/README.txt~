***********************************
README
***********************************
Prepared by-Satish Subhashrao Saley
***********************************

****Information****
***************************************************
1. I have implemented Simple MapReduce program to find simple word count, #word count and @word counts.
2. To sort the results by value, I have fed the results of first MapReduce program to another MapReduce program, and by swapping the key-value, I sorted the results according to the value.
3. Then using, CircularFIFO buffer in Java, I found the high frequency words in a file.
4. I created a HTML form using those words and published it automatically on wordle.net to view the word cloud.


****Contents****
***************************************************
1.AtTheRateWordCount.jar : Jar file containing MapReduce program to calculate @word counts

2.SimpleWordCount.jar : Jar file containing MapReduce program to calculate word counts

3.HashWordCount.jar : Jar file containing MapReduce program to calculate #word counts

4.SortingResults.jar : Jar file containing MapReduce program to sort input by value.

5.commons-collections-3.2.jar : Jar file where CircularFifoBuffer is implemented.

6.ResultAtTheRateWordCount : @word count 

7.ResultHashWordCount : #word count

8.ResultSimpleWordCount : word count

9.README.txt 



****Instructions to run  AtTheRateWordCount.jar****
***************************************************

$ hadoop jar /home/cloudera/DIC_Twitter/JarFiles/AtTheRateWordCount.jar com.satish.hadoop.AtTheRateWordCount /user/cloudera/DIC_Twitter/Input/smallData /user/cloudera/DIC_Twitter/IntermediateOutputAtTheRateWordCount

$ hadoop jar /home/cloudera/DIC_Twitter/JarFiles/SortingResults.jar com.satish.hadoop.SortingResults /user/cloudera/DIC_Twitter/IntermediateOutputAtTheRateWordCount/part* /user/cloudera/DIC_Twitter/FinalOutputAtTheRateWordCount


****Instructions to run  SimpleWordCount.jar****
***************************************************

$ hadoop jar /home/cloudera/DIC_Twitter/JarFiles/SimpleWordCount.jar com.satish.hadoop.TweetCount /user/cloudera/DIC_Twitter/Input/smallData /user/cloudera/DIC_Twitter/IntermediateOutputSimpleWordCount

$ hadoop jar /home/cloudera/DIC_Twitter/JarFiles/SortingResults.jar com.satish.hadoop.SortingResults /user/cloudera/DIC_Twitter/IntermediateOutputSimpleWordCount/part* /user/cloudera/DIC_Twitter/FinalOutputSimpleWordCount


**Instructions to run  HashWordCount.jar**
***************************************************

$ hadoop jar /home/cloudera/DIC_Twitter/JarFiles/HashWordCount.jar com.satish.hadoop.HashWordCount /user/cloudera/DIC_Twitter/Input/smallData /user/cloudera/DIC_Twitter/IntermediateOutputHashWordCount

$ hadoop jar /home/cloudera/DIC_Twitter/JarFiles/SortingResults.jar com.satish.hadoop.SortingResults /user/cloudera/DIC_Twitter/IntermediateOutputHashWordCount/part* /user/cloudera/DIC_Twitter/FinalOutputHashWordCount













