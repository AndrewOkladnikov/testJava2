package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        int fileCount=4;
        int partSize = 5000;

        // стирает предыдущий файл
        FileWriter fwr = new FileWriter("Result.txt", false);
        fwr.close();
        /*     Здесь меняем        */
        //String pathStr1 = "1_sort.txt";
        //String pathStr2 = "2_sort.txt";
        String pathStr1 = "str1_sort.txt";
        String pathStr2 = "str2_sort.txt";
        //String pathStr1 = "128MbSorted.txt";
        //String pathStr2 = "256MbSorted.txt";

        mergeArrayNew(pathStr1,pathStr2,fileCount,partSize);
     }

    private static ArrayList<String> fillBuffer(ICustomFileReader reader, int size) throws IOException {
        var valueList = new ArrayList<String>();
        while (!reader.isFileFinished() && valueList.size()<size)
        {
            try{
                var value = reader.getNext();
                if (value == null && reader.isFileFinished())
                {
                    break;
                }
                if (value != null)
                {
                    valueList.add(value);
                }
            }
            catch (NumberFormatException e){
                System.out.println(e.getMessage());
            }

        }

        return valueList;
    }

    private static ArrayList<String> fillIntBuffer(ICustomFileReader reader,int size) throws IOException {
        var numList = new ArrayList<String>();
        int[] buffer = new int[size];
        while (!reader.isFileFinished() && numList.size()<size)
        {
            try{
                var number = reader.getNext();
                if (number == null && reader.isFileFinished())
                {
                    break;
                }
                if (number != null)
                {
                    numList.add(number);
                }
            }
            catch (NumberFormatException e){
                System.out.println(e.getMessage());
            }

        }
        //int j = 0;
        //for(var v:numList){
        //    buffer[j++] = v;

       // }

        return numList;
    }   //  Не используется

    private static ArrayList<String> GetInputFilesFromArgs(String[] args) {
        ArrayList<String> arrOfFiles = new ArrayList<>();
        arrOfFiles.add("str1_sort.txt");
        arrOfFiles.add("str2_sort.txt");
        arrOfFiles.add("256MbSorted.txt");
        arrOfFiles.add("strings.txt");

        return arrOfFiles;
    }

    public static Integer[] sortArray(Integer[] arrayA){ // сортировка Массива который передается в функцию

        if (arrayA == null)  // проверяем не нулевой ли он?
        {
            return null;
        }
        // проверяем не 1 ли элемент в массиве?
        if (arrayA.length < 2) {
            return arrayA; // возврат в рекурсию в строки ниже см комменты.
        }
        // копируем левую часть от начала до середины
        Integer[] arrayB = new Integer[arrayA.length / 2];
        System.arraycopy(arrayA, 0, arrayB, 0, arrayA.length / 2);

        // копируем правую часть от середины до конца массива, вычитаем из длины первую часть
        int [] arrayC = new int[arrayA.length - arrayA.length / 2];
        System.arraycopy(arrayA, arrayA.length / 2, arrayC, 0, arrayA.length - arrayA.length / 2);

        // рекурсией закидываем поделенные обе части обратно в наш метод, он будет крутится до тех пор,
        // пока не дойдет до 1 элемента в массиве, после чего вернется в строку и будет искать второй такой же,
        // точнее правую часть от него и опять вернет его назад
        arrayB = sortArray(arrayB); // левая часть возврат из рекурсии строкой return arrayA;
        //arrayC = sortArray(arrayC); // правая часть возврат из рекурсии строкой return arrayA;

        // далее опять рекурсия возврата слияния двух отсортированных массивов
        //return mergeArray(arrayB, arrayC);
        return  null;
    }

    public static void mergeArrayNew(String str1, String str2,int fileCount,int partSize) throws IOException {
        String[] bufA = new String[fileCount];
        ICustomFileReader[] fileReaders = new ICustomFileReader[fileCount];

        int[] partPositions = new int[fileCount];
        ArrayList<ArrayList<String>> currentFileParts = new ArrayList<>();

        ArrayList<String> arrOfData = GetInputFilesFromArgs(bufA);
        for(int i=0; i < arrOfData.size() ; i++){
            var file = new File(arrOfData.get(i));
            var reader = new StringFileReader(file, null);
            fileReaders[i]=reader;
            currentFileParts.add(fillBuffer(fileReaders[i],partSize));
            partPositions[i]=0;
        }

        ArrayList<String> resultArr = new ArrayList<String>();
        while(true) {
            for (int i= 0; i<fileCount; i++) {
                if (fileReaders[i].isFileFinished()) {
                    if (currentFileParts.get(i) != null && partPositions[i] == currentFileParts.get(i).size()) {
                        currentFileParts.set(i, null);
                    }

                    continue;
                }
                boolean partHasEnded = partPositions[i] == currentFileParts.get(i).size();
                if (partHasEnded) {
                    var newFilePart = fillBuffer(fileReaders[i], partSize);
                    currentFileParts.set(i, newFilePart);
                    partPositions[i] = 0;
                }
            }

            while (!isAnyPartsFinished(fileCount, partPositions, currentFileParts))
            {
                var minimum = GetMinimum(currentFileParts, partPositions);
                if (minimum == null)
                {
                    break;
                }
                resultArr.add(minimum.getValue());
                partPositions[minimum.getKey()]++;
            }

            writeToTxt(resultArr, "Result.txt");
            resultArr.clear();

            if (allFilesAreFinished(fileReaders) && allPartsAreFinished(currentFileParts, partPositions))
            {
                break;
            }
         }
    }

    private static boolean allPartsAreFinished(ArrayList<ArrayList<String>> currentFileParts, int[] partPositions) {
        boolean allPartsFinished = true;
        for (int i = 0; i< partPositions.length; i++)
        {
            if (currentFileParts.get(i) != null && partPositions[i] != currentFileParts.get(i).size())
            {
                allPartsFinished = false;
            }
        }
        return allPartsFinished;
    }

    private static boolean allFilesAreFinished(ICustomFileReader[] fileReaders) {
        boolean allFilesFinished = true;
        for (int i = 0; i< fileReaders.length; i++)
        {
            if (!fileReaders[i].isFileFinished())
            {
                allFilesFinished = false;
            }
        }

        return allFilesFinished;
    }

    private static boolean isAnyPartsFinished(int fileCount, int[] partPositions, ArrayList<ArrayList<String>> currentFileParts) {
        boolean anyPartsFinished = false;
        for (int i = 0; i< fileCount; i++)
        {
            if (currentFileParts.get(i) != null && partPositions[i] == currentFileParts.get(i).size())
            {
                anyPartsFinished = true;
            }
        }
        return anyPartsFinished;
    }

    private static Pair<Integer, String> GetMinimum(ArrayList<ArrayList<String>> currentFileParts, int[] partPositions) {
            Pair<Integer, String> minimum = null;

            for (int i=0; i< currentFileParts.size(); i++)
            {
                if (currentFileParts.get(i) != null)
                {
                    minimum = new Pair(i, currentFileParts.get(i).get(partPositions[i]));
                    break;
                }
            }

            if (minimum == null)
            {
                return null;
            }

            for (int i = 0; i < currentFileParts.size(); i++)
            {
                if (currentFileParts.get(i) == null)
                {
                    continue;
                }
                var currentPartValue = currentFileParts.get(i).get(partPositions[i]);
                if (currentPartValue.compareTo(minimum.getValue()) < 1)
                {
                    minimum = new Pair(i, currentPartValue);
                }
            }
            return minimum;
        }


    public static void writeToTxt ( ArrayList<String> buf,String filePathName) throws IOException {
        //BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePathName)));
        FileWriter fwr = new FileWriter(filePathName,true);
        for (var v:buf) {
            fwr.write(v);
            fwr.write("\n");
        }
        fwr.close();
    }
}
