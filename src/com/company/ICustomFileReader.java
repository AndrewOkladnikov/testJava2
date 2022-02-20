package com.company;

import java.io.IOException;

public interface ICustomFileReader {
    boolean isFileFinished();
    String getNext() throws IOException;
}
