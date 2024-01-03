import React, { useState } from "react";
import styles from "./ClusteringAlgorithm.module.css";
import NumberOfClusters from "./NumberOfClusters";

export interface ClusteringAlgorithmProps {
  onAlgorithmChange: (selectedAlgo: String | null) => void;
  handleFormDataChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const ClusteringAlgorithm = ({
  onAlgorithmChange,
  handleFormDataChange,
}: ClusteringAlgorithmProps) => {
  const [selectedAlgo, setSelectedAlgo] = useState<String | null>(null);

  const handleAlgorithmChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedAlgo(e.target.value);
    onAlgorithmChange(e.target.value);
    handleFormDataChange(e);
  };

  return (
    <div className={styles.radioBox}>
      <h4 className={styles.radioBoxHeading}>Clustering Algorithm Selection</h4>
      <label>
        <input
          type="radio"
          name="algorithm"
          value="kMeans"
          onChange={handleAlgorithmChange}
        />
        K-means
      </label>
      <label>
        <input
          type="radio"
          name="algorithm"
          value="other"
          onChange={handleAlgorithmChange}
        />
        Other
      </label>

      {selectedAlgo === "kMeans" && (
        <NumberOfClusters handleFormDataChange={handleFormDataChange} />
      )}
    </div>
  );
};

export default ClusteringAlgorithm;
