package com.SearchEngine.SearchApp;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clustering.KmeansClustering;
import com.docSearch.DocumentSearch;
import com.docSearch.RetrievedDocument;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class Controller {
	
	@GetMapping(path = "/hello")
	public String helloWorld() {
		return "Hello world";
	}
	
	@GetMapping(path = "test")
	public String search() throws IOException, ParseException {
//----------------------------------Workflow------------------------------------------ 
//		Step 0 - Document Collection already indexed at the time of the start of the server
		
//		Step 1 - Search similar documents
		DocumentSearch documentSearch = new DocumentSearch();
		List<RetrievedDocument> retrievedDocuments = documentSearch.searchTopDocuments("accounting", 20);
		
//		Step 2 - Map documents to json format 
//		ObjectMapper objectMapper = new ObjectMapper();
//		String jsonString = objectMapper.writeValueAsString(retrievedDocuments);
//		return jsonString;
		
//		Step 3 - Cluster the documents 
		KmeansClustering.clusterDocuments(retrievedDocuments);
		return "";
		
	}
}
