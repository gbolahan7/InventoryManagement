import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  Output,
  SimpleChanges
} from '@angular/core';
import { NbThemeService } from '@nebular/theme';
import { delay, takeWhile } from 'rxjs/operators';

@Component({
  selector: 'ngx-earning-pie-chart',
  styleUrls: ['./earning-card-back.component.scss'],
  template: `
    <div echarts
         class="echart"
         [options]="options"
         (chartInit)="onChartInit($event)"
         (chartClick)="onChartClick($event)">
    </div>
  `,
})
export class EarningPieChartComponent implements AfterViewInit, OnDestroy, OnChanges {

  @Output() selectPie = new EventEmitter<{value: number; name: string; color: string}>();
  @Input() values: {value: number; name: string; }[];
  @Input() defaultSelectedCurrency: string;

  private alive = true;

  options: any = {};
  echartsInstance;

  constructor(private theme: NbThemeService) {
  }

  onChartInit(ec) {
    this.echartsInstance = ec;
  }

  onChartClick(event) {
    const pieData = {
      value: event.value,
      name: event.name,
      color: event.color.colorStops[0].color,
    };

    this.emitSelectPie(pieData);
  }

  emitSelectPie(pieData: {value: number; name: string; color: any}) {
    this.selectPie.emit(pieData);
  }

  ngAfterViewInit() {
    this.theme.getJsTheme()
      .pipe(
        takeWhile(() => this.alive),
        delay(1),
      )
      .subscribe(config => {
        const variables = config.variables;
        this.options = this.getOptions(variables);
        const defaultSelectedData =
          this.options.series[0].data.find((item) => item.name === this.defaultSelectedCurrency);
        if(defaultSelectedData == undefined || !defaultSelectedData) return;
        const color = defaultSelectedData.itemStyle.normal.color.colorStops[0].color;
        const pieData = {
          value: defaultSelectedData.value,
          name: defaultSelectedData.name,
          color,
        };

        this.emitSelectPie(pieData);
      });
  }

  getOptions(variables) {
    const earningPie: any = variables.earningPie;

    return {
      tooltip: {
        trigger: 'item',
        formatter: '',
      },
      series: this.getSeries(earningPie, variables),
    };
  }

  ngOnDestroy() {
    this.alive = false;
  }

  ngOnChanges(changes: SimpleChanges): void {
      this.ngAfterViewInit()
  }

  getSeries(earningPie: any, variables: any): [any] {
    return [
      {
        name: ' ',
        clockWise: true,
        hoverAnimation: false,
        type: 'pie',
        center: earningPie.center,
        radius: earningPie.radius,
        data: this.getDataSeries(variables),
      },
    ]
  }

  getDataSeries(variables: any): any[] {
    let series = [];
    if(this.values && this.values.length > 0) {
      for(let i = 0; i < this.values.length; i++) {
        let colour = this.getRandomColor();
        let processor = {
          value: this.values[i].value,
          name: this.values[i].name,
          label: {
            normal: {
              position: 'center',
              formatter: '',
              textStyle: {
                fontSize: '22',
                fontFamily: variables.fontSecondary,
                fontWeight: '600',
                color: variables.fgHeading,
              },
            },
          },
          tooltip: {
            show: true,
          },
          itemStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: colour,
                },
                {
                  offset: 1,
                  color: colour,
                },
              ]),
              shadowColor: 'rgba(0, 0, 0, 0)',
              shadowBlur: 0,
              shadowOffsetX: 0,
              shadowOffsetY: 3,
            },
          },
        };
        series.push(processor);
      }
    }
    return series;
  }

  getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
}
