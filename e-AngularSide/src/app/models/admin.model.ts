export interface Role {
  roleName: string;
  roleDescription?: string;
  dateCreated?: string;
  lastUpdated?: string;
}

export interface RoleCreateRequest {
  roleName: string;
  roleDescription?: string;
}

export interface RoleUpdateRequest {
  roles: string[];
}
