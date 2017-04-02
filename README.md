# AutomatedCodeCoverage

This project uses ASM byte-code manipulation framework to build an automated coverage collection tool that can capture the statement coverage for the program under test. 

The tool uses Java Agent to perform on-the-fly code instrumentation, and 
It is integrated with the Maven build system so that the tool can be triggered by simply changing the pom.xml file of the project under test. 

We applied our tool to 10 real-world Java projects (>1000 lines of code) with JUnit tests (>50 tests) from GitHub to collect the statement coverage for its JUnit tests. 
