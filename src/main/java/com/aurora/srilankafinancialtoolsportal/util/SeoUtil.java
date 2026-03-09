package com.aurora.srilankafinancialtoolsportal.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class SeoUtil {

    private static final String SITE_NAME = "Sri Lanka Financial Tools Portal";
    private static final String DEFAULT_ROBOTS = "index,follow,max-image-preview:large,max-snippet:-1,max-video-preview:-1";
    private static final String DEFAULT_OG_TYPE = "website";
    private static final String DEFAULT_LANGUAGE = "en-LK";
    private static final String DEFAULT_OG_LOCALE = "en_LK";
    private static final String CONTACT_EMAIL = "hello@srilankafinancialtools.com";

    private final String baseUrl;

    public SeoUtil(@Value("${app.base-url}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void addMeta(Model model, String title, String description, String path) {
        String normalizedBase = normalizeBaseUrl();
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        String canonicalUrl = normalizedBase + normalizedPath;

        model.addAttribute("siteName", SITE_NAME);
        model.addAttribute("siteBaseUrl", normalizedBase);
        model.addAttribute("contactEmail", CONTACT_EMAIL);
        model.addAttribute("pageTitle", title);
        model.addAttribute("metaDescription", description);
        model.addAttribute("canonicalUrl", canonicalUrl);
        model.addAttribute("robotsContent", DEFAULT_ROBOTS);
        model.addAttribute("pageLanguage", DEFAULT_LANGUAGE);
        model.addAttribute("ogTitle", title);
        model.addAttribute("ogDescription", description);
        model.addAttribute("ogUrl", canonicalUrl);
        model.addAttribute("ogType", DEFAULT_OG_TYPE);
        model.addAttribute("ogLocale", DEFAULT_OG_LOCALE);
    }

    private String normalizeBaseUrl() {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }
}
