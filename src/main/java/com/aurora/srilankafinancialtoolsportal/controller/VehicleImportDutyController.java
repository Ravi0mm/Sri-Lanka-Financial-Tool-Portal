package com.aurora.srilankafinancialtoolsportal.controller;

import com.aurora.srilankafinancialtoolsportal.model.vehicle.VehicleDutyRequest;
import com.aurora.srilankafinancialtoolsportal.service.VehicleImportDutyService;
import com.aurora.srilankafinancialtoolsportal.util.SeoUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vehicle-import-duty-calculator-sri-lanka")
public class VehicleImportDutyController {

    private final VehicleImportDutyService vehicleImportDutyService;
    private final SeoUtil seoUtil;

    public VehicleImportDutyController(VehicleImportDutyService vehicleImportDutyService, SeoUtil seoUtil) {
        this.vehicleImportDutyService = vehicleImportDutyService;
        this.seoUtil = seoUtil;
    }

    @GetMapping
    public String showCalculator(Model model) {
        if (!model.containsAttribute("vehicleDutyRequest")) {
            model.addAttribute("vehicleDutyRequest", new VehicleDutyRequest());
        }
        addLookups(model);
        addPageMetadata(model);
        model.addAttribute("activePage", "vehicle-duty");
        return "vehicle-duty";
    }

    @PostMapping
    public String calculate(
            @Valid @ModelAttribute("vehicleDutyRequest") VehicleDutyRequest vehicleDutyRequest,
            BindingResult bindingResult,
            Model model) {

        addLookups(model);
        addPageMetadata(model);
        model.addAttribute("activePage", "vehicle-duty");

        if (bindingResult.hasErrors()) {
            return "vehicle-duty";
        }

        model.addAttribute("result", vehicleImportDutyService.calculate(vehicleDutyRequest));
        return "vehicle-duty";
    }

    private void addLookups(Model model) {
        model.addAttribute("fuelTypes", List.of("Petrol", "Diesel", "Hybrid", "Electric"));
        model.addAttribute("vehicleTypes", List.of("Car", "SUV", "Van"));
    }

    private void addPageMetadata(Model model) {
        seoUtil.addMeta(
                model,
                "Sri Lanka Vehicle Import Duty Calculator | CIF, Excise, VAT",
                "Estimate Sri Lanka vehicle import duty including customs duty, PAL, excise duty, VAT, and final landed cost.",
                "/vehicle-import-duty-calculator-sri-lanka"
        );
    }
}
