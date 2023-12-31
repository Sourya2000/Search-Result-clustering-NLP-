package com.clustering;

import java.util.List;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;

import com.docSearch.RetrievedDocument;

import weka.core.stemmers.IteratedLovinsStemmer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class KmeansClustering {
	public static void clusterDocuments(List<RetrievedDocument> retrievedDocuments) {
        String[] docContentList = retrievedDocuments.stream().map(RetrievedDocument::getContent).toArray(String[]::new);
        String[] docTitleList = retrievedDocuments.stream().map(RetrievedDocument::getTitle).toArray(String[]::new);
//        float[] docScoreList = retrievedDocuments.stream().map(RetrievedDocument::getScore).toArray(float[]::new);
        

        StringToWordVector tfIdfVectorizer = new StringToWordVector();
        
//      Config for StringToWordVector to set it as tf-idf vectorizer
        tfIdfVectorizer.setIDFTransform(true);
        tfIdfVectorizer.setTFTransform(true);
        tfIdfVectorizer.setLowerCaseTokens(true);
        tfIdfVectorizer.setOutputWordCounts(false); // Output TF-IDF values instead of word counts
        
//		Check if stopwords filtering and stemming helps in getting significantly better clusters representation
//        CharArraySet stopwordSet = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
//        String[] stopwordsArray = stopwordSet.toArray(new String[0]);
//        tfIdfVectorizer.setStemmer(new IteratedLovinsStemmer());
//        tfIdfVectorizer.setStopwordsHandler(new EnglishStopWordsHandler());
      
//        tfIdfVectorizer.setInputFormat(retrievedDocuments);
	}
}





