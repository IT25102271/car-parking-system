package com.se1020.carparking.controller;

import com.se1020.carparking.model.User;
import com.se1020.carparking.service.BillService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BillController {

    @Autowired
    private BillService billService;

    // READ - User views own bills
    @GetMapping("/user/bills")
    public String myBills(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        model.addAttribute("bills", billService.getBillsByUser(user.getUserId()));
        return "bill/my-bills";
    }

    // READ - Admin views all bills
    @GetMapping("/admin/bills")
    public String allBills(Model model) {
        model.addAttribute("bills", billService.getAllBills());
        return "bill/admin-bill-list";
    }

    // CREATE - Generate bill from reservation
    @PostMapping("/admin/bills/generate")
    public String generateBill(@RequestParam String reservationId) {
        billService.generateBill(reservationId);
        return "redirect:/admin/bills";
    }

    // READ - View single bill detail
    @GetMapping("/user/bills/{billId}")
    public String viewBill(@PathVariable String billId, Model model) {
        model.addAttribute("bill", billService.getBillById(billId));
        return "bill/bill-detail";
    }

    // UPDATE - User pays bill
    @PostMapping("/user/bills/pay/{billId}")
    public String payBill(@PathVariable String billId) {
        billService.payBill(billId);
        return "redirect:/user/bills";
    }

    // UPDATE - Admin applies discount
    @GetMapping("/admin/bills/discount/{billId}")
    public String discountPage(@PathVariable String billId, Model model) {
        model.addAttribute("bill", billService.getBillById(billId));
        return "bill/apply-discount";
    }

    @PostMapping("/admin/bills/discount")
    public String applyDiscount(@RequestParam String billId,
                                @RequestParam double discount) {
        billService.applyDiscount(billId, discount);
        return "redirect:/admin/bills";
    }

    // DELETE - Admin removes erroneous bill
    @PostMapping("/admin/bills/delete/{billId}")
    public String deleteBill(@PathVariable String billId) {
        billService.deleteBill(billId);
        return "redirect:/admin/bills";
    }
}