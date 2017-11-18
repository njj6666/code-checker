# Introduction 
Validate PLM VB Script code style and generate report.

ARTIFACTS:
Before putting the artifacts to artifact repository, we kept them in the project folder for now.

# Releases
see fold release

# Command Line
Usage: java -jar plm-code-checker.jar <directory of your scripts>

# Build
mvn -e clean package

# SonarCloud
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.organization=njj6666-github -Dsonar.login=2320d83dd6af3bde67480f27de9f67ad32f31d01

