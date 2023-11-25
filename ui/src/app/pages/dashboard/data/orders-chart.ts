import {Observable} from "rxjs";
import {OrderProfitChartSummary} from "./orders-profit-chart";

export interface OrdersChart {
  chartLabel: string[];
  linesData: number[][];
}

export abstract class OrdersChartData {
  abstract getOrdersChartData(period: string): Observable<OrdersChart>;
  abstract getOrdersSummaryChartData(): Observable<OrderProfitChartSummary[]>
}
