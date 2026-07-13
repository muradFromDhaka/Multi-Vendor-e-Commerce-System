
export enum VendorStatus{

  PENDING='PENDING',
  ACTIVE='ACTIVE',
  APPROVED='APPROVED',
  REJECTED='REJECTED',
  SUSPENDED='SUSPENDED'

}



export interface VendorRequest {
  shopName: string;
  phone: string;
  address: string;
  businessEmail: string;
  description?: string;
  logoUrl?: string;
  bannerUrl: string;
}

export interface VendorResponse {
  id: number;
  shopName: string;
  slug: string;
  phone: string;
  address: string;
  status: VendorStatus;
  rating: number;
  userName: string;
  businessEmail: string;
  description?: string;
  logoUrl?: string;
  bannerUrl?: string;
}



export interface VendorSummary {

  all: number;
  pending: number;
  approved: number;
  active: number;
  suspended: number;
  rejected: number;

}

export interface VendorStats {

  totalProducts: number;
  totalOrders: number;
  totalRevenue: number;
  totalCustomers: number;
}