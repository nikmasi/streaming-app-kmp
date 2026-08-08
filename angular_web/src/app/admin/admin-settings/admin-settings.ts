import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-admin-settings',
  standalone: true,
  imports: [FormsModule, RouterLink, RouterLinkActive],
  templateUrl: './admin-settings.html',
  styleUrl: './admin-settings.css'
})
export class AdminSettings {
  siteName = 'Streaming App';
  allowRegistration = true;
  maintenanceMode = false;
  maxUploadSize = 500;
  defaultMovieLanguage = 'English';

  saveSettings() {
    console.log('Settings saved:', {
      siteName: this.siteName,
      allowRegistration: this.allowRegistration,
      maintenanceMode: this.maintenanceMode,
      maxUploadSize: this.maxUploadSize,
      defaultMovieLanguage: this.defaultMovieLanguage
    });
  }
}
