## Browser selection:
Default browser is Chrome.\
If different browser is required following configuration should be setup from Environment variables
Currently supported only `Chrome,Safari and Firefox`
```bash
# Execute the using the firefox
-Dbrowser=firefox

# Execute the using the Safari (Dev mode has to turn on)
-Dbrowser=safari
````

## Headless mode;
Default mode is headless false\
Note: Safari browser's headless mode is not supported.
```bash
# To enable add the only chrome and Safari
-Dheadless=true
```
## Environment selection:
Default environment mode is test.
To enable : `-Denv=<envName>`
```bash
# Execute test using the stage environment
-Denv=stage

# Execute test using the test environment
-Denv=test
```
## Validate the style check
```bash
mvn validate
```

## Running Tests

### From IDE
Run `TestSuiteRunner.java` as a Java application directly.  
Default group: `regression`.

### Maven
```bash
# Run regression suite (default)
mvn exec:java

# Run specific group
mvn exec:java -Dtestgroup=smoke
mvn exec:java -Dtestgroup=regression

# Run headless on Firefox against stage
mvn exec:java -Dtestgroup=regression -Dbrowser=firefox -Dheadless=true -Denv=stage