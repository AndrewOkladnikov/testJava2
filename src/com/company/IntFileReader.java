package com.company;

import java.io.*;

public class IntFileReader implements ICustomFileReader {

    private BufferedReader bufferedReader;
    private Integer maxSize;
    private boolean isFinished = false;

    // Если передать maxSize == null, то за памятью класс следить не будет
    public IntFileReader(File file, Integer maxSize) throws FileNotFoundException {
        if (file == null) // или exist?
        {
            throw new NullPointerException(); // узнать какой exception нужен
        }
        bufferedReader = new BufferedReader(new FileReader(file));
        maxSize = maxSize;
    }

    public boolean isFileFinished()
    {
        return isFinished;
    }

    public String getNext() throws IOException {
        var stringBuilder = new StringBuilder();

        boolean isFaulted = false;
        while (true) {
            var nextChar = bufferedReader.read();

            if (nextChar == -1) {
                isFinished = true;
                break;
            }

            if (!isFaulted && nextChar >= '0' && nextChar <= '9') {
                stringBuilder.append((char)nextChar);

                if (maxSize != null && stringBuilder.length() > maxSize) {
                    stringBuilder.setLength(0);
                    isFaulted = true;
                }

                continue;
            }

            if (nextChar == '\n') {
                break;
            }

            // для работы с CRLF
            if (nextChar == 13) {
                continue;
            }

            isFaulted = true;
        }
        String result1= null;
        //Integer result = null;
        try {
            var resultString = stringBuilder.toString();
            if (resultString == "")
            {
                return null;
            }
            result1 = resultString;
        }
        catch (Exception ex)
        {
            isFaulted = true;
        }

        if (isFaulted)
        {
            //System.out.println("DEBUG: IS FAULTED; FINISHED:" + isFinished);
            return null;
        }

        return result1;
    }
}
