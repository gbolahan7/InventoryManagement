import {Component} from "@angular/core";
import {DefaultEditor} from "ng2-smart-table";


@Component({
  selector: 'input-editor',
  styleUrls: ['./custom-input-editor.component.scss'],
  template: `
    <input type="number" nbInput
           [(ngModel)]="cell.newValue"
           [name]="cell.getId()"
           [placeholder]="cell.getTitle()"
           [disabled]="!cell.isEditable()"
           (click)="onClick.emit($event)"
           (keydown.enter)="onEdited.emit($event)"
           (keydown.esc)="onStopEditing.emit()">
    `,
})
export class CustomInputEditorComponent extends DefaultEditor {

  constructor() {
    super();
  }
}
