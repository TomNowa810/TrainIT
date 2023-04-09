import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    
    _passwordValue: string | null | undefined;
    _usernameValue: string | null | undefined;

    _output: boolean = false;
    _loginPassed: boolean = false;

    async _clickEvent() {
      const userNameFinal: string = "Thomas";
      const passwordFinal: string = "MyPassword12";

      if(this._usernameValue == userNameFinal && 
        this._passwordValue == passwordFinal){
          this._output = true;
          this._loginPassed = true;
        } else {
          this._usernameValue = null;
          this._passwordValue = null;
        }
    }
}
