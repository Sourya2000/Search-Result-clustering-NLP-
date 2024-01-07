export interface Document {
  score: number;
  cluster: number;
  docName: string;
  docExtract: string;
}

export interface ClusteredData {
  [key: string]: Document[];
}
