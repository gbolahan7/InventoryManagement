import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
    NbAccordionModule,
    NbButtonModule,
    NbCardModule,
    NbCheckboxModule,
    NbDatepickerModule, NbIconModule,
    NbInputModule,
    NbListModule, NbProgressBarModule,
    NbRouteTabsetModule,
    NbSelectModule,
    NbStepperModule,
    NbTabsetModule,
    NbTagModule,
    NbUserModule,
} from '@nebular/theme';

import {ThemeModule} from '../../theme/theme.module';
import {DashboardComponent} from "./dashboard.component";
import {UserData} from "./data/users";
import {UserService} from "./service/users.service";
import {ElectricityData} from "./data/electricity";
import {ElectricityService} from "./service/electricity.service";
import {SmartTableData} from "./data/smart-table";
import {SmartTableService} from "./service/smart-table.service";
import {UserActivityData} from "./data/user-activity";
import {UserActivityService} from "./service/user-activity.service";
import {OrdersChartData} from "./data/orders-chart";
import {OrdersChartService} from "./service/orders-chart.service";
import {ProfitChartData} from "./data/profit-chart";
import {ProfitChartService} from "./service/profit-chart.service";
import {TrafficListData} from "./data/traffic-list";
import {TrafficListService} from "./service/traffic-list.service";
import {EarningData} from "./data/earning";
import {EarningService} from "./service/earning.service";
import {OrdersProfitChartData} from "./data/orders-profit-chart";
import {TrafficBarService} from "./service/traffic-bar.service";
import {OrdersProfitChartService} from "./service/orders-profit-chart.service";
import {TrafficBarData} from "./data/traffic-bar";
import {ProfitBarAnimationChartData} from "./data/profit-bar-animation-chart";
import {ProfitBarAnimationChartService} from "./service/profit-bar-animation-chart.service";
import {TemperatureHumidityService} from "./service/temperature-humidity.service";
import {TemperatureHumidityData} from "./data/temperature-humidity";
import {SolarData} from "./data/solar";
import {SolarService} from "./service/solar.service";
import {TrafficChartData} from "./data/traffic-chart";
import {TrafficChartService} from "./service/traffic-chart.service";
import {StatsBarData} from "./data/stats-bar";
import {StatsBarService} from "./service/stats-bar.service";
import {CountryOrderService} from "./service/country-order.service";
import {CountryOrderData} from "./data/country-order";
import {StatsProgressBarData} from "./data/stats-progress-bar";
import {StatsProgressBarService} from "./service/stats-progress-bar.service";
import {VisitorsAnalyticsService} from "./service/visitors-analytics.service";
import {VisitorsAnalyticsData} from "./data/visitors-analytics";
import {SecurityCamerasData} from "./data/security-cameras";
import {SecurityCamerasService} from "./service/security-cameras.service";
import {PeriodsService} from "./service/periods.service";
import {StatsCardFrontComponent} from "./profit-card/front-side/stats-card-front.component";
import {StatsAreaChartComponent} from "./profit-card/back-side/stats-area-chart.component";
import {StatsBarAnimationChartComponent} from "./profit-card/front-side/stats-bar-animation-chart.component";
import {ProfitCardComponent} from "./profit-card/profit-card.component";
import {ECommerceChartsPanelComponent} from "./charts-panel/charts-panel.component";
import {ChartPanelHeaderComponent} from "./charts-panel/chart-panel-header/chart-panel-header.component";
import {ChartPanelSummaryComponent} from "./charts-panel/chart-panel-summary/chart-panel-summary.component";
import {OrdersChartComponent} from "./charts-panel/charts/orders-chart.component";
import {ProfitChartComponent} from "./charts-panel/charts/profit-chart.component";
import {StatsCardBackComponent} from "./profit-card/back-side/stats-card-back.component";
import {TrafficRevealCardComponent} from "./traffic-reveal-card/traffic-reveal-card.component";
import {TrafficBarChartComponent} from "./traffic-reveal-card/back-side/traffic-bar-chart.component";
import {TrafficFrontCardComponent} from "./traffic-reveal-card/front-side/traffic-front-card.component";
import {TrafficBackCardComponent} from "./traffic-reveal-card/back-side/traffic-back-card.component";
import {TrafficBarComponent} from "./traffic-reveal-card/front-side/traffic-bar/traffic-bar.component";
import {TrafficCardsHeaderComponent} from "./traffic-reveal-card/traffic-cards-header/traffic-cards-header.component";
import {CountryOrdersComponent} from "./country-orders/country-orders.component";
import {CountryOrdersMapComponent} from "./country-orders/map/country-orders-map.component";
import {CountryOrdersChartComponent} from "./country-orders/chart/country-orders-chart.component";
import {ECommerceVisitorsAnalyticsComponent} from "./visitors-analytics/visitors-analytics.component";
import {
  ECommerceVisitorsStatisticsComponent
} from "./visitors-analytics/visitors-statistics/visitors-statistics.component";
import {
  ECommerceVisitorsAnalyticsChartComponent
} from "./visitors-analytics/visitors-analytics-chart/visitors-analytics-chart.component";
import {ECommerceLegendChartComponent} from "./legend-chart/legend-chart.component";
import {ECommerceUserActivityComponent} from "./user-activity/user-activity.component";
import {ECommerceProgressSectionComponent} from "./progress-section/progress-section.component";
import {SlideOutComponent} from "./slide-out/slide-out.component";
import {EarningCardComponent} from "./earning-card/earning-card.component";
import {EarningCardFrontComponent} from "./earning-card/front-side/earning-card-front.component";
import {EarningCardBackComponent} from "./earning-card/back-side/earning-card-back.component";
import {EarningPieChartComponent} from "./earning-card/back-side/earning-pie-chart.component";
import {EarningLiveUpdateChartComponent} from "./earning-card/front-side/earning-live-update-chart.component";
import {NgxEchartsModule} from "ngx-echarts";
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import {CountryOrdersMapService} from "./country-orders/map/country-orders-map.service";

const DATA_SERVICES = [
  { provide: UserData, useClass: UserService },
  { provide: ElectricityData, useClass: ElectricityService },
  { provide: SmartTableData, useClass: SmartTableService },
  { provide: UserActivityData, useClass: UserActivityService },
  { provide: OrdersChartData, useClass: OrdersChartService },
  { provide: ProfitChartData, useClass: ProfitChartService },
  { provide: TrafficListData, useClass: TrafficListService },
  { provide: EarningData, useClass: EarningService },
  { provide: OrdersProfitChartData, useClass: OrdersProfitChartService },
  { provide: TrafficBarData, useClass: TrafficBarService },
  { provide: ProfitBarAnimationChartData, useClass: ProfitBarAnimationChartService },
  { provide: TemperatureHumidityData, useClass: TemperatureHumidityService },
  { provide: SolarData, useClass: SolarService },
  { provide: TrafficChartData, useClass: TrafficChartService },
  { provide: StatsBarData, useClass: StatsBarService },
  { provide: CountryOrderData, useClass: CountryOrderService },
  { provide: StatsProgressBarData, useClass: StatsProgressBarService },
  { provide: VisitorsAnalyticsData, useClass: VisitorsAnalyticsService },
  { provide: SecurityCamerasData, useClass: SecurityCamerasService },
];

const SERVICES = [
  UserService,
  ElectricityService,
  SmartTableService,
  UserActivityService,
  OrdersChartService,
  ProfitChartService,
  TrafficListService,
  PeriodsService,
  EarningService,
  OrdersProfitChartService,
  TrafficBarService,
  ProfitBarAnimationChartService,
  TemperatureHumidityService,
  SolarService,
  TrafficChartService,
  StatsBarService,
  CountryOrderService,
  StatsProgressBarService,
  VisitorsAnalyticsService,
  SecurityCamerasService,
];

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        ThemeModule,
        NbTabsetModule,
        NbRouteTabsetModule,
        NbStepperModule,
        NbCardModule,
        NbButtonModule,
        NbListModule,
        NbAccordionModule,
        NbUserModule,
        NbInputModule,
        NbTagModule,
        NbSelectModule,
        NbDatepickerModule,
        NbCheckboxModule,
        NgxEchartsModule,
        NbIconModule,
        LeafletModule,
        NbProgressBarModule,
    ],
  declarations: [
    DashboardComponent,
    StatsCardFrontComponent,
    StatsAreaChartComponent,
    StatsBarAnimationChartComponent,
    ProfitCardComponent,
    ECommerceChartsPanelComponent,
    ChartPanelHeaderComponent,
    ChartPanelSummaryComponent,
    OrdersChartComponent,
    ProfitChartComponent,
    StatsCardBackComponent,
    TrafficRevealCardComponent,
    TrafficBarChartComponent,
    TrafficFrontCardComponent,
    TrafficBackCardComponent,
    TrafficBarComponent,
    TrafficCardsHeaderComponent,
    CountryOrdersComponent,
    CountryOrdersMapComponent,
    CountryOrdersChartComponent,
    ECommerceVisitorsAnalyticsComponent,
    ECommerceVisitorsAnalyticsChartComponent,
    ECommerceVisitorsStatisticsComponent,
    ECommerceLegendChartComponent,
    ECommerceUserActivityComponent,
    ECommerceProgressSectionComponent,
    SlideOutComponent,
    EarningCardComponent,
    EarningCardFrontComponent,
    EarningCardBackComponent,
    EarningPieChartComponent,
    EarningLiveUpdateChartComponent,
  ],
  providers: [
    ...SERVICES,
    ...DATA_SERVICES,
    CountryOrdersMapService,
  ],
})
export class DashboardModule {
}
