import { Component } from '@angular/core';
import { Role, RoleCreateRequest } from 'src/app/models/admin.model';
import { UserResponse } from 'src/app/models/customer.model';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-role-management',
  templateUrl: './role-management.component.html',
  styleUrls: ['./role-management.component.scss']
})
export class RoleManagementComponent {

  
  roles: Role[] = [];
  usersByRole: UserResponse[] = [];
  selectedRoleName: string = '';
  newRoleName: string = '';
  newRoleDescription: string = '';

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.loadRoles();
  }

  /** Load all roles */
  loadRoles() {
    this.adminService.getAllRoles().subscribe(res => {
      this.roles = res;
    });
  }

  /** Create new role */
  createRole() {
    if (!this.newRoleName) {
      alert('Role name is required');
      return;
    }
    const roleRequest: RoleCreateRequest = {
      roleName: this.newRoleName,
      roleDescription: this.newRoleDescription
    };
    this.adminService.createRole(roleRequest).subscribe(res => {
      console.log('Role created', res);
      this.newRoleName = '';
      this.newRoleDescription = '';
      this.loadRoles(); // refresh roles list
    }, err => {
      console.error('Error creating role', err);
      alert('Failed to create role. Maybe it already exists.');
    });
  }

  /** Load users by selected role */
  loadUsersByRole(roleName: string) {
    this.selectedRoleName = roleName;
    this.adminService.getUsersByRole(roleName).subscribe(res => {
      this.usersByRole = res;
    });
  }

  /** Assign roles to a user via prompt */
  assignRolesToUser(user: UserResponse) {
    const currentRoles = user.roles?.join(', ') || '';
    const rolesInput = prompt(`Enter roles for ${user.userName} (comma separated)`, currentRoles);
    if (!rolesInput) return;
    const rolesArray = rolesInput.split(',').map(r => r.trim()).filter(r => r);
    this.adminService.updateUserRoles(user.userName, rolesArray).subscribe(res => {
      console.log('Updated user roles', res);
      // Refresh users for the selected role
      this.loadUsersByRole(this.selectedRoleName);
    });
  }
}
