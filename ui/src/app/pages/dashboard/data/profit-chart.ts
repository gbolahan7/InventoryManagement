import {Observable} from "rxjs";
import {OrderProfitChartSummary} from "./orders-profit-chart";

export interface ProfitChart {
  chartLabel: string[];
  data: number[][];
}

export abstract class ProfitChartData {
  abstract getProfitChartData(period: string): Observable<ProfitChart>;
  abstract getProductsSummaryChartData(): Observable<OrderProfitChartSummary[]>;
}
