export interface ShippingAddressRequest {

  username: string;

  phone: string;

  street: string;

  city: string;

  district: string;

  country: string;

  zipCode: string;

}

export interface ShippingAddressResponse {

  id: number;

  username: string;

  phone: string;

  street: string;

  city: string;

  district: string;

  country: string;

  zipCode: string;

}