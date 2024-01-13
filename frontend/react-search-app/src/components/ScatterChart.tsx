import React, { useEffect, useRef } from "react";
import { ClusteredData } from "./FetchedDataInterface";
import Highcharts from "highcharts";
import HC_more from "highcharts/highcharts-more";
import HighchartsReact from "highcharts-react-official";
import { convertedDataScatterChartFormat } from "./ScatterChartHelper";
HC_more(Highcharts);

interface ScatterChartProps {
  data: ClusteredData | undefined;
}

const ScatterChart: React.FC<ScatterChartProps> = ({ data }) => {
  const chartRef = useRef<Highcharts.Chart | null>(null);
  const scatterChartOptions: any = {
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
  };

  useEffect(() => {
    if (!chartRef.current && data) {
      // Chart is not yet created, create it now
      const graphSeriesData: any = convertedDataScatterChartFormat(data);
      console.log(data);
      console.log(graphSeriesData);
      scatterChartOptions.series = graphSeriesData;

      // Create the chart
      chartRef.current = Highcharts.chart(
        "chart-container",
        scatterChartOptions
      );
    } else if (chartRef.current && data) {
      const graphSeriesData: any = convertedDataScatterChartFormat(data);
      // Update series data
      chartRef.current.update({ series: graphSeriesData }, true, true);
    }
  }, [data, scatterChartOptions]);

  return (
    <div>
      <HighchartsReact
        highcharts={Highcharts}
        options={scatterChartOptions}
        containerProps={{ id: "chart-container" }}
      />
    </div>
  );
};

export default ScatterChart;
