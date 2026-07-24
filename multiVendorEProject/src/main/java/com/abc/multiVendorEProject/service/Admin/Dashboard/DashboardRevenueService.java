//package com.abc.multiVendorEProject.service.Admin.Dashboard;
//
//import com.abc.multiVendorEProject.DTOs.projectDtos.AdminDashboard.AdminDashboardResponseDto;
//import com.abc.multiVendorEProject.repository.PaymentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class DashboardRevenueService {
//
//    private final PaymentRepository paymentRepository;
//
//    public void populate(AdminDashboardResponseDto dto) {
//
//        dto.setTotalGrossRevenue(paymentRepository.getTotalGrossRevenue());
//        dto.setTodayGrossRevenue(paymentRepository.getTodayGrossRevenue());
//        dto.setMonthlyGrossRevenue(paymentRepository.getMonthlyGrossRevenue());
//        dto.setYearlyGrossRevenue(paymentRepository.getYearlyGrossRevenue());
//
//        dto.setTotalNetRevenue(paymentRepository.getTotalNetRevenue());
//        dto.setTodayNetRevenue(paymentRepository.getTodayNetRevenue());
//        dto.setMonthlyNetRevenue(paymentRepository.getMonthlyNetRevenue());
//        dto.setYearlyNetRevenue(paymentRepository.getYearlyNetRevenue());
//
//    }
//
//}
