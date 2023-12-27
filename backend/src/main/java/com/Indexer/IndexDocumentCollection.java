package com.Indexer;

import java.io.IOException;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;


public class IndexDocumentCollection {
	public CustomAnalyzer getAnalyzer() throws IOException {
		CustomAnalyzer custAnalyser = CustomAnalyzer.builder()
										  .withTokenizer(StandardTokenizerFactory.class)
				   					      .addTokenFilter(StandardFilterFactory.class)
				   					      .addTokenFilter(LowerCaseFilterFactory.class)
				   					      .addTokenFilter(StopFilterFactory.class)
				   					      .addTokenFilter(PorterStemFilterFactory.class)
				   					      .build();
		return custAnalyser;
	}
}
