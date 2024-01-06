package com.SearchEngine.SearchApp;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Constants.Constants;
import com.clustering.ClusterDocuments;
import com.docSearch.DocumentSearch;
import com.docSearch.RetrievedDocument;

@RestController
public class Controller {

	@GetMapping(path = "/hello")
	public String helloWorld() {
		return "Hello world";
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(path = "/search")
	public Map<Integer, List<Map<String, Object>>> search(@RequestBody Map<String, Object> inputParamsMap)
			throws Exception {
	
	//First Check Supplied input parameter of the request
	if (!InputParamMapIsCorrent(inputParamsMap)) {
		throw new Exception(Constants.REQUEST_INPUT_PARAM_NOT_CORRECT_STRING);
	}
		
//----------------------------------Workflow------------------------------------------ 
//		Step 0 - Document Collection already indexed at the time of the start of the server

//		Step 1 - Search similar documents
		DocumentSearch documentSearch = new DocumentSearch();
		List<RetrievedDocument> retrievedDocuments = documentSearch.searchTopDocuments(inputParamsMap);

//		------------- Commented : Only for testing ----------------
//		Map<String, Object> inputParamsMapTest = new HashMap<String, Object>();
//		inputParamsMapTest.put(Constants.INPUT_PARAM_CLUST_ALGO_STRING, Constants.CLUSTERING_ALGO_KMEANS_STRING);
//		inputParamsMapTest.put(Constants.INPUT_PARAM_K_VALUE_STRING, 3);

//		Step 2 - Cluster the documents 
		return ClusterDocuments.cluster(retrievedDocuments, inputParamsMap);

	}

	private boolean InputParamMapIsCorrent(Map<String, Object> inputParamsMap) {
		// Input should have the name of algorithm to use and the search query
		if (inputParamsMap.containsKey(Constants.INPUT_PARAM_CLUST_ALGO_STRING)
				&& inputParamsMap.containsKey(Constants.INPUT_SEARCH_QUERY_STRING)) {

			// If K-means
			if (inputParamsMap
					.get(Constants.INPUT_PARAM_CLUST_ALGO_STRING) == Constants.CLUSTERING_ALGO_KMEANS_STRING) {
				
				// Then input should either have no. of clusters given OR optimal clusters parameter
				if (inputParamsMap.containsKey(Constants.INPUT_PARAM_K_VALUE_STRING)
						|| inputParamsMap.containsKey(Constants.INPUT_PARAM_OPTIMAL_K_STRING)) {
					return true;
				}
				return false;
			}

			// Add else-if above to accommodate other algorithms
			return false;

		}
		return false;

	}
}
