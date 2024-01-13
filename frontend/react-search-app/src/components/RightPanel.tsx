import React, { useState } from "react";
import styles from "./RightPanel.module.css";
import { useData } from "./DataContext";
import BubbleChart from "./BubbleChart";
import ScatterChart from "./ScatterChart";
import Box from "@mui/material/Box";
import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";

const RightPanel: React.FC = () => {
  const { fetchedData } = useData();
  const [value, setValue] = React.useState(0);

  const handleChange = (event: React.SyntheticEvent, newValue: number) => {
    setValue(newValue);
  };

  return (
    <div className={styles.rightPanel}>
      <h3 style={{ marginTop: "4px", marginBottom: "2px" }}>
        Results - Clustering
      </h3>

      <Box
        sx={{ width: "100%", bgcolor: "background.paper", marginTop: "0px" }}
      >
        <Tabs value={value} onChange={handleChange} centered>
          <Tab label="Packed Bubble" />
          <Tab label="Scatter Chart" />
        </Tabs>
      </Box>
      {value === 0 && <BubbleChart data={fetchedData} />}
      {value === 1 && <ScatterChart data={fetchedData} />}
    </div>
  );
};

export default RightPanel;
