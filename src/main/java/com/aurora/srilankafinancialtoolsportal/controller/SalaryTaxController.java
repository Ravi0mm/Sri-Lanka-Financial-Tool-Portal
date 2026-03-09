package com.aurora.srilankafinancialtoolsportal.controller;

import com.aurora.srilankafinancialtoolsportal.model.salary.SalaryTaxRequest;
import com.aurora.srilankafinancialtoolsportal.service.SalaryTaxService;
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
@RequestMapping("/salary-tax-calculator-sri-lanka-2026")
public class SalaryTaxController {

    private final SalaryTaxService salaryTaxService;
    private final SeoUtil seoUtil;

    public SalaryTaxController(SalaryTaxService salaryTaxService, SeoUtil seoUtil) {
        this.salaryTaxService = salaryTaxService;
        this.seoUtil = seoUtil;
    }

    @GetMapping
    public String showCalculator(Model model) {
        if (!model.containsAttribute("salaryTaxRequest")) {
            model.addAttribute("salaryTaxRequest", new SalaryTaxRequest());
        }
        addPageMetadata(model);
        model.addAttribute("activePage", "salary-tax");
        return "salary-tax";
    }

    @PostMapping
    public String calculate(
            @Valid @ModelAttribute("salaryTaxRequest") SalaryTaxRequest salaryTaxRequest,
            BindingResult bindingResult,
            Model model) {

        addPageMetadata(model);
        model.addAttribute("activePage", "salary-tax");

        if (bindingResult.hasErrors()) {
            return "salary-tax";
        }

        model.addAttribute("result", salaryTaxService.calculate(salaryTaxRequest));
        return "salary-tax";
    }

    private void addPageMetadata(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka Salary Tax Calculator 2026 | PAYE Estimator",
                "Calculate Sri Lanka salary tax for 2026 with annual bonus and allowances using progressive slab rates and monthly deductions.",
                "/salary-tax-calculator-sri-lanka-2026"
        );
    }
}
