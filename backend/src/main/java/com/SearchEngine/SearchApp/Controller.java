package com.SearchEngine.SearchApp;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clustering.ClusterDocuments;
import com.docSearch.DocumentSearch;
import com.docSearch.RetrievedDocument;

@RestController
public class Controller {
	
	@GetMapping(path = "/hello")
	public String helloWorld() {
		return "Hello world";
	}
	
	@GetMapping(path = "test")
	public Map<Integer, List<Map<String, Object>>> search(Map<String, Object> inputParamsMap) throws Exception {
//----------------------------------Workflow------------------------------------------ 
//		Step 0 - Document Collection already indexed at the time of the start of the server
		
//		Step 1 - Search similar documents
		DocumentSearch documentSearch = new DocumentSearch();
		List<RetrievedDocument> retrievedDocuments = documentSearch.searchTopDocuments("accounting", 20);
		
//		Step 2 - Cluster the documents 
		return ClusterDocuments.cluster(retrievedDocuments, inputParamsMap);
		
	}
}
