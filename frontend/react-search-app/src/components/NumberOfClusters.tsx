import React, { useState } from "react";
import styles from "./NumberOfClusters.module.css";

export interface NumberOfClustersProps {
  handleFormDataChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const NumberOfClusters = ({ handleFormDataChange }: NumberOfClustersProps) => {
  const [selectedOption, setselectedOption] = useState<string>("3");
  const handleRadioButton = (e: React.ChangeEvent<HTMLInputElement>) => {
    setselectedOption(e.target.value);
    handleFormDataChange(e);
  };
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
          checked={selectedOption === "3"}
          onChange={handleRadioButton}
        />
        3 Clusters
      </label>
      <label>
        <input
          type="radio"
          name="kVal"
          value={4}
          checked={selectedOption === "4"}
          onChange={handleRadioButton}
        />
        4 Clusters
      </label>
      <label>
        <input
          type="radio"
          name="kVal"
          value={5}
          checked={selectedOption === "5"}
          onChange={handleRadioButton}
        />
        5 Clusters
      </label>
      <label>
        <input
          type="radio"
          name="isOptK"
          value="true"
          checked={selectedOption === "true"}
          onChange={handleRadioButton}
        />
        Optimal Clusters
      </label>
    </div>
  );
};

export default NumberOfClusters;
