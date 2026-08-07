import { Component, OnInit } from '@angular/core';
import { BannerResponse } from '../../models/banner.model';
import { BannerService } from '../../services/banner.service';
import { environment } from 'src/app/services/environments';


@Component({
  selector: 'app-banner-list',
  templateUrl: './banner-list.component.html',
  styleUrls: ['./banner-list.component.scss']
})
export class BannerListComponent implements OnInit {


  banners: BannerResponse[] = [];
  baseImageUrl = environment.baseImageUrl;

  loading = false;


  constructor(
    private bannerService: BannerService
  ){}



  ngOnInit(): void {

    this.loadBanners();

  }



  loadBanners(): void {


    this.loading = true;


    this.bannerService.getAllBanners()
      .subscribe({

        next:(response)=>{

//           console.log('BASE IMAGE URL:', this.baseImageUrl);
// console.log('BANNER IMAGE URL:', this.banners.map(banner => banner.imageUrl));
// console.log(
//   'FINAL IMAGE URL:',
//   this.baseImageUrl + this.banners.map  (banner => banner.imageUrl)
// );

// console.log('First Banner:',  response[0]);

console.log(
  'First Banner Image URL:',
  response[0]?.imageUrl
);

          this.banners = response;

          // console.log(
          //   "Banners:",
          //   this.banners
          // );

          this.loading = false;

        },


        error:(err)=>{

          console.error(err);

          this.loading = false;

        }

      });

  }





  deleteBanner(id:number){


    const confirmDelete =
      confirm(
        "Are you sure you want to delete this banner?"
      );


    if(!confirmDelete){
      return;
    }


    this.bannerService.deleteBanner(id)
      .subscribe({

        next:()=>{

          alert(
            "Banner deleted successfully"
          );


          this.loadBanners();

        },


        error:(err)=>{

          console.error(err);

        }

      });

  }





}