import { Component } from '@angular/core';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
   _dialogueOpen: boolean = false;

    async _formularInput(){
      this._dialogueOpen = !this._dialogueOpen;
    }

  
}
