import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserResponse } from 'src/app/models/customer.model';
import { OrderResponse } from 'src/app/models/order.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-customer-navbar',
  templateUrl: './customer-navbar.component.html',
  styleUrls: ['./customer-navbar.component.scss']
})
export class CustomerNavbarComponent {

  cartItemCount = 0;
    currentUser$!: UserResponse | null;
    orders: OrderResponse[] = [];
  
     private subscriptions = new Subscription();
  
  
     constructor(
      public authService: AuthService,
      private router: Router,
      private orderService:OrderService,
      private cartService: CartService
    ){}
   
  
      ngOnInit() {
  
         this.subscriptions.add(
        this.authService.user$.subscribe(user => {
          this.currentUser$ = user;
  
          // Load cart after login
          if (user) {
            this.cartService.loadCart();
          } else {
            this.cartItemCount = 0;
          }
        })
      );
  
        /* Cart state */
      this.subscriptions.add(
        this.cartService.cart$.subscribe(cart => {
          // this.cartItemCount = cart.items.reduce(
          //   (sum, item) => sum + item.quantity,
          //   0
          // );
          this.cartItemCount = cart.items.length;
        })
      );
  
      this.searchControl.valueChanges
        .subscribe(value => {
          this.showSuggestions = !!value && value.length > 0;
        });
  
      this.loadCartFromStorage();
  
       if (this.authService.isLoggedIn()) {
       this.loadOrders();
       }
      
    }
  
      loadOrders(): void {
      this.orderService.getMyOrders().subscribe({
        next: res => {
          this.orders = res.content;
          // this.loading = false;
        },
        error: err => {
          console.error('Failed to load orders', err);
          // this.loading = false;
        }
      });
    }
  
    get totalOrders(): number {
    return this.orders.length;
  }
  
  
  
  
    logOut(){
      this.authService.logOut();
      this.router.navigate(['/home'])
    }
  
       @Output() toggleSidebar = new EventEmitter<boolean>();
      
      searchQuery: string = '';
      notificationCount: number = 3;
      pendingOrders!: number;
      isSidebarOpen: boolean = true;
      
      
  
  
  onSearch() {
    if (!this.searchQuery.trim()) return;
  
    // Navigate to CategoryProductsComponent with query param
    this.router.navigate(['/categoryProduct'], {
      queryParams: { query: this.searchQuery.trim() }
    });
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
      dropdowns = {
        language: false,
        notifications: false,
        help: false,
        user: false,
        more: false
      };
      
      notifications = [
        {
          icon: '📦',
          title: 'New Order Received',
          description: 'Order #ORD-2024-00123 has been placed',
          time: '5 minutes ago',
          type: 'order'
        },
        {
          icon: '⚠️',
          title: 'Low Stock Alert',
          description: 'Product "Wireless Headphones" is running low',
          time: '1 hour ago',
          type: 'alert'
        },
        {
          icon: '💰',
          title: 'Payment Processed',
          description: 'Payment of $1,249.99 has been processed',
          time: '2 hours ago',
          type: 'payment'
        },
        {
          icon: '🚚',
          title: 'Order Shipped',
          description: 'Order #ORD-2024-00122 has been shipped',
          time: 'Yesterday',
          type: 'shipping'
        }
      ];
    
      
      toggleDropdown(dropdownName: keyof typeof this.dropdowns) {
        // Close all other dropdowns first
        Object.keys(this.dropdowns).forEach(key => {
          if (key !== dropdownName) {
            this.dropdowns[key as keyof typeof this.dropdowns] = false;
          }
        });
        
        // Toggle the clicked dropdown
        this.dropdowns[dropdownName] = !this.dropdowns[dropdownName];
      }
    
      onToggleSidebar() {
        this.isSidebarOpen = !this.isSidebarOpen;
        this.toggleSidebar.emit(this.isSidebarOpen);
      }
    
      markAllAsRead() {
        this.notificationCount = 0;
        this.dropdowns.notifications = false;
      }
    
      createNew() {
        console.log('Create new clicked');
        // Implement create new functionality
      }
    
      quickActions() {
        console.log('Quick actions clicked');
        // Implement quick actions
      }
  
  
  
  
  
  
  
  
       // User info
    isLoggedIn = true;
    userName = 'John';
    userLocation = 'Deliver to John<br>New York 10001';
    
    // Language & Currency
    languages = [
      { code: 'en', name: 'English', flag: '🇺🇸' },
      { code: 'es', name: 'Español - ES', flag: '🇪🇸' },
      { code: 'ar', name: 'العربية', flag: '🇸🇦' }
    ];
    
    currencies = [
      { code: 'USD', name: 'USD - U.S. Dollar', symbol: '$' },
      { code: 'EUR', name: 'EUR - Euro', symbol: '€' },
      { code: 'GBP', name: 'GBP - British Pound', symbol: '£' }
    ];
    
    selectedLanguage = this.languages[0];
    selectedCurrency = this.currencies[0];
    
    // Search
    searchControl = new FormControl('');
    searchSuggestions = [
      { text: 'Electronics', icon: '📱' },
      { text: 'Computers & Accessories', icon: '💻' },
      { text: 'Home & Kitchen', icon: '🏠' },
      { text: 'Books', icon: '📚' },
      { text: 'Clothing & Fashion', icon: '👕' },
      { text: 'Beauty & Personal Care', icon: '💄' },
      { text: 'Sports & Outdoors', icon: '⚽' },
      { text: 'Toys & Games', icon: '🎮' }
    ];
    showSuggestions = false;
    selectedCategory = 'All';
    categories = [];
    
    // Cart
    cartItems = [
      { id: 1, name: 'Echo Dot (4th Gen)', price: 49.99, quantity: 1 },
      { id: 2, name: 'Amazon Basics HDMI Cable', price: 12.99, quantity: 2 },
      { id: 3, name: 'Kindle Paperwhite', price: 139.99, quantity: 1 }
    ];
    
    // Orders
    recentOrders = [
      { id: 1, name: 'Apple AirPods Pro', date: 'Dec 15, 2023', status: 'Delivered' },
      { id: 2, name: 'Samsung 4K TV', date: 'Dec 10, 2023', status: 'Delivered' },
      { id: 3, name: 'Instant Pot', date: 'Dec 5, 2023', status: 'Delivered' }
    ];
  
  
    // State management
    showAccountDropdown = false;
    showLanguageDropdown = false;
    showCartDropdown = false;
    showOrdersDropdown = false;
    showServicesDropdown = false;
    showCategoriesDropdown = false;
    isMobileMenuOpen = false;
    scrolled = false;
  
  
    
   
    // Language methods
    selectLanguage(lang: any) {
      this.selectedLanguage = lang;
      this.showLanguageDropdown = false;
    }
    
    // Category methods
    selectCategory(category: string) {
      this.selectedCategory = category;
      this.showCategoriesDropdown = false;
    }
    
   
    
    selectSuggestion(suggestion: any) {
      this.searchControl.setValue(suggestion.text);
      this.showSuggestions = false;
      this.onSearch();
    }
    
  
    loadCartFromStorage() {
      // Load cart from localStorage
      const savedCart = localStorage.getItem('amazon_cart');
      if (savedCart) {
        this.cartItems = JSON.parse(savedCart);
      }
    }
    
    saveCartToStorage() {
      localStorage.setItem('amazon_cart', JSON.stringify(this.cartItems));
    }
    
    get cartTotal() {
      return this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
    }
    
    get cartCount() {
      return this.cartItems.reduce((count, item) => count + item.quantity, 0);
    }
    
    addToCart(product: any) {
      const existingItem = this.cartItems.find(item => item.id === product.id);
      if (existingItem) {
        existingItem.quantity++;
      } else {
        this.cartItems.push({ ...product, quantity: 1 });
      }
      this.saveCartToStorage();
    }
    
    removeFromCart(item: any) {
      this.cartItems = this.cartItems.filter(cartItem => cartItem.id !== item.id);
      this.saveCartToStorage();
    }
  
  
    
    // Close methods
    closeAllDropdowns() {
      this.showAccountDropdown = false;
      this.showLanguageDropdown = false;
      this.showCartDropdown = false;
      this.showOrdersDropdown = false;
      this.showServicesDropdown = false;
      this.showCategoriesDropdown = false;
      this.showSuggestions = false;
    }
    
    closeOtherDropdowns(current: string) {
      const dropdowns = {
        account: this.showAccountDropdown,
        language: this.showLanguageDropdown,
        cart: this.showCartDropdown,
        orders: this.showOrdersDropdown,
        services: this.showServicesDropdown,
        categories: this.showCategoriesDropdown
      };
    }
  
  
}
