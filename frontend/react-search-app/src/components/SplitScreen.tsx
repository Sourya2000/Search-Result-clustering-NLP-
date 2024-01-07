import React from "react";
import LeftPanel from "./LeftPanel";
import RightPanel from "./RightPanel";
import styles from "./SplitScreen.module.css";
import { DataProvider } from "./DataContext";

const SplitScreen: React.FC = () => {
  return (
    <div className={styles.splitContainer}>
      <DataProvider>
        <LeftPanel />
        <RightPanel />
      </DataProvider>
    </div>
  );
};

export default SplitScreen;
