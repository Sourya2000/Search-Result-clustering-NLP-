import React, { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HC_more from "highcharts/highcharts-more";
import HighchartsReact from "highcharts-react-official";
import convertedDataPackedBubbleFormat from "./BubbleChartHelper";
import { useData } from "./DataContext";
HC_more(Highcharts);

const BubbleChart = () => {
  const { fetchedData } = useData();

  const [bubblechartOptions, setBubblechartOptions] =
    useState<Highcharts.Options>({
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
    });

  useEffect(() => {
    if (fetchedData) {
      const graphSeriesData: any = convertedDataPackedBubbleFormat(fetchedData);
      setBubblechartOptions({ series: graphSeriesData });
    }
  }, [fetchedData]);

  return (
    <div>
      <HighchartsReact highcharts={Highcharts} options={bubblechartOptions} />
    </div>
  );
};

export default BubbleChart;
