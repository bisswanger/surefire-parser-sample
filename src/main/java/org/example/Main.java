package org.example;

import org.apache.maven.plugin.surefire.log.api.NullConsoleLogger;
import org.apache.maven.plugins.surefire.report.ReportTestSuite;
import org.apache.maven.plugins.surefire.report.SurefireReportParser;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create test file
        System.out.println("Creating sample file");

        Path testDataFolder = Paths.get("testdata");
        Files.createDirectories(testDataFolder);
        Path testFile = testDataFolder.resolve("huge.xml");

        try (BufferedWriter w = Files.newBufferedWriter(testFile)) {
            w.write(
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                            + "<testsuite xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" errors=\"0\" failures=\"1\" name=\"com.adobe.qe.eaasmockits.MockIT\" skipped=\"0\" tests=\"4\" time=\"2.413\" version=\"3.0\" xsi:noNamespaceSchemaLocation=\"https://maven.apache.org/surefire/maven-failsafe-plugin/xsd/failsafe-test-report-3.0.xsd\">\n"
                            + "  <properties></properties>"
                            + "  <testcase classname=\"com.adobe.qe.eaasmockits.MockIT\" name=\"validateTopology\" time=\"2.206\">\n"
                            + "    <system-err><![CDATA[\n");

            for (int i = 0; i < 3000000; i++) {
                w.write(
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n");
                w.flush();
            }
            w.write("]]></system-err>\n" + "  </testcase>\n" + "</testsuite>\n");
            w.flush();
        }

        System.out.println("File size: " + Files.size(testFile) / 1024 / 1024 + " MB");

        // Parse test file
        System.out.println("Parsing test file");

        SurefireReportParser parser = new SurefireReportParser(
                Collections.singletonList(testDataFolder.toFile()),
                new NullConsoleLogger());

        List<ReportTestSuite> testSuites = parser.parseXMLReportFiles();
        System.out.println("Test suites: " + testSuites.size());

        if (testSuites.size() > 0) {
            System.out.println("Test suite 1: " + testSuites.get(0).getFullClassName());
        }
    }
}