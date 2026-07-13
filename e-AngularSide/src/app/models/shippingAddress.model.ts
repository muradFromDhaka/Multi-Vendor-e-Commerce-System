export interface ShippingAddressRequest {

  customerName: string;

  phone: string;

  street: string;

  city: string;

  district: string;

  country: string;

  zipCode: string;

}

export interface ShippingAddressResponse {

  id: number;

  customerName: string;

  phone: string;

  street: string;

  city: string;

  district: string;

  country: string;

  zipCode: string;

}