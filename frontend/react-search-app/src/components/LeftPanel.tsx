import React from "react";
import styles from "./LeftPanel.module.css";
import InputForm from "./InputForm";

const LeftPanel: React.FC = () => {
  return (
    <div className={styles.leftPanel}>
      <h2>Left Panel</h2>
      <InputForm />
    </div>
  );
};

export default LeftPanel;
