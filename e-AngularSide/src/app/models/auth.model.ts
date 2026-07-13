


export interface LoginResponse {
  jwtToken: string;
  user: {
    userName: string;
    userFirstName?: string;
    userLastName?: string;
    email?: string;
    roles: { roleName: string }[];
  };
}



