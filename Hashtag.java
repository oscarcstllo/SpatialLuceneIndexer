

import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

public class Hashtag {
	public static void main(String[] args){
		bagofWords();// TODO Auto-generated method stub
		//hashing();
	}
	private static void bagofWords() {
		List<String> tweet_text = new ArrayList<String>();
		BufferedReader nbr,SW;
		Set<String> stopWords = new LinkedHashSet<String>();
		try {
			SW= new BufferedReader(new FileReader("en.txt"));
			for(String lin;(lin = SW.readLine()) != null;)
				stopWords.add(lin.trim());
			SW.close();
			nbr = new BufferedReader(new FileReader("result"));
			String line;
			while((line=nbr.readLine())!=null){
				tweet_text.add(line);
			}
			nbr.close();
			//System.out.println("Length of text "+ tweet_text.size()); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,Integer> wordMap = new HashMap<String,Integer>();
		Map<Integer,Integer> wordFreq;
		List<String> wordList = new ArrayList<String>();
		//Integer[][] wVectors;
		int count = 1;
		String num;
		String reg = "(\\d+)?[\\.;,?!()'\":-_$%`&-\\/#]+";
		String apos = "(')([a-z])";
		String aps = "([A-Za-z]+)(')([a-z])";
		String url = "https?.*$";
		String mention = "@([A-Za-z0-9_]*{1,15})";
		String clean_token;
		for(String line: tweet_text) {
			String smallLine = line.toLowerCase();
			String filtered = smallLine.replaceAll(url, " ").replaceAll(aps,"$1").replaceAll(mention, "").replaceAll(reg," ");
			String[] st = filtered.split("\\s+");
			for(int i = 0; i<st.length;i++) {
				String token = st[i];
				token.replaceAll("\\p{P}", "");
				token = token.trim();
				clean_token = token.replaceAll("#","");
				if(clean_token.startsWith("#"))
					clean_token = clean_token.substring(1);
				if(clean_token.startsWith("#")||clean_token.equals("")||clean_token.equals("^$")||clean_token.equals("\\s+")||clean_token.length()<2)
					continue;
				//System.out.println(clean_token);
				if(!wordMap.containsKey(clean_token)){
					wordMap.put(clean_token,count);
					wordList.add(clean_token);
					count +=1;
					
				}
				//
			}
		}
		try {
			File fil = new File("vocab_hashtag.txt");
			FileWriter f = new FileWriter(fil,true);
			BufferedWriter b = new BufferedWriter(f);
			for(int i = 0;i<wordList.size();i++){
				b.write(wordList.get(i));
				b.write("\n");
			}
			b.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int line_number =0;
		for(String line: tweet_text) {
			line_number++;
			wordFreq = new HashMap<Integer,Integer>();
			String smallLine = line.toLowerCase();
			String filtered = smallLine.replaceAll(reg," ").replaceAll(aps,"$1").replaceAll(mention, "").replaceAll(url, " ");
			String[] st = filtered.split("\\s+");
			for(int i = 0; i<st.length;i++) {
				String token = st[i];
				token.replaceAll("\\p{P}", "");
				token = token.trim();
				if(token.equals("")||token.equals("^$")||token.equals("\\s+")||token.length()<2)
					continue;
				if(!wordFreq.containsKey(wordMap.get(token)))
					wordFreq.put(wordMap.get(token),1);
				else
					wordFreq.put(wordMap.get(token),wordFreq.get(wordMap.get(token))+1);
			}
			try {
				File fil = new File("bagofwords_hashtag.txt");
				FileWriter f = new FileWriter(fil,true);
				BufferedWriter b = new BufferedWriter(f);
				for(Integer token : wordFreq.keySet()){
					b.write(String.valueOf(line_number));
					b.write(" ");
					b.write(String.valueOf(token));
					b.write(" ");
					b.write(String.valueOf(wordFreq.get(token)));
					b.write("\n");
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
	private static void hashing() {
		BufferedReader br;
		List<String> hash = new ArrayList<String>();
		Map<String,Integer> author_map = new HashMap<String,Integer>();
		Map<String,List<String>> hash_tweets = new HashMap<String,List<String>>();
		Map<String,List<Integer>> doc_map = new HashMap<String,List<Integer>>();
		//		try{			 
		//			String sCurrentLine;
		//			br = new BufferedReader(new FileReader("data/hashtags"));
		//			while ((sCurrentLine = br.readLine()) != null) {
		//				hash.add(sCurrentLine);
		//			} 
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		String hashtag = "#[A-Za-z0-9]+";
		Pattern pattern = Pattern.compile(hashtag);
		int line=0;
		try{			 
			String sCurrentLine;
			br = new BufferedReader(new FileReader("data/hashtag_tweets_latest.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(sCurrentLine);
				line++;
				String result="";
				while (matcher.find()) {
					result = matcher.group();
					String tag = result.substring(1).toLowerCase();
					List<String> temp = new ArrayList<String>();
					List<Integer> num_tmp = new ArrayList<Integer>();
					if(!hash_tweets.containsKey(tag)){
						//hash++;
						num_tmp.add(line);
						doc_map.put(tag,num_tmp);
						temp.add(sCurrentLine);
						hash_tweets.put(tag,temp);
					}
					else{
						num_tmp=doc_map.get(tag);
						if(!num_tmp.contains(line))
							num_tmp.add(line);
						doc_map.put(tag,num_tmp);
						temp = hash_tweets.get(tag);
						temp.add(sCurrentLine);
						hash_tweets.put(tag,temp);
					}
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		try{			 
			String sCurrentLine;
			br = new BufferedReader(new FileReader("data/hashtag_author_latest.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				hash.add(sCurrentLine);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		int author_count=0;
		try{			 
			String sCurrentLine;
			br = new BufferedReader(new FileReader("data/hashtag_author_uniq.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				author_count++;
				author_map.put(sCurrentLine,author_count);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*for(String tag : hash_tweets.keySet()){
			try {
				File fil = new File(tag+".txt");
				FileWriter f = new FileWriter(fil,true);
				BufferedWriter b = new BufferedWriter(f);
				List<String> tmp = hash_tweets.get(tag);
				for(int i=0;i<tmp.size();i++){
					b.write(tmp.get(i));
					b.write(" ");
				}
				b.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
		}*/
		//System.out.println(doc_map);
		int count=0;
		for(String tag : doc_map.keySet()){
			count++;
			try {
				File fil = new File("author_document.txt");
				FileWriter f = new FileWriter(fil,true);
				BufferedWriter b = new BufferedWriter(f);
				List<Integer> tmp = doc_map.get(tag);
				Set<Integer> authors = new HashSet<Integer>();
				//System.out.println(tmp);
				for(int i=0;i<tmp.size();i++){
					if(!authors.contains(author_map.get(hash.get(tmp.get(i)-1))))
						authors.add(author_map.get(hash.get(tmp.get(i)-1)));
				}
				for(Integer a: authors){
					b.write(String.valueOf(count));
					b.write(" ");
					b.write(String.valueOf(a));
					b.write("\n");
				}
				b.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
			//b.close();
		}
	}

}
