import { Component, OnDestroy } from '@angular/core';
import { StatsBarData } from '../../data/stats-bar';
import { takeWhile } from 'rxjs/operators';

@Component({
  selector: 'ngx-stats-card-back',
  styleUrls: ['./stats-card-back.component.scss'],
  templateUrl: './stats-card-back.component.html',
})
export class StatsCardBackComponent implements OnDestroy {

  alive = true;
  activePeriodDate: string = '';
  activeFirstPeriod: number = 0;
  previousFirstPeriod: number = 0;
  previousPeriodDate: string = '';
  chartData: number[];

  constructor(private statsBarData: StatsBarData) {
    this.statsBarData.getStatsBarData()
      .pipe(takeWhile(() => this.alive))
      .subscribe((data) => {
        this.activeFirstPeriod = data.activeFirstPeriod;
        this.activePeriodDate = data.activePeriodDate;
        this.previousFirstPeriod = data.previousFirstPeriod;
        this.previousPeriodDate = data.previousPeriodDate;
        this.chartData = data.line;
      });
  }

  ngOnDestroy() {
    this.alive = false;
  }
}
