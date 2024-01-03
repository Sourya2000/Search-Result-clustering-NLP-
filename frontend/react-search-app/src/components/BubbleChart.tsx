import React, { useEffect, useRef } from "react";
import Chart from "chart.js/auto";
import { Document, ClusteredData } from "./FetchedDataInterface";

interface BubbleChartProps {
  data: ClusteredData | undefined;
}

const BubbleChart: React.FC<BubbleChartProps> = ({ data }) => {
  const chartRef = useRef<HTMLCanvasElement | null>(null);

  useEffect(() => {
    if (chartRef.current && data) {
      const ctx = chartRef.current.getContext("2d");

      if (ctx) {
        // Aggregate data by clusters
        const clusters = Object.values(data).map((cluster) => ({
          x: cluster.length, // x-axis: number of documents in the cluster
          y: cluster[0].score, // y-axis: average score of the cluster (adjust as needed)
          r: cluster.length * 5, // bubble size: proportional to the number of documents
        }));

        new Chart(ctx, {
          type: "bubble",
          data: {
            datasets: [
              {
                label: "Clusters",
                data: clusters,
                backgroundColor: "rgba(75, 192, 192, 0.2)",
                borderColor: "rgba(75, 192, 192, 1)",
              },
            ],
          },
          options: {
            scales: {
              x: {
                type: "linear",
                position: "bottom",
                title: {
                  display: true,
                  text: "Number of Documents in Cluster",
                },
              },
              y: {
                type: "linear",
                position: "left",
                title: {
                  display: true,
                  text: "Average Score of Cluster",
                },
              },
            },
          },
        });
      }
    }
  }, [data]);

  return <canvas ref={chartRef} />;
};

export default BubbleChart;
