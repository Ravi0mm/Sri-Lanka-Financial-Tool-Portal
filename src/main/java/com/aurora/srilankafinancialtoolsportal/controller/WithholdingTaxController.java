package com.aurora.srilankafinancialtoolsportal.controller;

import com.aurora.srilankafinancialtoolsportal.model.withholding.WithholdingTaxRequest;
import com.aurora.srilankafinancialtoolsportal.model.withholding.WithholdingTaxCategory;
import com.aurora.srilankafinancialtoolsportal.service.WithholdingTaxService;
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
@RequestMapping("/withholding-tax-calculator-sri-lanka")
public class WithholdingTaxController {

    private final WithholdingTaxService withholdingTaxService;
    private final SeoUtil seoUtil;

    public WithholdingTaxController(WithholdingTaxService withholdingTaxService, SeoUtil seoUtil) {
        this.withholdingTaxService = withholdingTaxService;
        this.seoUtil = seoUtil;
    }

    @GetMapping
    public String showCalculator(Model model) {
        if (!model.containsAttribute("withholdingTaxRequest")) {
            model.addAttribute("withholdingTaxRequest", new WithholdingTaxRequest());
        }
        addPageMetadata(model);
        addCategoryOptions(model);
        model.addAttribute("activePage", "withholding-tax");
        return "withholding-tax";
    }

    @PostMapping
    public String calculate(
            @Valid @ModelAttribute("withholdingTaxRequest") WithholdingTaxRequest withholdingTaxRequest,
            BindingResult bindingResult,
            Model model) {

        addPageMetadata(model);
        addCategoryOptions(model);
        model.addAttribute("activePage", "withholding-tax");

        WithholdingTaxCategory category = WithholdingTaxCategory.fromCode(withholdingTaxRequest.getPresetCategory())
                .orElse(WithholdingTaxCategory.CUSTOM);

        if (!category.isRequiresCustomRate() && category.getRate() != null) {
            withholdingTaxRequest.setWithholdingRate(category.getRate());
        }

        if (category.isRequiresCustomRate() || category.getRate() == null) {
            if (withholdingTaxRequest.getWithholdingRate() == null) {
                bindingResult.rejectValue("withholdingRate", "NotNull", "Withholding rate is required");
            }
        }

        if (bindingResult.hasErrors()) {
            return "withholding-tax";
        }

        model.addAttribute("result", withholdingTaxService.calculate(withholdingTaxRequest));
        return "withholding-tax";
    }

    private void addPageMetadata(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka Withholding Tax Calculator | WHT Rate & Net Payment",
                "Calculate Sri Lanka withholding tax (WHT) by entering the gross payment amount and applicable rate to get tax withheld and net payment.",
                "/withholding-tax-calculator-sri-lanka"
        );
    }

    private void addCategoryOptions(Model model) {
        model.addAttribute("whtCategories", WithholdingTaxCategory.values());
    }
}
