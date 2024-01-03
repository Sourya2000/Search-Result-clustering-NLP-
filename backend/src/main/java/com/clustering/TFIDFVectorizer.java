package com.clustering;

import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

class TFIDFVectorizer {
	public Instances fitTransform(List<RetrievedDocument> retrievedDocuments) throws Exception {
		StringToWordVector tfIdfVectorizer = new StringToWordVector();

//      Config for StringToWordVector to set it as tf-idf vectorizer
		tfIdfVectorizer.setIDFTransform(true);
		tfIdfVectorizer.setTFTransform(true);
		tfIdfVectorizer.setLowerCaseTokens(true);
		tfIdfVectorizer.setOutputWordCounts(false); // Output TF-IDF values instead of word counts
		// Word vectors will be built based on the entire dataset without considering the class labels
		tfIdfVectorizer.setDoNotOperateOnPerClassBasis(true);
		
//		Check if stopwords filtering and stemming helps in getting significantly better clusters representation
//        CharArraySet stopwordSet = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
//        String[] stopwordsArray = stopwordSet.toArray(new String[0]);
//        tfIdfVectorizer.setStemmer(new IteratedLovinsStemmer());
//        tfIdfVectorizer.setStopwordsHandler(new EnglishStopWordsHandler());
		
		Instances inputDataInstances = convertToWekaInstances(retrievedDocuments);
        tfIdfVectorizer.setInputFormat(inputDataInstances);
        Instances transformedDataInstances = Filter.useFilter(inputDataInstances, tfIdfVectorizer);
        return transformedDataInstances;
	}

	private Instances convertToWekaInstances(List<RetrievedDocument> retrievedDocuments) {
		// Create attributes for title, content, and score
		Attribute docNameAttr = new Attribute(Constants.DOC_FIELD_NAME_STRING, (List<String>) null);
		Attribute docExtractAttr = new Attribute(Constants.DOC_FIELD_EXTRACT_STRING, (List<String>) null);
		Attribute scoreAttr = new Attribute(Constants.DOC_FIELD_SCORE_STRING);

		// Create Instances object with the defined attributes
		ArrayList<Attribute> attributes = new ArrayList<>();
		attributes.add(docNameAttr);
		attributes.add(docExtractAttr);
		attributes.add(scoreAttr);

		Instances wekaInstances = new Instances(Constants.WEKA_INSTANCE_RELATION_NAME_STRING, attributes, retrievedDocuments.size());

		// Add data to Instances
		for (RetrievedDocument doc : retrievedDocuments) {
			double[] values = new double[3];
			values[0] = wekaInstances.attribute(Constants.DOC_FIELD_NAME_STRING).addStringValue(doc.getTitle());
			values[1] = wekaInstances.attribute(Constants.DOC_FIELD_EXTRACT_STRING).addStringValue(doc.getContent());
			values[2] = doc.getScore();

			DenseInstance instance = new DenseInstance(1.0, values);
			wekaInstances.add(instance);
		}

		// Set class index as negative number to indicate no target attribute present
		wekaInstances.setClassIndex(-1);

		return wekaInstances;
	}

}
