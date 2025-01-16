import React, { useState } from "react";
import styles from "./RightPanel.module.css";
import { useData } from "./DataContext";
import BubbleChart from "./BubbleChart";
import ScatterChart from "./ScatterChart";


const RightPanel: React.FC = () => {
  return (
    <div className={styles.rightPanel}>
      <BubbleChart />
      {/* <ScatterChart /> */}
    </div>
  );
};

export default RightPanel;
