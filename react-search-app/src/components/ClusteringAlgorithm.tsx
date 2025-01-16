import React, { useState } from "react";
import styles from "./ClusteringAlgorithm.module.css";
import NumberOfClusters from "./NumberOfClusters";

export interface ClusteringAlgorithmProps {
  handleFormDataChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const ClusteringAlgorithm = ({
  handleFormDataChange,
}: ClusteringAlgorithmProps) => {

  const [selectedAlgo, setSelectedAlgo] = useState<String | null>("kMeans");

  const handleAlgorithmChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedAlgo(e.target.value);
    handleFormDataChange(e);
  };

  return (
      <NumberOfClusters handleFormDataChange={handleFormDataChange} />
  );
};

export default ClusteringAlgorithm;
