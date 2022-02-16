package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        var input = new File("strings.txt");
        ICustomFileReader reader = new IntFileReader(input, null);

        while (!reader.isFileFinished())
        {
            var number = reader.getNext();
            if (number == null && reader.isFileFinished())
            {
                break;
            }
            if (number != null)
            {
                System.out.println(number);
            }
        }
        // var outputFile = GetOutputFileFromArgs(args);
        // var inputFiles = GetInputFilesFromArgs(args);

        //outputFile = MergeFiles(inputFiles, SortOrder.ASCENDING);
    }

    private static ArrayList<File> GetInputFilesFromArgs(String[] args) {
        throw new UnsupportedOperationException();        
    }

    private static File GetOutputFileFromArgs(String[] args) {
        throw new UnsupportedOperationException();
    }

    private static File MergeFiles(ArrayList<File> inputFiles, SortOrder sortOrder) {
        if (inputFiles.size() == 1)
        {
            return inputFiles.get(0);
        }
        var pairs = SplitByPairs(inputFiles);
        var sortedFiles = new ArrayList<File>();
        for (var pair: pairs) {
            var sortedFile = MergeSortedFiles(pair[0], pair[1], sortOrder);
            sortedFiles.add(sortedFile);
        }
        return MergeFiles(sortedFiles, sortOrder);
    }

    private static ArrayList<File[]> SplitByPairs(ArrayList<File> inputFiles) {
        throw new UnsupportedOperationException();
    }

    private static File MergeSortedFiles(File firstFile, File secondFile, SortOrder sortOrder) {
        throw new UnsupportedOperationException();
       /* String filename = GenerateFileName(firstFile, secondFile);
        var outputFile = new File(filename);
        int firstFileCurrentValue = GetNextValue(firstFile);
        int secondFileCurrentValue = GetNextValue(secondFile);

        if (sortOrder == SortOrder.ASCENDING)
        {
            while (firstFile.NotFinished || secondFile.NotFinished) {
                if (firstFileCurrentValue < secondFileCurrentValue) {
                    outputFile.Write(firstFileCurrentValue);
                    firstFileCurrentValue = GetNextValue(firstFile);
                } else {
                    outputFile.Write(secondFileCurrentValue);
                    secondFileCurrentValue = GetNextValue(secondFile);
                }
            }

            var remainingValues = firstFile.NotFinished ? firstFile.GetRemainingValues : secondFile.GetRemainingValues;
            outputFile.AppendRemainingValues(remainingValues);
        }

        // TODO: убрать дублирование с ASCENDING
        if (sortOrder == SortOrder.DESCENDING)
        {
            while (firstFile.NotFinished || secondFile.NotFinished) {
                if (firstFileCurrentValue > secondFileCurrentValue) {
                    outputFile.Write(firstFileCurrentValue);
                    firstFileCurrentValue = GetNextValue(firstFile);
                } else {
                    outputFile.Write(secondFileCurrentValue);
                    secondFileCurrentValue = GetNextValue(secondFile);
                }
            }

            var remainingValues = firstFile.NotFinished ? firstFile.GetRemainingValues : secondFile.GetRemainingValues;
            outputFile.AppendRemainingValues(remainingValues);

        }

        return outputFile; */
    }

    public static int [] sortArray(int[] arrayA){ // сортировка Массива который передается в функцию

        if (arrayA == null)  // проверяем не нулевой ли он?
        {
            return null;
        }
        // проверяем не 1 ли элемент в массиве?
        if (arrayA.length < 2) {
            return arrayA; // возврат в рекурсию в строки ниже см комменты.
        }
        // копируем левую часть от начала до середины
        int [] arrayB = new int[arrayA.length / 2];
        System.arraycopy(arrayA, 0, arrayB, 0, arrayA.length / 2);

        // копируем правую часть от середины до конца массива, вычитаем из длины первую часть
        int [] arrayC = new int[arrayA.length - arrayA.length / 2];
        System.arraycopy(arrayA, arrayA.length / 2, arrayC, 0, arrayA.length - arrayA.length / 2);

        // рекурсией закидываем поделенные обе части обратно в наш метод, он будет крутится до тех пор,
        // пока не дойдет до 1 элемента в массиве, после чего вернется в строку и будет искать второй такой же,
        // точнее правую часть от него и опять вернет его назад
        arrayB = sortArray(arrayB); // левая часть возврат из рекурсии строкой return arrayA;
        arrayC = sortArray(arrayC); // правая часть возврат из рекурсии строкой return arrayA;

        // далее опять рекурсия возврата слияния двух отсортированных массивов
        return mergeArray(arrayB, arrayC);
    }

    public static int [] mergeArray(int [] arrayА, int [] arrayB) {

        int [] arrayC = new int [arrayА.length + arrayB.length];
        int positionA = 0, positionB = 0;

        for (int i = 0; i < arrayC.length; i++) {
            if (positionA == arrayА.length){
                arrayC[i] = arrayB[positionB];
                positionB++;
            } else if (positionB == arrayB.length) {
                arrayC[i] = arrayА[positionA];
                positionA++;
            } else if (arrayА[ positionA] < arrayB[ positionB]) {
                arrayC[i] = arrayА[positionA];
                positionA++;
            } else {
                arrayC[i] = arrayB[ positionB];
                positionB++;
            }
        }
        return arrayC;
    }

    public static void writeToTxt ( int[] buf,String filePathName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePathName)));
        for (int i = 0; i < buf.length; i++) {

            //writer.write(String.valueOf(array[i]));
            //writer.write(" ");
            writer.write(String.valueOf(buf[i]));

            writer.write("\n");
        }
        writer.flush();
    }
}
//10, 12, 13, 16 ,17, 25, 51, 111, 119, 138, 184 ,211
//0, 2, 3, 5, 6 ,7, 11, 19, 21, 38, 84 ,125