import React from "react";
import LeftPanel from "./LeftPanel";
import RightPanel from "./RightPanel";
import styles from "./SplitScreen.module.css";

const SplitScreen: React.FC = () => {
  return (
    <div className={styles.splitContainer}>
      <LeftPanel />
      <RightPanel />
    </div>
  );
};

export default SplitScreen;
