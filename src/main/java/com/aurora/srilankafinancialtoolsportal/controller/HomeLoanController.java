package com.aurora.srilankafinancialtoolsportal.controller;

import com.aurora.srilankafinancialtoolsportal.model.loan.HomeLoanRequest;
import com.aurora.srilankafinancialtoolsportal.service.HomeLoanService;
import com.aurora.srilankafinancialtoolsportal.util.SeoUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home-loan-calculator-sri-lanka")
public class HomeLoanController {

    private final HomeLoanService homeLoanService;
    private final SeoUtil seoUtil;

    public HomeLoanController(HomeLoanService homeLoanService, SeoUtil seoUtil) {
        this.homeLoanService = homeLoanService;
        this.seoUtil = seoUtil;
    }

    @GetMapping
    public String showCalculator(Model model) {
        if (!model.containsAttribute("homeLoanRequest")) {
            model.addAttribute("homeLoanRequest", new HomeLoanRequest());
        }
        addPageMetadata(model);
        model.addAttribute("activePage", "home-loan");
        return "home-loan";
    }

    @PostMapping
    public String calculate(
            @Valid @ModelAttribute("homeLoanRequest") HomeLoanRequest homeLoanRequest,
            BindingResult bindingResult,
            Model model) {

        addPageMetadata(model);
        model.addAttribute("activePage", "home-loan");

        if (bindingResult.hasErrors()) {
            return "home-loan";
        }

        model.addAttribute("result", homeLoanService.calculate(homeLoanRequest));
        return "home-loan";
    }

    private void addPageMetadata(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka Home Loan EMI Calculator | Mortgage Repayment Estimator",
                "Calculate Sri Lanka home loan EMI, total interest payable, and total repayment using current mortgage assumptions.",
                "/home-loan-calculator-sri-lanka"
        );
    }
}
