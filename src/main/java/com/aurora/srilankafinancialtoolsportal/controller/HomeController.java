package com.aurora.srilankafinancialtoolsportal.controller;

import com.aurora.srilankafinancialtoolsportal.util.SeoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final SeoUtil seoUtil;

    public HomeController(SeoUtil seoUtil) {
        this.seoUtil = seoUtil;
    }

    @GetMapping("/")
    public String home(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka Financial Tools Portal | Tax, WHT, EPF, Vehicle Duty & Home Loan Calculators",
                "Use free Sri Lanka financial calculators for salary tax 2026, withholding tax, vehicle import duty, EPF maturity, and home loan EMI.",
                "/"
        );
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka Finance Blog | Tax and Loan Guides",
                "Read practical Sri Lanka finance guides on PAYE tax 2026, withholding tax, EPF planning, vehicle duty, and home loan strategy.",
                "/blog"
        );
        model.addAttribute("activePage", "blog");
        return "blog";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        seoUtil.addMeta(
                model,
                "Contact | Sri Lanka Financial Tools Portal",
                "Contact the Sri Lanka Financial Tools Portal team for feedback, calculator improvements, or partnerships.",
                "/contact"
        );
        model.addAttribute("activePage", "contact");
        return "contact";
    }

    @GetMapping("/privacy-policy")
    public String privacyPolicy(Model model) {
        seoUtil.addMeta(
                model,
                "Privacy Policy | Sri Lanka Financial Tools Portal",
                "Read the privacy policy for Sri Lanka Financial Tools Portal including analytics and ad personalization disclosures.",
                "/privacy-policy"
        );
        model.addAttribute("activePage", "privacy");
        return "privacy-policy";
    }
}
