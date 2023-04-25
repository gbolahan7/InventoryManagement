import { Observable } from 'rxjs';

export abstract class StatsBarData {
  abstract getStatsBarData(): Observable<{ activePeriodDate: string; activeFirstPeriod: number;previousPeriodDate: string; previousFirstPeriod: number; line: number[]; }>;
}
