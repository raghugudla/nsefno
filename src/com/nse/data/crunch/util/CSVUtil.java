package com.nse.data.crunch.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CSVUtil {

    private final static String DELIMITER = "\",";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private final static NumberFormat NF_ENG = NumberFormat.getNumberInstance(Locale.ENGLISH);
    //private final static NumberFormat NF_SWE = NumberFormat.getInstance(new Locale("sv", "SE"));

    public static SortedSet<LocalDate> readFrom(final String dir, final int keyIndex) throws Exception {

        List<File> files = readFilesFrom(dir);

        SortedSet<LocalDate> dates = new TreeSet<>(Comparator.reverseOrder());

        for(File file: files) {
            String line = "";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine();

                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] row = line.split(DELIMITER);
                    LocalDate date = LocalDate.parse(parse(row[keyIndex]), FORMATTER);
                    dates.add(date);
                }
            }
        }

        return dates;
    }

    public static SortedMap<LocalDate, Float[]> readFrom(final String dir, final int keyIndex, final int valIndex) throws Exception {

        List<File> files = readFilesFrom(dir);

        SortedMap<LocalDate, Float[]> data = new TreeMap<>(Comparator.reverseOrder());

        for(File file: files) {
            String line = "";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine();

                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] row = line.split(DELIMITER);
                    LocalDate date = LocalDate.parse(parse(row[keyIndex]), FORMATTER);
                    String cellVal = parse(row[valIndex]).trim();
                    float close = NF_ENG.parse(cellVal).floatValue();
                    data.put(date, new Float[]{close});
                }
            }
        }

        return data;
    }

    public static SortedMap<LocalDate, Float[]> readFrom(final String dir, final int dateIndex, final int openIndex, final int closeIndex) throws Exception {

        List<File> files = readFilesFrom(dir);

        SortedMap<LocalDate, Float[]> data = new TreeMap<>(Comparator.reverseOrder());

        for(File file: files) {
            String line = "";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine();

                while ((line = br.readLine()) != null) {
                    // use comma as separator
                    String[] row = line.split(DELIMITER);
                    LocalDate date = LocalDate.parse(parse(row[dateIndex]), FORMATTER);
                    String cellVal = parse(row[openIndex]).trim();
                    float open = NF_ENG.parse(cellVal).floatValue();
                    cellVal = parse(row[closeIndex]).trim();
                    float close = NF_ENG.parse(cellVal).floatValue();
                    data.put(date,
                            new Float[]{open, close});
                }
            }
        }

        return data;
    }

    public static boolean writeTo(final String fileName, final List<List<String>> records) throws Exception {

        File crunchedDataFile = new File("resources/" + fileName + "-data.csv");
        boolean flag = crunchedDataFile.createNewFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(crunchedDataFile));

        for(List<String> row: records) {
            for(String cell: row){
                writer.append(cell).append(";");
            }
            writer.append("\n");
        }
        writer.close();

        return crunchedDataFile.length() > 0;
    }

    private static List<File> readFilesFrom(final String dir) throws IOException {
        return Files.walk(Paths.get("./resources/" + dir))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

    }

    private static String parse(final String cell) {
        return cell.substring(1);
    }
}
