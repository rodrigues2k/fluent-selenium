package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.seleniumhq.selenium.fluent.BaseTest;
import org.seleniumhq.selenium.fluent.FluentExecutionStopped;
import org.seleniumhq.selenium.fluent.FluentRecorder;
import org.seleniumhq.selenium.fluent.FluentWebDriverImpl;
import org.seleniumhq.selenium.fluent.FluentWebElements;
import org.seleniumhq.selenium.fluent.RecordingFluentWebDriverFactoryImpl;
import org.seleniumhq.selenium.fluent.WebDriverJournal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class textarea extends BaseTest {

    private StringBuilder sb;
    private WebDriver wd;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);
    }

    @Test
    public void textarea_functionality() {

        FluentWebElements fe = fwd.textarea()
                .textarea(By.xpath("@foo = 'bar'"))
                .textarea(By.cssSelector("baz"))
                .textareas();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: textarea) -> we1\n" +
                        "we1.getTagName() -> 'textarea'\n" +
                        "we1.findElement(By.xpath: .//textarea[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'textarea'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'textarea'\n" +
                        "we3.findElements(By.tagName: textarea) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'textarea'\n" +
                        "we5.getTagName() -> 'textarea'\n"
        ));
    }

    @Test
    public void textareas_functionality() {
        FluentWebElements fe = fwd.textarea()
                .textareas(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: textarea) -> we1\n" +
                        "we1.getTagName() -> 'textarea'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'textarea'\n" +
                        "we3.getTagName() -> 'textarea'\n"
        ));
    }

    @Test
    public void textarea_mismatched() {
        try {
            fwd.textarea(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.textarea(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


    @Test
    public void recording_textarea_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl().recordTo(recording)
                .textarea()
                .textarea(By.xpath("@foo = 'bar'"))
                .textarea(By.cssSelector("baz"))
                .textareas();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: textarea) -> we1\n" +
                        "we1.getTagName() -> 'textarea'\n" +
                        "we1.findElement(By.xpath: .//textarea[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'textarea'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'textarea'\n" +
                        "we3.findElements(By.tagName: textarea) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'textarea'\n" +
                        "we5.getTagName() -> 'textarea'\n"
        ));
    }

    @Test
    public void recording_textareas_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl()
               .recordTo(recording)
                .textarea()
                .textareas(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: textarea) -> we1\n" +
                        "we1.getTagName() -> 'textarea'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'textarea'\n" +
                        "we3.getTagName() -> 'textarea'\n"
        ));
    }

    @Test
    public void recording_textarea_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecordingFluentWebDriverFactoryImpl().recordTo(recording).textarea(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.textarea(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }


}
