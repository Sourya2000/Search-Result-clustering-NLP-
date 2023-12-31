package com.clustering;

import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

public class TFIDFVectorizer {
	public void fitTransform() {
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

	private Instances convertToWekaInstances(List<RetrievedDocument> retrievedDocuments) {
		// Create attributes for title, content, and score
		Attribute docNameAttr = new Attribute(Constants.DOC_FIELD_NAME_STRING, (List<String>) null);
		Attribute docExtractAttr = new Attribute(Constants.DOC_FIELD_EXTRACT_STRING, (List<String>) null);
		Attribute scoreAttr = new Attribute("score");

		// Create Instances object with the defined attributes
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(docNameAttr);
		attributes.add(docExtractAttr);
		attributes.add(scoreAttr);

		Instances wekaInstances = new Instances("DocumentInstances", attributes, retrievedDocuments.size());

		// Add data to Instances
		for (RetrievedDocument doc : retrievedDocuments) {
			double[] values = new double[3];
			values[0] = wekaInstances.attribute(Constants.DOC_FIELD_NAME_STRING).addStringValue(doc.getTitle());
			values[1] = wekaInstances.attribute(Constants.DOC_FIELD_EXTRACT_STRING).addStringValue(doc.getContent());
			values[2] = doc.getScore();

			DenseInstance instance = new DenseInstance(1.0, values);
			wekaInstances.add(instance);
		}

		// Set the class index (e.g., the 'score' attribute)
		wekaInstances.setClassIndex(attributes.size() - 1);

		return wekaInstances;
	}

}
