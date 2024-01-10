package com.SearchEngine.SearchApp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.Constants.Constants;
import com.Indexer.IndexDocumentCollection;

@Component
public class MyApplicationRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
//      Indexing Document Collections at start of the server 
		System.out.println(Constants.SERVER_START_INDEXING_START_STRING);
		IndexDocumentCollection.indexDocuments();
		System.out.println(Constants.INDEXING_COMPLETED_STRING);
	}
}
