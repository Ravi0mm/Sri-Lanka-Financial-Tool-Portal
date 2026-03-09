# Sri Lanka Financial Tools Portal

Production-ready Spring Boot 3 + Thymeleaf web application with SEO-focused, server-side rendered financial calculators for Sri Lanka.

## Tech Stack

- Backend: Spring Boot 3 (Java 21)
- Frontend: Thymeleaf + Bootstrap 5
- Architecture: Layered (`controller`, `service`, `util`, `model`)
- Deployment target: Render.com
- Persistence: None (stateless calculators)

## Features

### 1) Sri Lanka Salary Tax Calculator 2026
- Route: `/salary-tax-calculator-sri-lanka-2026`
- Inputs: Monthly salary, annual bonus, other allowances
- Output: Annual taxable income, total tax, monthly deduction, net monthly salary
- Includes progressive slab breakdown table

### 2) Sri Lanka Vehicle Import Duty Calculator
- Route: `/vehicle-import-duty-calculator-sri-lanka`
- Inputs: CIF value, engine capacity, fuel type, vehicle type
- Output: Customs duty, PAL, excise duty, VAT, total tax, final vehicle cost

### 3) Sri Lanka EPF Maturity Calculator
- Route: `/epf-maturity-calculator-sri-lanka`
- Inputs: Monthly salary, years of service, annual interest rate
- Uses 8% employee + 12% employer contributions with yearly compounding

### 4) Sri Lanka Home Loan EMI Calculator
- Route: `/home-loan-calculator-sri-lanka`
- Inputs: Loan amount, interest rate, tenure
- Output: EMI, total interest payable, total repayment

## SEO Implementation

- SEO-friendly URLs for all tools
- Per-page optimized title and description
- Canonical tags
- Open Graph tags
- FAQ structured data via JSON-LD on each calculator page
- `robots.txt` and `sitemap.xml`
- Internal linking between calculators

## Bonus Pages

- Blog: `/blog`
- Contact: `/contact`
- Privacy Policy: `/privacy-policy`
- Google Analytics placeholder included
- Google AdSense placeholder included

## Project Structure

```text
src/main/java/com/aurora/srilankafinancialtoolsportal
├── SriLankaFinancialToolsPortalApplication.java
├── controller
│   ├── EpfController.java
│   ├── HomeController.java
│   ├── HomeLoanController.java
│   ├── SalaryTaxController.java
│   └── VehicleImportDutyController.java
├── model
│   ├── epf
│   │   ├── EpfRequest.java
│   │   └── EpfResult.java
│   ├── loan
│   │   ├── HomeLoanRequest.java
│   │   └── HomeLoanResult.java
│   ├── salary
│   │   ├── SalaryTaxRequest.java
│   │   ├── SalaryTaxResult.java
│   │   └── TaxSlabBreakdown.java
│   └── vehicle
│       ├── VehicleDutyRequest.java
│       └── VehicleDutyResult.java
├── service
│   ├── EpfCalculatorService.java
│   ├── HomeLoanService.java
│   ├── SalaryTaxService.java
│   └── VehicleImportDutyService.java
└── util
    ├── CurrencyUtil.java
    └── SeoUtil.java

src/main/resources
├── application.yml
├── static
│   ├── css/styles.css
│   ├── robots.txt
│   └── sitemap.xml
└── templates
    ├── blog.html
    ├── contact.html
    ├── epf.html
    ├── home-loan.html
    ├── index.html
    ├── privacy-policy.html
    ├── salary-tax.html
    ├── vehicle-duty.html
    └── fragments
        ├── footer.html
        ├── head.html
        └── navbar.html
```

## Run Locally

### Prerequisites

- Java 21
- Maven 3.9+

### Commands

```bash
mvn clean spring-boot:run
```

App runs at:

- `http://localhost:8080`

## Build

```bash
mvn clean package -DskipTests
```

Jar path:

- `target/sri-lanka-financial-tools-portal-1.0.0.jar`

## Deploy on Render.com

### Option A: Blueprint Deploy (Recommended)

1. Push this repository to GitHub.
2. In Render dashboard, choose **New +** > **Blueprint**.
3. Connect your repository.
4. Render reads `render.yaml` and configures build/start automatically.
5. Set `APP_BASE_URL` to your Render domain if different.

### Option B: Manual Web Service

1. Create a new **Web Service** on Render.
2. Environment: **Java**
3. Build command: `mvn clean package -DskipTests`
4. Start command: `java -Dserver.port=$PORT -jar target/sri-lanka-financial-tools-portal-1.0.0.jar`
5. Add environment variable:
   - `APP_BASE_URL=https://your-service-name.onrender.com`

## Notes

- Calculations are deterministic and stateless.
- This app provides planning estimates, not legal or tax filing advice.
- Replace analytics/ad placeholders with production IDs before launch.
