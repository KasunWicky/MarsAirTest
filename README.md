Browser selection:
Default browser is Chrome;
If different browser is required following configuration should be setup from Environment variables
Currently supported only Chrome,Safari and Firefox
eg : -Dbrowser=firefox; -Dbrowser=safari

Headless mode;
Default mode is headless false
To enable : -Dheadless=true
Note: Safari browser's headless mode is not supported.

Environment selection:
Default environment mode is test. 
To enable : -Denv=<envName>
eg : -Denv=stage; -Denv=test

Validate the style check
mvn validate 


# Smoke
-Dtestgroup
mvn compile exec:java -Dexec.mainClass="runner.TestSuiteRunner" -Dexec.args="smoke"

# Regression
mvn compile exec:java -Dexec.mainClass="runner.TestSuiteRunner" -Dexec.args="regression"