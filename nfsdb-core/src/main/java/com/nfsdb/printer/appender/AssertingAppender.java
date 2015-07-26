/*******************************************************************************
 * _  _ ___ ___     _ _
 * | \| | __/ __| __| | |__
 * | .` | _|\__ \/ _` | '_ \
 * |_|\_|_| |___/\__,_|_.__/
 * <p/>
 * Copyright (c) 2014-2015. The NFSdb project and its contributors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.nfsdb.printer.appender;

public class AssertingAppender implements Appender {

    private final String[] expected;
    private int index;

    public AssertingAppender(String expected) {
        this.expected = expected.split("\n");
    }

    @Override
    public void append(StringBuilder stringBuilder) {
        if (index < expected.length) {
            String s = stringBuilder.toString();
            if (!expected[index].equals(s)) {
                throw new AssertionError(("\n\n>>>> Expected [ " + (index + 1) + " ]>>>>\n") + expected[index] + "\n<<<<  Actual  <<<<\n" + s + '\n');
            }
        } else {
            throw new AssertionError("!!! Expected " + expected.length + " lines, actual " + index);
        }
        index++;
    }

    @Override
    public void close() {
        if (index < expected.length) {
            throw new AssertionError("!!! Too few rows. Expected " + expected.length + ", actual " + index);
        }
    }
}
