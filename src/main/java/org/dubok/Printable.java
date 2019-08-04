package org.dubok;

import java.io.PrintStream;

public interface Printable {
    String dumpIndent = "  ";
    String javaIndent = "    ";
    void print(final PrintStream printStream, final String indent);
}
