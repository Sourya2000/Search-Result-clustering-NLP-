import React, { useState } from "react";
import styles from "./InputForm.module.css";
import ClusteringAlgorithm from "./ClusteringAlgorithm";

const InputForm = () => {
  const [submitDisabled, setsubmitDisabled] = useState(false);
  const handleAlgorithmChange = (selectedAlgo: String | null) => {
    setsubmitDisabled(selectedAlgo !== "kMeans");
  };
  return (
    <div>
      <form action="" className={styles.input}>
        <input
          type="text"
          placeholder="Eg. India"
          className={styles.inputSearch}
        />

        <ClusteringAlgorithm onAlgorithmChange={handleAlgorithmChange} />

        <div>
          <button
            className={styles.inputSubmit}
            type="submit"
            disabled={submitDisabled}
          >
            Submit
          </button>
        </div>
      </form>
    </div>
  );
};

export default InputForm;
