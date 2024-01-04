import React from "react";
import styles from "./RightPanel.module.css";
import { useData } from "./DataContext";
import BubbleChart from "./BubbleChart";

const RightPanel: React.FC = () => {
  const { fetchedData } = useData();

  return (
    <div className={styles.rightPanel}>
      {/* <h2>Results - Clustering</h2> */}
      <BubbleChart data={fetchedData} />
    </div>
  );
};

export default RightPanel;
