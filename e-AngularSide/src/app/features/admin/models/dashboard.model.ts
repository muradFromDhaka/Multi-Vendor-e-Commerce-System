export interface AdminDashboard {

  // Revenue
  totalRevenue: number;
  todayRevenue: number;
  monthlyRevenue: number;
  yearlyRevenue: number;

  // Orders
  totalOrders: number;
  pendingOrders: number;
  processingOrders: number;
  shippedOrders: number;
  deliveredOrders: number;
  cancelledOrders: number;
  returnedOrders: number;
  paidOrders: number;

  // Payment
  pendingPayments: number;
  paidPayments: number;
  failedPayments: number;
  refundedPayments: number;
  cancelledPayments: number;

  // Users
  totalCustomers: number;
  totalVendors: number;
  activeVendors: number;
  pendingVendors: number;
  approvedVendors: number;
  rejectVendors: number;
  suspendedVendors: number;

  // Catalog
  totalProducts: number;
  totalProductVariants: number;
  totalCategories: number;
  totalBrands: number;

  // Inventory
  outOfStockProducts: number;
  lowStockProducts: number;

  totalReviews: number;
  totalWishlistItems: number;
  totalCarts: number;

  averageOrderValue: number;
  highestOrderValue: number;

  todayOrders: number;
  todayCustomers: number;
  todayVendors: number;
}