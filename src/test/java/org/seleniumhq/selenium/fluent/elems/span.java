package org.seleniumhq.selenium.fluent.elems;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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

public class span extends BaseTest {

    private StringBuilder sb;
    private FluentWebDriverImpl fwd;
    private WebDriverJournal wd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        wd = new WebDriverJournal(sb);
        fwd = new FluentWebDriverImpl(wd);
        FAIL_ON_NEXT.set(null);
    }

    @Test
    public void span_functionality() {
        FluentWebElements fe = fwd.span()
                .span(By.xpath("@foo = 'bar'"))
                .span(By.cssSelector("baz"))
                .spans();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: span) -> we1\n" +
                        "we1.getTagName() -> 'span'\n" +
                        "we1.findElement(By.xpath: .//span[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'span'\n" +
                        "we3.findElements(By.tagName: span) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'span'\n" +
                        "we5.getTagName() -> 'span'\n"
        ));
    }


    @Test
    public void spans_functionality() {
        FluentWebElements fe = fwd.span()
                .spans(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: span) -> we1\n" +
                        "we1.getTagName() -> 'span'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we3.getTagName() -> 'span'\n"
        ));
    }

    @Test
    public void span_mismatched() {
        try {
            fwd.span(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.span(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }


    @Test
    public void recording_span_functionality() {

        FluentRecorder recorder = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl()
                .recordTo(recorder)
                .span()
                .span(By.xpath("@foo = 'bar'"))
                .span(By.cssSelector("baz"))
                .spans();

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recorder.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: span) -> we1\n" +
                        "we1.getTagName() -> 'span'\n" +
                        "we1.findElement(By.xpath: .//span[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'span'\n" +
                        "we3.findElements(By.tagName: span) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'span'\n" +
                        "we5.getTagName() -> 'span'\n"
        ));
    }

    @Test
    public void recording_spans_functionality() {

        FluentRecorder recording = new FluentRecorder();

        FluentWebElements fe = new RecordingFluentWebDriverFactoryImpl()
                .recordTo(recording)
                .span()
                .spans(By.name("qux"));

        assertThat(fe, notNullValue());
        assertThat(sb.toString(), equalTo(""));

        recording.recording().playback(fwd);

        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: span) -> we1\n" +
                        "we1.getTagName() -> 'span'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'span'\n" +
                        "we3.getTagName() -> 'span'\n"
        ));
    }

    @Test
    public void recording_span_mismatched() {

        FluentRecorder recording = new FluentRecorder();

        new RecordingFluentWebDriverFactoryImpl().recordTo(recording).span(By.linkText("mismatching_tag_name"))
                .clearField();

        try {
            recording.recording().playback(fwd);
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.span(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }

    }

}
