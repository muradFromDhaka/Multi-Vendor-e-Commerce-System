import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { LayoutService } from 'src/app/shared/services/layout.service';

@Component({
  selector: 'app-admin-sidebar',
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.scss']
})
export class AdminSidebarComponent {


    sidebarOpen$: Observable<boolean>;
  
    constructor(
      private layoutService: LayoutService,
      public authService: AuthService,
      private router: Router
    ) {
  
        console.log('VendorSidebar Loaded');
  
    this.sidebarOpen$ = this.layoutService.sidebarState$;
  
    this.sidebarOpen$.subscribe(value => {
      console.log('Sidebar State:', value);
    });
  
    }
  
    closeSidebar(): void {
  
      if (window.innerWidth < 992) {
        this.layoutService.closeSidebar();
      }
  
    }
  
  
    logOut(){
        this.authService.logOut();
        this.router.navigate(['/home'])
      }
      
}
