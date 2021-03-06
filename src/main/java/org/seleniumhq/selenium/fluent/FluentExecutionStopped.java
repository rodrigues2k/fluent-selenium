/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.seleniumhq.selenium.fluent;

public class FluentExecutionStopped extends RuntimeException {
    private int retries;
    private long durationInMillis;

    public FluentExecutionStopped(String message, Throwable cause) {
        super(message, cause);
    }
    protected FluentExecutionStopped(String message) {
        super(message);
    }

    public FluentExecutionStopped setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public void setDuration(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    @Override
    public String getMessage() {
        String message = "";
        if (retries > 0) {
            message = message + retries
                    + " retries over "
                    + durationInMillis
                    + " millis; ";
        }
        return message + super.getMessage();
    }

    public static class BecauseOfStaleElement extends FluentExecutionStopped {
        public BecauseOfStaleElement(String message, RuntimeException cause) {
            super(message, cause);
        }
    }
    public static class BecauseNothingMatchesInFilter extends FluentExecutionStopped {
        public BecauseNothingMatchesInFilter(String message) {
            super(message);
        }
    }

}
