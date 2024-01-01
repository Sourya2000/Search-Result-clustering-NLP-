package com.clustering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

public class ClusterDocuments {
	private static final Map<String, Class<? extends ClusterAlgorithm>> clusterAlgoClassMap = new HashMap<>();

	static {
		clusterAlgoClassMap.put(Constants.CLUSTERING_ALGO_KMEANS_STRING, KmeansClustering.class);
	}

	public static Map<Integer, List<Map<String, Object>>> cluster(List<RetrievedDocument> retrievedDocuments, 
			Map<String, Object> inputParamsMap) throws Exception {
		
		if (clusterAlgoClassMap.containsKey(inputParamsMap.get(Constants.INPUT_PARAM_CLUST_ALGO_STRING))){
		// Get the relevant class of the given Algorithm
			Class<? extends ClusterAlgorithm> clusterAlgorithmClass = clusterAlgoClassMap.get(Constants.INPUT_PARAM_CLUST_ALGO_STRING);
		  	ClusterAlgorithm clusterAlgoObj = clusterAlgorithmClass.getDeclaredConstructor().newInstance();
			return clusterAlgoObj.clusterDocuments(retrievedDocuments, inputParamsMap);
		}else {
			System.out.println("Algorithm not present in the configuration");
			return null;
		}
		
	}
}
