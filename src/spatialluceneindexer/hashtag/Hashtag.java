
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hashtag {
	public void main(String[] args){
		bagofWords();
	}
	private void bagofWords() {
		BufferedReader br;
		Set<String> hash = new HashSet<String>();
		Map<String,List<String>> hash_tweets = new HashMap<String,List<String>>();
		try{			 
			String sCurrentLine;
			br = new BufferedReader(new FileReader("hashtags"));
			while ((sCurrentLine = br.readLine()) != null) {
				hash.add(sCurrentLine);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		String hashtag = "#[A-Za-z0-9]+";
		Pattern pattern = Pattern.compile(hashtag);
		try{			 
			String sCurrentLine;
			br = new BufferedReader(new FileReader("hash_tweets.txt"));
			while ((sCurrentLine = br.readLine()) != null) {
				Matcher matcher = pattern.matcher(sCurrentLine);
				String result="";
				while (matcher.find()) {
					result = matcher.group();
					System.out.println(result);
				}
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
