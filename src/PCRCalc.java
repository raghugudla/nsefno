import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.Locale;

public class PCRCalc {
    private final static String DELIMITER = "\t";
    private final static NumberFormat nf = NumberFormat.getIntegerInstance(Locale.UK);

    public static void main(String[] args) throws Exception{

        String niftyOI = "C:\\Users\\ragu01\\Documents\\NSE\\nifty-oi.csv";
        processOI(niftyOI);
        processCOI(niftyOI);
    }

    private static void processOI(final String csvFile) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int calls=0, puts=0;
            while ((line = br.readLine()) != null) {

                String[] row = line.split(DELIMITER);

                if(row.length==1) {
                    System.out.println("puts: " + puts + ", calls: " + calls +", PCR: " + ((float)puts/calls));
                    calls=0; puts=0;
                    continue;
                }

                if(!row[1].contains("-"))
                    calls += nf.parse(row[1]).intValue();
//                System.out.println("call: " + row[1]);
                    if(!row[row.length-2].contains("-"))
                puts += nf.parse(row[row.length-2]).intValue();
//                System.out.println("put: " + row[row.length-2]);

            }
//            System.out.println("pcr: " + ((float)puts/calls));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void processCOI(final String csvFile) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int calls=0, puts=0;
            while ((line = br.readLine()) != null) {

                String[] row = line.split(DELIMITER);

                if(row.length==1) {
                    System.out.println("puts: " + puts + ", calls: " + calls + ", diff: " + (puts-calls));
                    calls=0; puts=0;
                    continue;
                }


                if(!row[2].contains("-"))
                    calls += nf.parse(row[2]).intValue();
//                System.out.println("call: " + row[1]);
                if(!row[row.length-3].contains("-"))
                    puts += nf.parse(row[row.length-3]).intValue();
//                System.out.println("put: " + row[row.length-2]);

            }
//            System.out.println("pcr: " + ((float)puts/calls));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
