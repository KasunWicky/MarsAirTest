package runner;

import groups.TestGroup;
import lombok.extern.slf4j.Slf4j;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
public class TestSuiteRunner {

    private static final String GROUPS_PACKAGE = "groups.";

    public static void main(String[] args) throws Exception {
        String groupName = resolveGroupName(args);
        TestGroup group  = loadGroup(groupName);

        log.info("========================================");
        log.info("Group  : {}",  groupName);
        log.info("Suite  : {}", group.getSuiteName());
        log.info("Classes: {}" , group.getTestClasses().stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining(", ")));
        log.info("========================================");

        XmlSuite suite = buildSuite(group);

        TestNG testng = new TestNG();
        testng.setXmlSuites(Collections.singletonList(suite));
        testng.setOutputDirectory("target/test-output");  // report output
        testng.addListener(new ScreenshotListener());
        testng.run();

        System.exit(testng.getStatus());
    }

    private static XmlSuite buildSuite(TestGroup group) {
        XmlSuite suite = new XmlSuite();
        suite.setName(group.getSuiteName());
        suite.setVerbose(1);

        XmlTest xmlTest = new XmlTest(suite);
        xmlTest.setName(group.getSuiteName());

        List<XmlClass> xmlClasses = group.getTestClasses()
                .stream()
                .map(c -> new XmlClass(c.getName()))
                .collect(Collectors.toList());

        xmlTest.setXmlClasses(xmlClasses);
        return suite;
    }

    private static String resolveGroupName(String[] args) {
        if (args.length > 0 && !args[0].isBlank()) {
            return args[0].trim().toLowerCase();
        }
        String prop = System.getProperty("testgroup");
        if (prop != null && !prop.isBlank()) {
            return prop.trim().toLowerCase();
        }
        return "regression";
    }

    private static TestGroup loadGroup(String groupName) throws Exception {
        String className = GROUPS_PACKAGE + capitalize(groupName);
        try {
            Class<?> clazz = Class.forName(className);
            return (TestGroup) clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "No group found for '" + groupName + "'. " +
                            "Expected class: " + className + ". " +
                            "Available: smoke, regression"
            );
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}