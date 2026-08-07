import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BannerService } from '../../services/banner.service';
import { BannerResponse } from '../../models/banner.model';
import { environment } from 'src/app/services/environments';


@Component({
  selector: 'app-banner-form',
  templateUrl: './banner-form.component.html',
  styleUrls: ['./banner-form.component.scss']
})
export class BannerFormComponent implements OnInit {

  baseImageUrl = environment.baseImageUrl;;

  bannerForm!: FormGroup;


  selectedImage!: File;


  imagePreview:string | null = null;


  bannerId!:number;


  isEditMode = false;


  loading = false;



  constructor(
    private fb:FormBuilder,
    private bannerService:BannerService,
    private route:ActivatedRoute,
    private router:Router
  ){}



  ngOnInit():void{


    this.initForm();


    this.bannerId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    console.log('Banner Id =========== ', this.bannerId);


    if(this.bannerId){

      this.isEditMode=true;

      this.loadBanner();

    }


  }





  initForm(){


    this.bannerForm=this.fb.group({


      title:[
        '',
        Validators.required
      ],


      subtitle:[
        ''
      ],


      buttonText:[
        '',
        Validators.required
      ],


      buttonLink:[
        ''
      ],


      displayOrder:[
        0
      ],


      active:[
        true
      ]

    });


  }






  loadBanner(){


    this.bannerService
    .getBanner(this.bannerId)
    .subscribe({


      next:(banner:BannerResponse)=>{

         console.log('Banner Response==============', banner);

        this.bannerForm.patchValue({

          title:banner.title,

          subtitle:banner.subtitle,

          buttonText:banner.buttonText,

          buttonLink:banner.buttonLink,

          displayOrder:banner.displayOrder,

          active:banner.active

        });


        this.imagePreview =
        this.baseImageUrl + banner.imageUrl;


      },


      error:(err)=>{

        console.error(err);

      }


    });


  }






  onFileSelected(event:any){


    const file =
    event.target.files[0];


    if(file){


      this.selectedImage=file;


      const reader =
      new FileReader();


      reader.onload=()=>{

        this.imagePreview =
        reader.result as string;

      };


      reader.readAsDataURL(file);


    }


  }







  submit(){


    if(this.bannerForm.invalid){

      this.bannerForm.markAllAsTouched();

      return;

    }



    const formData =
    new FormData();



    Object.keys(
      this.bannerForm.value
    )
    .forEach(key=>{


      formData.append(

        key,

        this.bannerForm.value[key]

      );


    });





    if(this.selectedImage){

      formData.append(
        'image',
        this.selectedImage
      );

    }




    this.loading=true;



    if(this.isEditMode){


      this.bannerService
      .updateBanner(
        this.bannerId,
        formData
      )
      .subscribe({

        next:()=>{

          this.loading=false;

          this.router.navigate(
            ['/admin/banner-management/banner']
          );

        },


        error:(err)=>{

          console.error(err);

          this.loading=false;

        }

      });



    }

    else{


      this.bannerService
      .createBanner(formData)
      .subscribe({

        next:()=>{

          this.loading=false;

          this.router.navigate(
            ['/admin/banner-management/banner']
          );

        },


        error:(err)=>{

          console.error(err);

          this.loading=false;

        }

      });



    }



  }





  cancel(){


    this.router.navigate(
      ['/admin/banner-management/banner']
    );


  }



}