package com.clustering;

import java.util.List;
import java.util.Map;

import com.docSearch.RetrievedDocument;

import weka.core.Instances;

abstract class ClusterAlgorithm {
	
	Instances dataInstances;
	
//	ClusterAlgorithm(Instances dataInstances) {
//		this.dataInstances = dataInstances;
//	}

	Map<Integer, List<Map<String, Object>>> clusterDocuments(List<RetrievedDocument> retrievedDocuments, 
			Map<String, Object> inputParamsMap) throws Exception {
		return null;
	}
}
