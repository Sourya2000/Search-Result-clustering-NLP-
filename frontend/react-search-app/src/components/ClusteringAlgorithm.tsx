import React, { useState } from "react";
import styles from "./ClusteringAlgorithm.module.css";
import NumberOfClusters from "./NumberOfClusters";

interface ClusteringAlgorithmProps {
  onAlgorithmChange: (selectedAlgo: String | null) => void;
  handleFormDataChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const ClusteringAlgorithm = ({
  onAlgorithmChange,
  handleFormDataChange,
}: ClusteringAlgorithmProps) => {
  const [selectedAlgo, setSelectedAlgo] = useState<String | null>(null);

  const handleAlgorithmChange = (algorithm: String) => {
    setSelectedAlgo(algorithm);
    onAlgorithmChange(algorithm);
  };

  return (
    <div className={styles.radioBox}>
      <h4 className={styles.radioBoxHeading}>Clustering Algorithm Selection</h4>
      <label>
        <input
          type="radio"
          name="algorithm"
          value="kMeans"
          onChange={() => handleAlgorithmChange("kMeans")}
        />
        K-means
      </label>
      <label>
        <input
          type="radio"
          name="algorithm"
          value="other"
          onChange={() => handleAlgorithmChange("other")}
        />
        Other
      </label>

      {selectedAlgo === "kMeans" && <NumberOfClusters />}
    </div>
  );
};

export default ClusteringAlgorithm;
