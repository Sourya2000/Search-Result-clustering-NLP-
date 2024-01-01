package com.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

class KmeansClustering {

	public Map<Integer, List<Map<String, Object>>> clusterDocuments(List<RetrievedDocument> retrievedDocuments, 
			Map<String, Object> inputParamsMap) throws Exception {
		
		// Vectorize text data using TF-IDF weighting scheme
        TFIDFVectorizer tfidfVectorizer = new TFIDFVectorizer();
        Instances inputDataInstances = tfidfVectorizer.fitTransform(retrievedDocuments);
        
        // Determine number of clusters as per given input parameters
        int numberOfClusters = determineK(inputParamsMap, retrievedDocuments.size());
        
        // K-means clustering
        SimpleKMeans kMeans = new SimpleKMeans();
        kMeans.setNumClusters(numberOfClusters);
        kMeans.buildClusterer(inputDataInstances);
        
        int[] labels = kMeans.getAssignments();
        return constructResponse(retrievedDocuments, labels);
	}

	private Map<Integer, List<Map<String, Object>>> constructResponse(List<RetrievedDocument> retrievedDocuments,
			int[] labels) {
		
		String[] docExtractList = retrievedDocuments.stream().map(RetrievedDocument::getContent).toArray(String[]::new);
		String[] docNameList = retrievedDocuments.stream().map(RetrievedDocument::getTitle).toArray(String[]::new);
		float[] docScoreList = new float[retrievedDocuments.size()];
		for (int i = 0; i < retrievedDocuments.size(); i++) {
		    docScoreList[i] = retrievedDocuments.get(i).getScore();
		}
	
		// Add associated cluster to each data instance
		List<Map<String, Object>> responseDataStruct = new ArrayList<>();
		for (int i = 0; i < docExtractList.length; i++) {
			Map<String, Object> entry = new HashMap<>();
			entry.put(Constants.DOC_FIELD_NAME_STRING, docNameList[i]);
			entry.put(Constants.DOC_FIELD_EXTRACT_STRING, docExtractList[i]);
			entry.put(Constants.DOC_FIELD_SCORE_STRING, docScoreList[i]);
			entry.put(Constants.DOC_FIELD_CLUSTER_STRING, labels[i]);
			responseDataStruct.add(entry);
		}

		// Group data by cluster
		Map<Integer, List<Map<String, Object>>> responseMap = new HashMap<>();
		for (Map<String, Object> entry : responseDataStruct) {
			int cluster = (int) entry.get(Constants.DOC_FIELD_CLUSTER_STRING);
			responseMap.computeIfAbsent(cluster, k -> responseMap.getOrDefault(k, responseMap.get(k)));
			responseMap.get(cluster).add(entry);
		}

		return responseMap;
	}
	
	private int determineK(Map<String, Object> inputParamsMap, int retrievedDocumentsSize) {
		int numberOfClusters;
		
		if (inputParamsMap.containsKey(Constants.INPUT_PARAM_OPTIMAL_K_STRING) && 
        		(boolean) inputParamsMap.get(Constants.INPUT_PARAM_OPTIMAL_K_STRING)) {
			System.out.println("");
			numberOfClusters = 0;
		} else {
			if ((int) inputParamsMap.get(Constants.INPUT_PARAM_K_VALUE_STRING) > retrievedDocumentsSize ) {
				numberOfClusters = retrievedDocumentsSize;
			} else {
				numberOfClusters = (int) inputParamsMap.get(Constants.INPUT_PARAM_K_VALUE_STRING);
			}
		}
		
		return numberOfClusters;
	}
}
