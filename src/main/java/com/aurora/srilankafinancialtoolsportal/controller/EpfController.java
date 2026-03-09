package com.aurora.srilankafinancialtoolsportal.controller;

import com.aurora.srilankafinancialtoolsportal.model.epf.EpfRequest;
import com.aurora.srilankafinancialtoolsportal.service.EpfCalculatorService;
import com.aurora.srilankafinancialtoolsportal.util.SeoUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/epf-maturity-calculator-sri-lanka")
public class EpfController {

    private final EpfCalculatorService epfCalculatorService;
    private final SeoUtil seoUtil;

    public EpfController(EpfCalculatorService epfCalculatorService, SeoUtil seoUtil) {
        this.epfCalculatorService = epfCalculatorService;
        this.seoUtil = seoUtil;
    }

    @GetMapping
    public String showCalculator(Model model) {
        if (!model.containsAttribute("epfRequest")) {
            EpfRequest request = new EpfRequest();
            request.setAnnualInterestRate(BigDecimal.valueOf(8.0));
            model.addAttribute("epfRequest", request);
        }
        addPageMetadata(model);
        model.addAttribute("activePage", "epf");
        return "epf";
    }

    @PostMapping
    public String calculate(
            @Valid @ModelAttribute("epfRequest") EpfRequest epfRequest,
            BindingResult bindingResult,
            Model model) {

        addPageMetadata(model);
        model.addAttribute("activePage", "epf");

        if (bindingResult.hasErrors()) {
            return "epf";
        }

        model.addAttribute("result", epfCalculatorService.calculate(epfRequest));
        return "epf";
    }

    private void addPageMetadata(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka EPF Maturity Calculator | 8% + 12% Contributions",
                "Estimate EPF maturity in Sri Lanka using employee 8% and employer 12% contributions with annual compounding.",
                "/epf-maturity-calculator-sri-lanka"
        );
    }
}
