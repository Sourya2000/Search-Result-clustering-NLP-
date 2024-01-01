package com.SearchEngine.SearchApp;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.Indexer.IndexDocumentCollection;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//      Indexing Document Collections at start of the server 
        System.out.println("Server is started. Indexing the Document Collection....");
        IndexDocumentCollection.indexDocuments();
        System.out.println("Done");
    }
}
