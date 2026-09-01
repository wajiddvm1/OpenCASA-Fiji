# OpenCASA Fiji
OpenCASA is a sperm analysis tool designed for Fiji/ImageJ. This fork keeps the project working with modern Fiji setups and includes the validated OpenCASA data used to check the analysis pipeline.

## What is included
- The ready-to-use Fiji plugin: OpenCASA_Fiji.jar
- The validated sample datasets in the Test_data folder
- A simple installation guide for regular users

## Quick start
1. Download the plugin file OpenCASA_Fiji.jar.
2. Copy it into your Fiji plugins folder.
3. Put javaml-0.1.7.jar in the Fiji plugins/jars folder.
4. Restart Fiji.
5. Open Plugins > OpenCASA.

## Validation data
The Test_data folder contains the original OpenCASA example datasets used to validate this workflow. These are the checked reference files for the project and are kept as the validated dataset for testing and comparison.

## System requirements
- Fiji/ImageJ installed on your computer
- Java 8 or newer
- Enough available memory for video analysis

## More information
For the original scientific background, see the OpenCASA paper: https://journals.plos.org/ploscompbiol/article?id=10.1371/journal.pcbi.1006691

## Notes for users
This version is intended to be simple and easy to use for regular Fiji users. The goal is to make OpenCASA easier to install and reliable for analysis work with current Fiji environments.
