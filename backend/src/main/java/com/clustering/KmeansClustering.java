package com.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Constants.Constants;
import com.docSearch.RetrievedDocument;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.SelectedTag;

class KmeansClustering extends ClusterAlgorithm {

//	KmeansClustering(Instances dataInstances) {
//		super(dataInstances);
//	}

	@Override
	public Map<Integer, List<Map<String, Object>>> clusterDocuments(List<RetrievedDocument> retrievedDocuments,
			Map<String, Object> inputParamsMap) throws Exception {

		// Vectorize text data using TF-IDF weighting scheme
		TFIDFVectorizer tfidfVectorizer = new TFIDFVectorizer();
		Instances inputDataInstances = tfidfVectorizer.fitTransform(retrievedDocuments);
		int featureVectorSize = inputDataInstances.numAttributes();
		System.out.println(Constants.DOC_FEATURE_VECTOR_SIZE_STRING + featureVectorSize);

		// Determine number of clusters as per given input parameters
		int numberOfClusters = determineK(inputParamsMap, featureVectorSize, inputDataInstances);

		// K-means clustering
		SimpleKMeans kMeans = getKMeansModel(numberOfClusters, inputDataInstances);

		int[] labels = kMeans.getAssignments();
		return constructResponse(retrievedDocuments, labels);
	}

	private SimpleKMeans getKMeansModel(int numberOfClusters, Instances inpInstances) throws Exception {
		SimpleKMeans kMeans = new SimpleKMeans();
		kMeans.setNumClusters(numberOfClusters);
		kMeans.setSeed(10);
		kMeans.setPreserveInstancesOrder(true);
		kMeans.setInitializationMethod(new SelectedTag(SimpleKMeans.KMEANS_PLUS_PLUS, SimpleKMeans.TAGS_SELECTION));
		kMeans.buildClusterer(inpInstances);
		return kMeans;
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
			responseMap.computeIfAbsent(cluster, k -> new ArrayList<>()).add(entry);
		}

		return responseMap;
	}

	private int determineK(Map<String, Object> inputParamsMap, int featureVectorSize, Instances inpInstances)
			throws Exception {
		int numberOfClusters;
		boolean isOptKTrue = Boolean.parseBoolean((String) inputParamsMap.get(Constants.INPUT_PARAM_OPTIMAL_K_STRING));

		if (inputParamsMap.containsKey(Constants.INPUT_PARAM_OPTIMAL_K_STRING) && isOptKTrue) {
			// Get minimum between 50 and featureVectorSize, 50 being a hyperparameter
			int maxClusters = Math.min(Constants.CLUSTERING_KMEANS_MIN_CLUSTERS, featureVectorSize);

			return determineOptKwithElbowMethod(maxClusters, inpInstances);

		} else {
			int kVal = Integer.parseInt((String) inputParamsMap.get(Constants.INPUT_PARAM_K_VALUE_STRING));
			if (kVal > featureVectorSize) {
				numberOfClusters = (int) Math.sqrt(featureVectorSize);
			} else {
				numberOfClusters = kVal;
			}
		}

		return numberOfClusters;
	}

	private int determineOptKwithElbowMethod(int maxClusters, Instances inpInstances) throws Exception {

		double prevSSE = Double.MAX_VALUE;

		// Perform k-means clustering for different values of k
		for (int k = 2; k <= maxClusters; k++) {
			SimpleKMeans kMeans = getKMeansModel(k, inpInstances);
			// Get the sum of squared errors (SSE)
			double sse = kMeans.getSquaredError();

			// If reduction in SSE is less than 10%
			if ((prevSSE - sse) / (prevSSE) < 0.1) {
				return k;
			}

			prevSSE = sse;
		}
		return maxClusters;
	}
}
