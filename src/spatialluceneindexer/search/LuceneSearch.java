package spatialluceneindexer.search;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author nazneen
 *
 */
public class LuceneSearch {
	public static String FIELD_PATH = "";
	public static String FIELD_CONTENTS = "";
	public static String INDEX_DIRECTORY="";
	public LuceneSearch(String fieldPath, String fieldContent, String indexDir, String query){
		this.FIELD_PATH=fieldPath;
		this.FIELD_CONTENTS=fieldContent;
		this.INDEX_DIRECTORY=indexDir;
		try {
			searchIndex(query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void searchIndex(String searchString) throws IOException, ParseException {
		System.out.println("Searching for '" + searchString + "'");
		File file = new File(INDEX_DIRECTORY);
		Directory directory = FSDirectory.open(file);
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		QueryParser queryParser = new QueryParser(Version.LUCENE_44, FIELD_CONTENTS, analyzer);
		Query query = queryParser.parse(searchString);
		TopDocs td = indexSearcher.search(query,100);
		ScoreDoc[] hits = td.scoreDocs;
		System.out.println("Number of hits: " + hits.length);

		for (int i = 0; i < hits.length; i++) {
		     int docId = hits[i].doc;
		     Document document = indexSearcher.doc(docId);
			String path = document.get(FIELD_PATH);
			System.out.println("Hit: " + path);
		}

	}
}