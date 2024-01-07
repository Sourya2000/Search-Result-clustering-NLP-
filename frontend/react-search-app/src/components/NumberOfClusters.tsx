import React from "react";
import styles from "./NumberOfClusters.module.css";

export interface NumberOfClustersProps {
  handleFormDataChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const NumberOfClusters = ({ handleFormDataChange }: NumberOfClustersProps) => {
  return (
    <div className={styles.radioBox}>
      <h4 className={styles.radioBoxHeading}>Number of Clusters</h4>
      {/* <span className={styles.warningText}>
        *If given number of clusters is less than feature vectors dimension,
        then floor of the square root of the feature vector dimension will be
        taken as the number of clusters.
      </span> */}
      <label>
        <input
          type="radio"
          name="kVal"
          value={3}
          checked
          onChange={handleFormDataChange}
        />
        3 Clusters
      </label>
      <label>
        <input
          type="radio"
          name="kVal"
          value={4}
          onChange={handleFormDataChange}
        />
        4 Clusters
      </label>
      <label>
        <input
          type="radio"
          name="kVal"
          value={5}
          onChange={handleFormDataChange}
        />
        5 Clusters
      </label>
      <label>
        <input
          type="radio"
          name="isOptK"
          value="true"
          onChange={handleFormDataChange}
        />
        Optimal Clusters
      </label>
    </div>
  );
};

export default NumberOfClusters;
