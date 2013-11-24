package spatialluceneindexer.bow;

//package spatialluceneindexer.bow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class createVector {
	public static void make(List<String> tweet_text, String output_train, Set<String> stopWords) throws IOException{
		Map<String,Integer> wordMap = new HashMap<String,Integer>();
		Map<Integer,Integer> wordFreq = new HashMap<Integer,Integer>();
		//Integer[][] wVectors;
		int count = 1;
		String num;
		String reg = "(\\d+)?[\\.;,?!()'\":-@_$%`&]+";
		String apos = "(')([a-z])";
		String aps = "([A-Za-z]+)(')([a-z])";
		String url = "https?.*";
		String mention = "@([A-Za-z0-9_]*{1,15})";

		for(String line: tweet_text) {
			String smallLine = line.toLowerCase();
			//.replaceAll("[^a-zA-Z@]", " ")
			smallLine.replaceAll(reg,"");
			smallLine.replaceAll(aps,"$1");
			StringTokenizer st = new StringTokenizer(smallLine);
			try {
				File fil = new File("smaller_vocab.txt");
				FileWriter f = new FileWriter(fil,true);
				BufferedWriter b = new BufferedWriter(f);
				while (st.hasMoreTokens()) {
					String token = st.nextToken();
					if (token.matches(mention) || token.matches(url) ||token.matches(reg) || token.matches(apos)|| token.matches("\\d+")||stopWords.contains(token))
						continue;
					if(!wordMap.containsKey(token)){
						wordMap.put(token,count);
						count +=1;
					}
					b.write(token);
					b.write(" ");
					//
				}
				b.write("\n");
				b.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}
	/*File fil = new File("vocab.txt");
		FileWriter f = new FileWriter(fil.getAbsoluteFile());
		BufferedWriter b = new BufferedWriter(f);
		try {
			for(String key: wordMap.keySet()){
				b.write(key);
				b.write("\n");
			}
			b.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	/*wVectors = new Integer[tweet_text.size()][wordMap.size()+1];
		for(int i =0; i<tweet_text.size();i++){
			for(int j =0;j<=wordMap.size();j++){
				wVectors[i][j]=0;
			}
		}*/
	/*File file = new File(output_train);
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for(int j=0;j<tweet_text.size();j++){
			StringTokenizer st = new StringTokenizer(tweet_text.get(j));
			//String reg = "(\\d+)?[\\.;,?!()'\":-@_$%`&]+";
			//String apos = "(')([a-z])";
			while (st.hasMoreTokens()) {
				String token = st.nextToken().toLowerCase();
				if (token.matches(mention) || token.matches(url) || token.matches(reg) || token.matches(apos) || token.matches("\\d+") ||stopWords.contains(token))
					continue;
				//int val = wordMap.get(token);
				if(!wordFreq.containsKey(token))
					wordFreq.put(wordMap.get(token),1);
				else
					wordFreq.put(wordMap.get(token), wordFreq.get(token)+1);
			}
			try {
				for(int i=0;i<143255;i++){
					bw.write(j);
					bw.write(" ");
					bw.write(i);
					bw.write(" ");
					if(wordFreq.containsKey(i))
						bw.write(wordFreq.get(i));
					else
						bw.write(0);
					bw.write("\n");
				}
				bw.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}


}*/
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		List<String> tweet_text = new ArrayList<String>();
		BufferedReader nbr,SW;
		Set<String> stopWords = new LinkedHashSet<String>();
		try {
			SW= new BufferedReader(new FileReader("en.txt"));
			for(String lin;(lin = SW.readLine()) != null;)
				stopWords.add(lin.trim());
			SW.close();
			nbr = new BufferedReader(new FileReader("smaller_tweet.txt"));
			String line;
			while((line=nbr.readLine())!=null){
				//Object obj = parser.parse(line);
				//JSONObject jsonObject = (JSONObject) obj;
				//String text = (String) jsonObject.get("text");
				//tweet_text.add(text.replaceAll("http.*?\\s", " "));
				tweet_text.add(line);
			}
			System.out.println("Length of text "+ tweet_text.size()); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} /*catch (ParseException e) {
			e.printStackTrace();
		}*/
		make(stopWords);
		//premake(tweet_text,stopWords);
		/*try{
			//make(tweet_text,"bagofwords.txt",stopWords);

			//make(stopWords);
		}
		catch(IOException x){
			x.printStackTrace();
		}*/
	}
	private static void premake(List<String> tweet_text, Set<String> stopWords) {
		Map<String,Integer> wordMap = new HashMap<String,Integer>();
		Map<Integer,Integer> wordFreq = new HashMap<Integer,Integer>();
		//Integer[][] wVectors;
		int count = 1;
		String num;
		String reg = "(\\d+)?[\\.;,?!()'\":-_$%`&-\\/]+";
		String apos = "(')([a-z])";
		String aps = "([A-Za-z]+)(')([a-z])";
		String url = "https?.*$";
		String mention = "@([A-Za-z0-9_]*{1,15})";

		for(String line: tweet_text) {
			String smallLine = line.toLowerCase();
			String filtered = smallLine.replaceAll(reg," ").replaceAll(aps,"$1").replaceAll(mention, "").replaceAll(url, " ");
			String[] st = filtered.split("\\s+");
			try {
				File fil = new File("smaller_vocab.txt");
				FileWriter f = new FileWriter(fil,true);
				BufferedWriter b = new BufferedWriter(f);
				for(int i = 0; i<st.length;i++) {
					String token = st[i];
					token.replaceAll("\\p{P}", "");
					token = token.trim();
					if(token.equals("")||token.equals("^$")||token.equals("\\s+")||token.length()<2)
						continue;
					System.out.println(token);
					if(!wordMap.containsKey(token)){
						wordMap.put(token,count);
						b.write(token);
						b.write("\n");
						count +=1;
					}
					//
				}
				b.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}
	private static void make(Set<String> stopWords) {
		Map<String,Integer> wordMap = new HashMap<String,Integer>();
		Map<Integer,Integer> wordFreq = new HashMap<Integer,Integer>();
		String num;
		String reg = "(\\d+)?[\\.;,?!()'\":-_$%`&]+";
		String apos = "(')([a-z])";
		String aps = "([A-Za-z]+)(')([a-z])";
		String url = "https?.*";
		String mention = "@([A-Za-z0-9_]*{1,15})";
		BufferedReader br;
		int count1 = 1;
		try {			 
			String sCurrentLine;
			br = new BufferedReader(new FileReader("smaller_vocab.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				wordMap.put(sCurrentLine, count1);
				count1++;
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		/*File fil = new File("vocab.txt");
		FileWriter f = new FileWriter(fil.getAbsoluteFile());
		BufferedWriter b = new BufferedWriter(f);
		try {
			for(String key: wordMap.keySet()){
				b.write(key);
				b.write("\n");
			}
			b.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		/*wVectors = new Integer[tweet_text.size()][wordMap.size()+1];
		for(int i =0; i<tweet_text.size();i++){
			for(int j =0;j<=wordMap.size();j++){
				wVectors[i][j]=0;
			}
		}*/
		File file = new File("bagofwords.txt");
		FileWriter fw ;
		BufferedWriter bw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			String sCurrentLine;
			br = new BufferedReader(new FileReader("smaller_tweet.txt"));
			int count = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				count++;
				String filtered = sCurrentLine.replaceAll(reg," ").replaceAll(aps,"$1").replaceAll(mention, "").replaceAll(url, " ");
				//System.out.println(filtered);
				String[] st = filtered.split("\\s+");
				//StringTokenizer st = new StringTokenizer(sCurrentLine);
				//String reg = "(\\d+)?[\\.;,?!()'\":-@_$%`&]+";
				//String apos = "(')([a-z])";
				for(int i = 0;i<st.length;i++) {
					String token = st[i].toLowerCase();
					token.replaceAll("\\p{P}", "");
					token = token.trim();
					//if (token.matches(mention) || token.matches(url) || token.matches(reg) || token.matches(apos) || token.matches("\\d+") ||stopWords.contains(token))
					//continue;
					if(token.equals("")||token.equals("^$")||token.equals("\\s+")||token.length()<2)
						continue;
					//int val = wordMap.get(token);
					if(!wordFreq.containsKey(token))
						wordFreq.put(wordMap.get(token),1);
					else
						wordFreq.put(wordMap.get(token), wordFreq.get(token)+1);
				}
				try {
					for(int i=1;i<=9372;i++){
						if(wordFreq.containsKey(i)){
							bw.write(String.valueOf(count));
							bw.write(" ");
							bw.write(String.valueOf(i));
							bw.write(" ");
							bw.write(String.valueOf(wordFreq.get(i)));
							bw.write("\n");
						}
					}
					//bw.close();
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		//bw.close();
	}
}
