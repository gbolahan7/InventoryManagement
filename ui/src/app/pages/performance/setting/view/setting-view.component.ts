import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {SettingService} from "../setting.service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {GenericResponse} from "../../../../core/utils/template/http-util";
import {PerformanceSetting} from "../setting.data";

@Component({
  selector: 'performance-setting-view',
  styleUrls: ['./setting-view.component.scss'],
  templateUrl: './setting-view.component.html',
})
export class SettingViewComponent implements OnInit, OnChanges {

  performanceSetting: PerformanceSetting = null;
  dataPresent: boolean = false;
  @Input() _id: number = 1;
  staffStyle: string = "display: none";
  staffChecked: boolean = false;

  constructor(private settingService: SettingService,
              private activatedRoute: ActivatedRoute,
              private router:Router, ) {}

  private fetchData(id: number): void {
    this.settingService.getPerformanceSetting(id)
      .subscribe(((p: object) => {
        const response: GenericResponse<PerformanceSetting> = p as GenericResponse<PerformanceSetting>;
        this.performanceSetting = response.data;
        if(this.performanceSetting && !!this.performanceSetting.staffPeriod && !!this.performanceSetting.staffStartTime) {
          this.staffChecked = true;
          this.staffStyle = "display: block";
        }
        this.dataPresent = true;
      }), (error) => console.log(error));
  }

  onClickEvent(): void {
    this.router.navigate(['pages/performance/setting/edit', this._id]);
  }

  ngOnInit(): void {
    if (!!this._id) {
      this.fetchData(this._id);
    } else {
      this.activatedRoute.params.subscribe((params: Params) => {
        this.fetchData(params.id);
      });
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes['_id']) {
      this.ngOnInit();
    }
  }


}
