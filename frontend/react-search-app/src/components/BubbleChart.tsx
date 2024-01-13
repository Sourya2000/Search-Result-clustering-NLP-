import React, { useEffect, useRef } from "react";
import { ClusteredData } from "./FetchedDataInterface";
import Highcharts from "highcharts";
import HC_more from "highcharts/highcharts-more";
import HighchartsReact from "highcharts-react-official";
import convertedDataPackedBubbleFormat from "./BubbleChartHelper";
HC_more(Highcharts);

interface BubbleChartProps {
  data: ClusteredData | undefined;
}

const BubbleChart: React.FC<BubbleChartProps> = ({ data }) => {
  const chartRef = useRef<Highcharts.Chart | null>(null);
  const bubblechartOptions: Highcharts.Options = {
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
          dragBetweenSeries: false,
          parentNodeLimit: true,
        },
      },
    },
    tooltip: {
      useHTML: true,
      pointFormat: `<b>Title:</b> {point.name}<br>
                    <b>Relevance score:</b> {point.value}<br>
                    <b>Content:</b> {point.content}`,
    },
    // title: {
    //   text: "Packed Bubble Chart",
    // },
    title: undefined,
  };

  useEffect(() => {
    if (!chartRef.current && data) {
      // Chart is not yet created, create it now

      // Update series data
      // Highcharts.SeriesPackedbubbleOptions
      // Highcharts.Series;
      const graphSeriesData: any = convertedDataPackedBubbleFormat(data);
      // console.log(graphSeriesData);

      bubblechartOptions.series = graphSeriesData;

      // Create the chart
      chartRef.current = Highcharts.chart(
        "chart-container",
        bubblechartOptions
      );
    } else if (chartRef.current && data) {
      const graphSeriesData: any = convertedDataPackedBubbleFormat(data);
      // console.log(graphSeriesData);
      // Update series data
      chartRef.current.update({ series: graphSeriesData }, true, true);
    }
  }, [data, bubblechartOptions]);

  return (
    <div>
      <HighchartsReact
        highcharts={Highcharts}
        options={bubblechartOptions}
        containerProps={{ id: "chart-container" }}
      />
    </div>
  );
};

export default BubbleChart;
