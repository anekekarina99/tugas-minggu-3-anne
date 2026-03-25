# Tugas Minggu 3 - Selenium TestNG (SIT)

Repo ini berisi automation test menggunakan **Selenium 4** dan **TestNG** untuk:
- Positif & Negatif `Login`
- Positif & Negatif `Add to Cart` (Inventory)

## Identitas
- Nama repo: `tugas-minggu-3-anne`
- Package: `juaracoding.anne`

## SIT (Google Spreadsheet)
- URL SIT: `https://docs.google.com/spreadsheets/d/1v081TLkjiTAauoKh9ooT4w7i9xIyXgtQsKRnKU2DuvM/edit?usp=sharing`

## Template SIT (format Excel)
- Lihat `docs/SIT.md`
- Versi tab-separated untuk dibuka di Excel: `docs/SIT.tsv`

## Requirement
- Java 11+
- Maven
- Browser: Chrome (disarankan)

## Jalankan Test
Di dalam folder repo:

```bash
mvn test
```

Opsional lewat system property:

```bash
mvn test -DbaseUrl=https://www.saucedemo.com
```

## Credensial (SauceDemo)
- Valid: `standard_user` / `secret_sauce`
- Invalid: (di kode) `invalid_user` / `invalid_pass`

