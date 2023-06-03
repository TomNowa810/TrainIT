import { Component } from '@angular/core';

@Component({
  selector: '[app-create-run-dialogue]',
  templateUrl: './create-run-dialogue.component.html',
  styleUrls: ['./create-run-dialogue.component.scss']
})
export class CreateRunDialogueComponent {
  _kmNumber!: Number;
  _date!: Date;

    async _submitFormular() {
        console.log(this._date);
        console.log(this._kmNumber);
    }
}
