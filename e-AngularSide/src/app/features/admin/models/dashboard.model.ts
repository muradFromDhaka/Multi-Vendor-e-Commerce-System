export interface AdminDashboard {

  // ==============================
  // Revenue Analytics
  // ==============================

  totalGrossRevenue: number;
  todayGrossRevenue: number;
  monthlyGrossRevenue: number;
  yearlyGrossRevenue: number;

  totalNetRevenue: number;
  todayNetRevenue: number;
  monthlyNetRevenue: number;
  yearlyNetRevenue: number;

  averagePaymentAmount: number;
  highestPaymentAmount: number;
  lowestPaymentAmount: number;

  // ==============================
  // Sales Analytics
  // ==============================

  todaySalesAmount: number;
  monthlySalesAmount: number;
  yearlySalesAmount: number;

  // ==============================
  // Refund Analytics
  // ==============================

  totalRefundAmount: number;
  todayRefundAmount: number;
  monthlyRefundAmount: number;
  yearlyRefundAmount: number;

  averageRefundAmount: number;
  highestRefundAmount: number;
  lowestRefundAmount: number;

  // ==============================
  // Orders Analytics
  // ==============================

  totalOrders: number;
  pendingOrders: number;
  processingOrders: number;
  shippedOrders: number;
  deliveredOrders: number;
  cancelledOrders: number;
  returnedOrders: number;
  paidOrders: number;
  todayOrders: number;

  // ==============================
  // Payment Analytics
  // ==============================

  pendingPayments: number;
  paidPayments: number;
  failedPayments: number;
  refundedPayments: number;
  cancelledPayments: number;
  totalPayments: number;

  // ==============================
  // Payment Method Analytics
  // ==============================

  cardRevenue: number;
  cashOnDeliveryRevenue: number;
  mobileBankingRevenue: number;
  bankTransferRevenue: number;

  cardPayments: number;
  cashOnDeliveryPayments: number;
  mobileBankingPayments: number;
  bankTransferPayments: number;

  averageCardPayment: number;
  averageCashOnDeliveryPayment: number;
  averageMobileBankingPayment: number;
  averageBankTransferPayment: number;

  // ==============================
  // Payment Provider Analytics
  // ==============================

  manualRevenue: number;
  bkashRevenue: number;
  nagadRevenue: number;
  rocketRevenue: number;
  sslCommerzRevenue: number;
  stripeRevenue: number;

  // ==============================
  // Finance Analytics
  // ==============================

  pendingPaymentAmount: number;
  failedPaymentAmount: number;
  cancelledPaymentAmount: number;

  // ==============================
  // Customer Analytics
  // ==============================

  totalCustomers: number;
  todayCustomers: number;
  activeCustomers: number;
  repeatCustomers: number;

  // ==============================
  // Vendor Analytics
  // ==============================

  totalVendors: number;
  activeVendors: number;
  pendingVendors: number;
  approvedVendors: number;
  rejectVendors: number;
  suspendedVendors: number;
  todayVendors: number;

  topVendorName: string;
  topVendorRevenue: number;

  // ==============================
  // Catalog Analytics
  // ==============================

  totalProducts: number;
  totalProductVariants: number;
  totalCategories: number;
  totalBrands: number;

  // ==============================
  // Product Analytics
  // ==============================

  bestSellingProduct: string;
  bestSellingProductQuantitySold: number;
  bestSellingProductRevenue: number;

  bestSellingCategory: string;
  bestSellingCategoryQuantitySold: number;
  bestSellingCategoryRevenue: number;

  // ==============================
  // Inventory Analytics
  // ==============================

  outOfStockProducts: number;
  totalStockQuantity: number;
  lowStockProducts: number;

  // ==============================
  // Engagement Analytics
  // ==============================

  totalCarts: number;
  totalReviews: number;
  totalActiveCarts: number;
  averageRating: number;
  totalWishlistItems: number;
}