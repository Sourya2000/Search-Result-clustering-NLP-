import React from "react";
import styles from "./NumberOfClusters.module.css";

const NumberOfClusters = () => {
  return (
    <div className={styles.radioBox}>
      <h4 className={styles.radioBoxHeading}>Number of Clusters</h4>
      {/* <span className={styles.warningText}>
        *If given number of clusters is less than feature vectors dimension,
        then floor of the square root of the feature vector dimension will be
        taken as the number of clusters.
      </span> */}
      <label>
        <input type="radio" name="kVal" value="3" checked />3 Clusters
      </label>
      <label>
        <input type="radio" name="kVal" value="4" />4 Clusters
      </label>
      <label>
        <input type="radio" name="kVal" value="5" />5 Clusters
      </label>
      <label>
        <input type="radio" name="kVal" value="4" />
        Optimal Clusters
      </label>
    </div>
  );
};

export default NumberOfClusters;
