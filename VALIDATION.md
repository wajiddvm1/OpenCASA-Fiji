# Author Fixture Validation

This repository was validated using the data in `Test_Data`, which corresponds to the author-provided fixtures associated with these publications:

- Alquezar-Baeta et al., 2019, *OpenCASA: A new open-source and scalable tool for sperm quality analysis*, DOI: 10.1371/journal.pcbi.1006691.
- Yaniz et al., 2020, *Expanding the Limits of Computer-Assisted Sperm Analysis through the Development of Open Software*, DOI: 10.3390/biology9080207.

## Verified Motility Regression

The supplied `Sample_21_20170515` directory contains eight files named as AVI videos and a saved OpenCASA baseline at `Results_Backup_20260831_103302/Average_Motility.csv`.

The comparison test applies the published Motility settings:

- 0.481 microns/pixel
- cell area 10-100 square microns
- 60 frames/second
- 10-frame minimum track length
- 20 microns maximum inter-frame displacement
- 4-frame moving-average window
- 10 microns/second minimum VCL

Seven valid AVI files reproduced the supplied baseline exactly, to three decimal places, for motile trajectory count, VSL, VCL, VAP, and motility percentage:

| Video | Motile tracks | VSL (um/s) | VCL (um/s) | VAP (um/s) | Motility (%) |
| --- | ---: | ---: | ---: | ---: | ---: |
| Muestra_21_1 | 93 | 125.501 | 246.230 | 148.863 | 88.571 |
| Muestra_21_2 | 97 | 115.887 | 247.210 | 146.503 | 92.381 |
| Muestra_21_3 | 100 | 123.842 | 252.046 | 155.288 | 81.967 |
| Muestra_21_5 | 125 | 126.478 | 269.836 | 168.534 | 88.652 |
| Muestra_21_6 | 106 | 129.647 | 267.425 | 161.922 | 89.831 |
| Muestra_21_7 | 84 | 139.014 | 273.874 | 165.758 | 90.323 |
| Muestra_21_8 | 90 | 120.664 | 250.630 | 149.058 | 89.109 |

`Muestra_21_4.avi` cannot be analyzed: it is a 5,347-byte tab-separated text export beginning with `Line`, not a RIFF AVI file. The baseline row for that video exists, but the original video is not present under that filename.

Run this regression when the author fixture directory is available:


## Fixture Coverage

| Module | Available author fixtures | Numerical baseline status |
| --- | --- | --- |
| Motility | 16 AVI-named files, parameter sheet, workbook, saved CSV output | Exact per-video regression for seven valid Sample 21 videos |
| Viability | 52 fluorescence images, parameter sheet, summary workbook | Aggregate author baseline available; no per-image reference counts supplied |
| Morphometry | 100 images, parameter sheet, ISAS/OpenCASA workbook | Aggregate author baseline available; fixture image IDs require mapping to worksheet IDs |
| Chemotaxis | 4 AVI videos and parameter sheet | Published expected directional behavior and aggregate statistics; no per-video expected result export supplied |
| Accumulation | 20 AVI videos and published heat-map/ROI examples | Published qualitative case-control expectation; no raw time-series baseline supplied |
| Functionality | 9 fluorescence images and publication figures | Published aggregate subtype comparison; no per-image manual classifications supplied |
| Concentration | 12 phase-contrast images and publication figures | Published aggregate comparison; no per-image manual counts supplied |

## Publication Benchmarks

The 2019 paper reports Motility correlation with ISAS between 0.8180 and 0.9756, Viability correlation with flow cytometry of 0.7901 (viable) and 0.7987 (non-viable), and Morphometry correlation with CASMA-F/ImageJ between 0.8226 and 0.9368.

The 2020 paper reports Functionality subtype correlations against manual classification, Concentration correlation with manual Neubauer counting of 0.958 with -2.407% Bland-Altman bias, and qualitative Accumulation separation between attractant and control videos.

These aggregate publication statistics are appropriate scientific context, but they are not per-file expected values. Exact automated assertions for those modules require the corresponding raw manual-count, ROI/time-series, or result-export tables.
