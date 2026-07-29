export interface BannerResponse {

  id:number;

  title:string;

  subtitle:string;

  buttonText:string;

  buttonLink:string;

  imageUrl:string;

  displayOrder:number;

  active:boolean;

}



export interface BannerRequest {


  title:string;

  subtitle:string;

  buttonText:string;

  buttonLink:string;

  displayOrder:number;

  active:boolean;


}