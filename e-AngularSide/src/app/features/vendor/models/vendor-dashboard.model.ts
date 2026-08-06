export interface VendorDashboard {

  // Revenue
  totalRevenue: number;
  monthlyRevenue: number;
  todayRevenue: number;

  // Orders
  totalOrders: number;
  pendingOrders: number;
  processingOrders: number;
  shippedOrders: number;
  deliveredOrders: number;
  cancelledOrders: number;

  // Products
  totalProducts: number;
  lowStockProducts: number;
  outOfStockProducts: number;

  // Customers
  totalCustomers: number;

  // Reviews
  totalReviews: number;
  averageRating: number;

  recentOrders: RecentVendorOrder[];
  topSellingProducts: TopSellingProduct[];
  lowStockProductsList: LowStockProduct[];
  latestReviews: LatestReview[];

}






// ================================================

export interface LatestReview {

  customerName: string;

  productName: string;

  rating: number;

  comment: string;

  reviewDate: string;

}


export interface RecentVendorOrder {

  vendorOrderId: number;

  orderNumber: string;

  customerName: string;

  totalAmount: number;

  status: string;

  orderDate: string;

}


export interface TopSellingProduct {

  productId: number;

  productName: string;

  imageUrl: string;

  soldQuantity: number;

  revenue: number;

}


export interface LowStockProduct {

  variantId: number;

  productName: string;

  sku: string;

  stock: number;

}