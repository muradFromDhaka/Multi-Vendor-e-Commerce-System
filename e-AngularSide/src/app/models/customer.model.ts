import { Role } from "./admin.model";

export interface UserResponse {
  userName: string;
  userFirstName?: string;
  userLastName?: string;
  vendorId?: number; 
  email?: string;
  enabled?: boolean;
  roles?: Role[];
}

export interface UserRequest {
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
}


export interface CustomerStatistics {
  totalCustomers: number;
  activeCustomers: number;
  disabledCustomers: number;
}