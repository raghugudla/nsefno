import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CSVReader {

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private final static String DELIMITER = ",";
    private final static NumberFormat NF = NumberFormat.getInstance(new Locale("sv", "SE"));

    public static void main(String[] args) throws Exception{

        processBankNiftyFiles(false);
        processNiftyFiles(false);

    }

    private static void processBankNiftyFiles(final boolean isExpiryOnly) throws Exception {

        String newFile = isExpiryOnly ?
                "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-expiry.csv"
                : "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-crunch.csv";
        File csvOutputFile = new File(newFile);
        PrintWriter pw = new PrintWriter(csvOutputFile);
        pw.println(header());

        String csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-2016.csv";
        process(csvFile, pw, isExpiryOnly);
        csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-2017.csv";
        process(csvFile, pw, isExpiryOnly);
        csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-2018.csv";
        process(csvFile, pw, isExpiryOnly);
        csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-2019.csv";
        process(csvFile, pw, isExpiryOnly);
        csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Bank Nifty\\data-2020.csv";
        process(csvFile, pw, isExpiryOnly);

        pw.flush();
        pw.close();
    }

    private static void processNiftyFiles(final boolean isExpiryOnly) throws Exception {

        String newFile = isExpiryOnly
                ? "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-expiry.csv"
                : "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-crunch.csv";
        File csvOutputFile = new File(newFile);
        PrintWriter pw = new PrintWriter(csvOutputFile);
        pw.println(header());

        String csvFile;
        if(!isExpiryOnly) {
            csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-2016.csv";
            process(csvFile, pw, isExpiryOnly);
            csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-2017.csv";
            process(csvFile, pw, isExpiryOnly);
            csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-2018.csv";
            process(csvFile, pw, isExpiryOnly);
        }
        csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-2019.csv";
        process(csvFile, pw, isExpiryOnly);
        csvFile = "C:\\Users\\ragu01\\Documents\\NSE\\Historical Data\\Nifty\\data-2020.csv";
        process(csvFile, pw, isExpiryOnly);

        pw.flush();
        pw.close();
    }

    private static void process(final String csvFile, final PrintWriter pw, final boolean isExpiryOnly) {
        String line;
        float prevClose = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] row = line.split(DELIMITER);
                LocalDate date = LocalDate.parse(parse(row[0]), FORMATTER);
                float close = Float.valueOf(parse(row[4]));

                StringBuilder sb = new StringBuilder();
                sb.append(row[0]).append(DELIMITER)
                        .append(row[1]).append(DELIMITER)
                        .append(row[2]).append(DELIMITER)
                        .append(row[3]).append(DELIMITER)
                        .append(row[4]).append(DELIMITER);
                additionalData(sb, row, prevClose);

                if(isExpiryOnly && DayOfWeek.THURSDAY.equals(date.getDayOfWeek())) {
                    pw.println(sb);
                } else {
                    pw.println(sb);
                }

                prevClose = close;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String parse(final String cell) {
        return cell.substring(1, cell.length()-1);
    }

    private static StringBuilder additionalData(final StringBuilder sb, final String[] row, final float prevClose) {
        float open = Float.valueOf(parse(row[1]));
        float high = Float.valueOf(parse(row[2]));
        float low = Float.valueOf(parse(row[3]));
        float close = Float.valueOf(parse(row[4]));

        float gap = (open-prevClose);
        float highDiff = (high-open);
        float lowDiff = (open-low);
        float gain = (close-prevClose);

        sb.append("\" \"").append(DELIMITER);
        sb.append("\""+ Math.round(gap) + "\"").append(DELIMITER);
        sb.append("\""+ Math.round(highDiff) + "\"").append(DELIMITER);
        sb.append("\""+ Math.round(lowDiff) + "\"").append(DELIMITER);
        sb.append("\""+ Math.round(gain) + "\"").append(DELIMITER);

        sb.append("\" \"").append(DELIMITER);
        sb.append("\""+ getPercentage(open, highDiff) + "\"").append(DELIMITER);
        sb.append("\""+ getPercentage(open, lowDiff) + "\"").append(DELIMITER);
        sb.append("\""+ getPercentage(open, gap) + "\"").append(DELIMITER);
        sb.append("\""+ getPercentage(open, gain) + "\"").append(DELIMITER);

        return sb;
    }

    private static BigDecimal getPercentage(final float value, final float gain) {
        return new BigDecimal(100*gain/value).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    private static String header() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"Date\"").append(DELIMITER)
                .append("\"Open\"").append(DELIMITER)
                .append("\"High\"").append(DELIMITER)
                .append("\"Low\"").append(DELIMITER)
                .append("\"Close\"").append(DELIMITER)
                .append("\" empty \"").append(DELIMITER)
                .append("\"Gap\"").append(DELIMITER)
                .append("\"H-O\"").append(DELIMITER)
                .append("\"O-L\"").append(DELIMITER)
                .append("\"Gain\"").append(DELIMITER)
                .append("\" empty \"").append(DELIMITER)
                .append("\"H-O %\"").append(DELIMITER)
                .append("\"O-L %\"").append(DELIMITER)
                .append("\"Gap %\"").append(DELIMITER)
                .append("\"Gain %\"");
        return sb.toString();
    }
}