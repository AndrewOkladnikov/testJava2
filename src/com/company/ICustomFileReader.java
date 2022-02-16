package com.company;

import java.io.IOException;

public interface ICustomFileReader {
    boolean isFileFinished();
    Integer getNext() throws IOException;
}
