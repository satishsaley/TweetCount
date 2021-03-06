import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.awt.Frame;
import java.io.*;
import java.util.List;
import java.util.StringTokenizer;

public class CollectTweets {

	static boolean  firstTime=true;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey("UrkUA4kVtrLfSnF9Lirg")
			.setOAuthConsumerSecret("rpdRIuV09XnjSXQWz6AsMf9UHZBlCsdh3Tqgy4B4vA")
			.setOAuthAccessToken("45519245-AbfXXOC0NOq9prqlm85VklS8FJBh6bBCoL4f6FARb")
			.setOAuthAccessTokenSecret("TCqxpb5D1LeRiunWB9OCsFSzC7wkjTDpIaADH8DzuXQ")

			.setClientURL("https://stream.twitter.com/1.1/statuses/firehose.json")
			;

			cb.setJSONStoreEnabled(true);
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();


			//OutputStreamWriter out=new OutputStreamWriter(outputFile);
			Query query = new Query("love");
			String previousFileName="";
			FileOutputStream outputFile=null;
			String osName=System.getProperty("os.name");

			for (int page = 1; page <=1; page++) {
				//System.out.println("\nPage: " + page);

				query.setResultType("recent");

				query.setCount(100); // set tweets per page to 100

				QueryResult qr = twitter.search(query);
				List<Status> qrTweets = qr.getTweets();

				byte [] buffer=new byte[1024];
				for(Status t : qrTweets) {

					String toFile="\r\n "+t.getId() + " $$ " + t.getCreatedAt() + " $$ " + t.getText() +"\r\n ";

					String s=t.getCreatedAt().toString();
					StringTokenizer st=new StringTokenizer(s);
					String fileName="";
					fileName=st.nextToken();
					fileName=fileName+"_"+st.nextToken();
					fileName=fileName+"_"+st.nextToken();
					st.nextToken();
					st.nextToken();
					fileName=fileName+"_"+st.nextToken()+".txt";


					if(osName.contains("windows"))
					{
						outputFile=new FileOutputStream("C:\\Users\\Satish\\workspace\\TwitterDB\\TwitterData\\"+fileName,true);
					}
					else
					{
						outputFile=new FileOutputStream("/home/cloudera/DIC_Twitter/InputFiles/"+fileName,true);
					}
						
					
					buffer=toFile.getBytes();
					outputFile.write(buffer); 
					outputFile.flush();
					//System.out.println(t.getId() + " - "+t.getUser()+"  " + t.getCreatedAt() + ": " + t.getText()  );
				}
				//System.out.println("inner for end");





			}
		} catch (Exception te) {
			//te.printStackTrace();
		   //System.out.println("Failed to get retweets: " + te.getMessage() );
			System.exit(-1);
		}
		//System.out.println("outer for end");

	}

}
