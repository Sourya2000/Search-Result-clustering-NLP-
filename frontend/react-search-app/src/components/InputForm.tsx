import React, { useState } from "react";
import axios from "axios";
import styles from "./InputForm.module.css";
import ClusteringAlgorithm from "./ClusteringAlgorithm";
import { useData } from "./DataContext";

export interface formDataProps {
  searchString: string;
  algorithm: string;
  kVal?: string;
  isOptK?: string;
}

const InputForm = () => {
  const [submitDisabled, setsubmitDisabled] = useState<boolean>(true);
  const [formData, setFormData] = useState<formDataProps>({
    algorithm: "kMeans",
    kVal: "3",
    searchString: "",
  });
  const { setData } = useData();

  const handleAlgorithmChange = (selectedAlgo: String | null) => {
    setsubmitDisabled(selectedAlgo !== "kMeans");
  };

  const handleFormDataChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const prevFormData = formData;
    const updatedFormData = {
      ...prevFormData,
      [e.target.name]: e.target.value,
    };
    setFormData(updatedFormData);
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(e);
    try {
      const response = await axios.post(
        "http://localhost:8080/search",
        formData
      );
      // console.log("Response from backend:", response.data);
      console.log("Got Non-error response from backend server");
      setData(response.data);
    } catch (error) {
      console.error("Error submitting form:", error as Error);
    }
  };

  const isInputValid = () => {
    // Check if searchString is not empty or contains only spaces
    return formData.searchString.trim() !== "";
  };

  return (
    <div>
      <form className={styles.input} onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Eg. India"
          name="searchString"
          required
          className={styles.inputSearch}
          onChange={handleFormDataChange}
        />

        <ClusteringAlgorithm
          onAlgorithmChange={handleAlgorithmChange}
          handleFormDataChange={handleFormDataChange}
        />

        <div>
          <button
            // className={styles.inputSubmit}
            type="submit"
            disabled={submitDisabled || !isInputValid()}
          >
            Submit
          </button>
        </div>
      </form>
    </div>
  );
};

export default InputForm;
