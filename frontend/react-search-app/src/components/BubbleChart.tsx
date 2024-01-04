import React, { useEffect, useRef } from "react";
import { ClusteredData } from "./FetchedDataInterface";
import Highcharts from "highcharts";
import HC_more from "highcharts/highcharts-more";
import HighchartsReact from "highcharts-react-official";
HC_more(Highcharts);

interface BubbleChartProps {
  data: ClusteredData | undefined;
}

const BubbleChart: React.FC<BubbleChartProps> = ({ data }) => {
  const chartRef = useRef<Highcharts.Chart | null>(null);
  const bubblechartOptions: any = {
    chart: {
      type: "packedbubble",
    },
    series: undefined,
    plotOptions: {
      packedbubble: {
        layoutAlgorithm: {
          gravitationalConstant: 0.05,
          splitSeries: true,
          seriesInteraction: false,
          dragBetweenSeries: true,
          parentNodeLimit: true,
        },
      },
    },
    tooltip: {
      useHTML: true,
      pointFormat: `<b>Title:</b> {point.docName}<br>
                    <b>Relevance score:</b> {point.score}<br>
                    <b>Content:</b> {point.docExtract}`,
    },
    title: {
      text: "Search Result Clusters",
    },
  };

  useEffect(() => {
    if (chartRef.current && data) {
      // Update series data
      bubblechartOptions.series = data;

      // Create or update the chart
      chartRef.current = Highcharts.chart(
        chartRef.current as any,
        bubblechartOptions
      );
    }
  }, [data, bubblechartOptions]);

  return (
    <div>
      <HighchartsReact highcharts={Highcharts} options={bubblechartOptions} />
    </div>
  );
};

export default BubbleChart;
