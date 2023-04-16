import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
    _kmNumber!: Number;
    _isLogin!: boolean;
    _date!: Date;

    async _formularInput(){
      this._isLogin = true;
    }

    async _submitFormular() {
        console.log(this._date);
        console.log(this._kmNumber);
    }
}
