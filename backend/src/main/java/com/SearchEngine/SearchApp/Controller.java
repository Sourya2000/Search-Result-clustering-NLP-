package com.SearchEngine.SearchApp;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
		DocumentSearch documentSearch = new DocumentSearch();
		List<RetrievedDocument> retrievedDocuments = documentSearch.searchTopDocuments("accounting", 20);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(retrievedDocuments);
		return jsonString;
		
	}
}
