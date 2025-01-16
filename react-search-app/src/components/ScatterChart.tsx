import React, { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HC_more from "highcharts/highcharts-more";
import HighchartsReact from "highcharts-react-official";
import { convertedDataScatterChartFormat } from "./ScatterChartHelper";
import { useData } from "./DataContext";
HC_more(Highcharts);

const ScatterChart = () => {
  const { fetchedData } = useData();
  const [scatterChartOptions, setScatterChartOptions] = useState<any>({
    chart: {
      type: "scatter",
      zoomType: "xy",
    },
    // title: {
    //   text: "Scatter Plot",
    //   align: "center",
    // },
    title: null,

    xAxis: {
      title: {
        text: "X-coordinate",
      },
      labels: {
        format: "{value}",
      },
      startOnTick: true,
      endOnTick: true,
      showLastLabel: true,
    },
    yAxis: {
      title: {
        text: "Y-coordinate",
      },
      labels: {
        format: "{value}",
      },
    },
    legend: {
      enabled: true,
    },
    plotOptions: {
      scatter: {
        marker: {
          radius: 2.5,
          symbol: "circle",
          states: {
            hover: {
              enabled: true,
              lineColor: "rgb(100,100,100)",
            },
          },
        },
        states: {
          hover: {
            marker: {
              enabled: false,
            },
          },
        },
        jitter: {
          x: 0.005,
        },
      },
    },
    tooltip: {
      pointFormat: "X: {point.x} <br/> Y: {point.y}",
    },
    series: undefined,
    // colors: undefined,
  });

  useEffect(() => {
    if (fetchedData) {
      const graphSeriesData: any = convertedDataScatterChartFormat(fetchedData);
      setScatterChartOptions({ series: graphSeriesData });
    }
  }, [fetchedData]);

  return (
    <div>
      <HighchartsReact highcharts={Highcharts} options={scatterChartOptions} />
    </div>
  );
};

export default ScatterChart;
