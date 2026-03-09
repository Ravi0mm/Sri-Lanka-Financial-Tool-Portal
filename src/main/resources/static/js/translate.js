(() => {
    const SOURCE_LANGUAGE = "en";
    const TARGET_LANGUAGES = new Set(["en", "si"]);
    const COOKIE_KEY = "googtrans";
    const SELECTOR = ".portal-language-switcher .portal-lang-btn";
    const HIDDEN_TRANSLATE_UI = [
        "#goog-gt-tt",
        ".goog-tooltip",
        ".goog-tooltip:hover",
        ".goog-text-highlight"
    ];
    const BANNER_MIN_HEIGHT = 40;

    function getCookie(name) {
        const match = document.cookie.match(new RegExp(`(?:^|;\\s*)${name}=([^;]+)`));
        return match ? decodeURIComponent(match[1]) : "";
    }

    function setTranslateCookie(lang) {
        const cookieValue = `/${SOURCE_LANGUAGE}/${lang}`;
        const expiration = new Date();
        expiration.setFullYear(expiration.getFullYear() + 1);

        const cookie = `${COOKIE_KEY}=${cookieValue};expires=${expiration.toUTCString()};path=/`;
        document.cookie = cookie;

        const host = window.location.hostname.replace(/^www\./, "");
        if (host.includes(".")) {
            document.cookie = `${cookie};domain=.${host}`;
        }
    }

    function currentLanguage() {
        const value = getCookie(COOKIE_KEY);
        if (!value) {
            return SOURCE_LANGUAGE;
        }

        const segments = value.split("/");
        const lang = segments[segments.length - 1];
        return TARGET_LANGUAGES.has(lang) ? lang : SOURCE_LANGUAGE;
    }

    function updateButtons(activeLanguage) {
        document.querySelectorAll(SELECTOR).forEach((button) => {
            const isActive = button.dataset.lang === activeLanguage;
            button.classList.toggle("active", isActive);
            button.setAttribute("aria-pressed", String(isActive));
        });
    }

    function applyWidgetLanguage(language) {
        const combo = document.querySelector(".goog-te-combo");
        if (!combo) {
            return false;
        }

        if (combo.value !== language) {
            combo.value = language;
            combo.dispatchEvent(new Event("change"));
        }

        return true;
    }

    function findTranslateBannerFrame() {
        const frames = document.querySelectorAll("iframe");
        for (const frame of frames) {
            const className = frame.className || "";
            const src = (frame.getAttribute("src") || "").toLowerCase();
            if (className.includes("goog-te-banner-frame") || className.includes("VIpgJd-ZVi9od-ORHb")) {
                return frame;
            }

            if ((src.includes("translate.google.com") || src.includes("translate.googleapis.com")) && frame.getBoundingClientRect().top <= 2) {
                return frame;
            }
        }
        return null;
    }

    function updateNavbarOffset() {
        const bodyTop = parseFloat(window.getComputedStyle(document.body).top) || 0;
        const banner = findTranslateBannerFrame();

        if (!banner || bodyTop > 0) {
            document.documentElement.style.setProperty("--translate-banner-height", "0px");
            document.body.classList.remove("translate-banner-visible");
            return;
        }

        const rect = banner.getBoundingClientRect();
        const computedHeight = parseFloat(window.getComputedStyle(banner).height) || 0;
        const bannerHeight = Math.max(rect.height, computedHeight, BANNER_MIN_HEIGHT);
        document.documentElement.style.setProperty("--translate-banner-height", `${Math.round(bannerHeight)}px`);
        document.body.classList.add("translate-banner-visible");
    }

    function hideTranslateUiArtifacts() {
        document.querySelectorAll(HIDDEN_TRANSLATE_UI.join(",")).forEach((node) => {
            node.style.setProperty("display", "none", "important");
            node.style.setProperty("visibility", "hidden", "important");
            node.style.setProperty("height", "0", "important");
        });
    }

    function startUiCleanup() {
        hideTranslateUiArtifacts();
        updateNavbarOffset();
        let runs = 0;
        const timer = window.setInterval(() => {
            hideTranslateUiArtifacts();
            updateNavbarOffset();
            runs += 1;
            if (runs > 80) {
                window.clearInterval(timer);
            }
        }, 200);
        window.addEventListener("resize", updateNavbarOffset);
    }

    function setLanguage(language) {
        if (!TARGET_LANGUAGES.has(language)) {
            return;
        }

        const current = currentLanguage();
        updateButtons(language);

        if (current === language) {
            applyWidgetLanguage(language);
            return;
        }

        setTranslateCookie(language);

        if (!applyWidgetLanguage(language)) {
            window.location.reload();
        }
    }

    function bindLanguageButtons() {
        updateButtons(currentLanguage());

        document.querySelectorAll(SELECTOR).forEach((button) => {
            button.addEventListener("click", () => setLanguage(button.dataset.lang));
        });
    }

    window.googleTranslateElementInit = function googleTranslateElementInit() {
        if (!window.google || !google.translate || !google.translate.TranslateElement) {
            return;
        }

        new google.translate.TranslateElement(
            {
                pageLanguage: SOURCE_LANGUAGE,
                includedLanguages: "en,si",
                autoDisplay: false
            },
            "google_translate_element"
        );

        applyWidgetLanguage(currentLanguage());
        hideTranslateUiArtifacts();
        updateNavbarOffset();
    };

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", () => {
            bindLanguageButtons();
            startUiCleanup();
        });
    } else {
        bindLanguageButtons();
        startUiCleanup();
    }
})();
