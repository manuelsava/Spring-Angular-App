import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  loadedItem = 'students';
  
  onNavigate(item: string){
    this.loadedItem = item;
  }
}
