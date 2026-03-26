package com.automation.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class TestNgResultsDashboardTest {

    @Test
    void generatesPrettyHtmlWithSummaryAndFailureDetails() throws Exception {
        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <testng-results ignored="1" total="2" passed="1" failed="1" skipped="0">
              <reporter-output>
                Root level output
              </reporter-output>
              <suite started-at="2026-03-12T20:59:52 ART" name="Demo suite" finished-at="2026-03-12T21:00:00 ART" duration-ms="8066">
                <test started-at="2026-03-12T20:59:52 ART" name="Demo test" finished-at="2026-03-12T21:00:00 ART" duration-ms="8066">
                  <class name="example.LoginTests">
                    <test-method is-config="true" name="setup" started-at="2026-03-12T20:59:52 ART" finished-at="2026-03-12T20:59:52 ART" duration-ms="23" status="PASS" />
                    <test-method name="loginOk" description="happy path" started-at="2026-03-12T20:59:54 ART" finished-at="2026-03-12T20:59:55 ART" duration-ms="504" status="PASS" />
                    <test-method name="loginFail" started-at="2026-03-12T20:59:59 ART" finished-at="2026-03-12T21:00:00 ART" duration-ms="444" status="FAIL">
                      <params>
                        <param index="0">
                          <value><![CDATA[user=locked_out]]></value>
                        </param>
                      </params>
                      <exception class="java.lang.AssertionError">
                        <message><![CDATA[Expected 200 but was 401]]></message>
                        <full-stacktrace><![CDATA[line 1
line 2]]></full-stacktrace>
                      </exception>
                    </test-method>
                  </class>
                </test>
              </suite>
            </testng-results>
            """;

        Path baseDir = Path.of("target", "tmp-testng-dashboard");
        Files.createDirectories(baseDir);

        Path tempDir = Files.createTempDirectory(baseDir, "case-");
        Path input = tempDir.resolve("testng-results.xml");
        Path output = tempDir.resolve("testng-results-pretty.html");

        Files.writeString(input, xml.trim(), StandardCharsets.UTF_8);

        TestNgResultsDashboard dashboard = new TestNgResultsDashboard();
        TestNgResultsDashboard.GenerationResult result = dashboard.generateReport(input, output);
        String html = Files.readString(output, StandardCharsets.UTF_8);

        assertTrue(Files.exists(output));
        assertEquals(2, result.report().total());
        assertEquals(1, result.report().passed());
        assertEquals(1, result.report().failed());
        assertEquals(1, result.report().ignored());
        assertTrue(html.contains("Pretty dashboard for testng-results.xml"));
        assertTrue(html.contains("Demo suite"));
        assertTrue(html.contains("Expected 200 but was 401"));
        assertTrue(html.contains("Config methods (1)"));
        assertTrue(html.contains("Root level output"));
        assertTrue(html.contains("user=locked_out"));
    }
}
