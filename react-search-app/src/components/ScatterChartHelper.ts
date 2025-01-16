import { ClusteredData, Document } from "./FetchedDataInterface";

interface ConvertedDataScatterChartFormatProps {
  name: string;
  //   docName: string;
  id: string;
  //   score: number;
  //   content: string;
  marker: {
    symbol: string;
  };
  data: [number, number][];
  color: string;
}

export function convertedDataScatterChartFormat(data: ClusteredData) {
  const convertedData: ConvertedDataScatterChartFormatProps[] = [];

  for (const clusterKey in data) {
    const clusterData: Document[] = data[clusterKey];
    const cluster_color: string = generateRandomColor();
    const convertedDataItem: ConvertedDataScatterChartFormatProps = {
      name: `Cluster ${clusterKey}`,
      // docName: item.docName,
      id: `Cluster ${clusterKey}`,
      // score: item.score,
      // content: item.docExtract,
      marker: {
        symbol: "circle",
        //   color: cluster_color,
      },
      data: [],
      color: cluster_color,
    };
    clusterData.forEach((item: Document) => {
      convertedDataItem.data.push(item.coordinates);
    });
    convertedData.push(convertedDataItem);
  }

  return convertedData;
}

function generateRandomColor(): string {
  const randomComponent = () => Math.floor(Math.random() * 256);
  const alpha = 1;

  const color = `rgba(${randomComponent()}, ${randomComponent()}, ${randomComponent()}, ${alpha})`;
  return color;
}

export function generateRandomColors(n: number): string[] {
  const colors: string[] = [];
  for (let i = 0; i < n; i++) {
    colors.push(generateRandomColor());
  }
  return colors;
}
