import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BannerResponse } from '../../models/banner.model';
import { BannerService } from '../../services/banner.service';


@Component({
  selector: 'app-banner-details',
  templateUrl: './banner-details.component.html',
  styleUrls: ['./banner-details.component.scss']
})
export class BannerDetailsComponent implements OnInit {


  banner!: BannerResponse;

  loading = false;



  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bannerService: BannerService
  ) {}



  ngOnInit(): void {

    this.loadBanner();

  }




  loadBanner(): void {


    const id = Number(
      this.route.snapshot.paramMap.get('id')
    );


    if(!id){
      return;
    }



    this.loading = true;


    this.bannerService.getBanner(id)
      .subscribe({

        next:(response)=>{

          this.banner = response;

          console.log(
            "Banner Details:",
            this.banner
          );

          this.loading = false;

        },


        error:(err)=>{

          console.error(err);

          this.loading = false;

        }

      });


  }




  backToList(){

    this.router.navigate([
      '/admin/banner-management'
    ]);

  }



}