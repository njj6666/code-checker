# Introduction 
Validate PLM VB Script code style and generate report.
This tool supports to extend to validate other types of file. To developer new types of checker, see `How to contribute`. 

ARTIFACTS:
Before putting the artifacts to artifact repository, we kept them in the project folder for now.

# Releases
see folder release

# Command Line
`java -jar plm-code-checker.jar <directory of your scripts>`  
Used by Jenkins integration  
`java -jar plm-code-checker.jar jenkins`  

# Build
mvn -e clean package

# SonarCloud
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.organization=njj6666-github -Dsonar.login=2320d83dd6af3bde67480f27de9f67ad32f31d01

# How to contribute
## How to add a new checker
1. Extends abstract class `Checker`. 
2. Add logic in class `CheckerFactory`
3. Configure config file in project root directory.
4. `VBSChecker` is an example.

## How to add new rules for existed checker
1. Implements interface `Rule`.
2. Configure config file in project root directory.
3. `VBSVariableNamingRule` is an example.


