import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class IntradayOI {
    private final static String DELIMITER = "\t";
    private final static NumberFormat nf = NumberFormat.getIntegerInstance(Locale.UK);

    public static void main(String[] args) throws Exception{

        String niftyOI = "C:\\Users\\ragu01\\Documents\\NSE\\nifty-oi.csv";
        process(niftyOI);

    }

    private static void process(final String csvFile) {
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int calls=0, puts=0, nifty=0;
            int niftyPrevCalls=0, niftyPrevPuts=0;
            int bnfPrevCalls=0, bnfPrevPuts=0;
            while ((line = br.readLine()) != null) {

                String[] row = line.split(DELIMITER);

                if(row.length==1) {
                    int cdelta;
                    int pdelta;
                    if(nifty%2==0) {
                        pdelta = (puts-niftyPrevPuts);
                        cdelta = (calls-niftyPrevCalls);
                        niftyPrevCalls = calls;
                        niftyPrevPuts = puts;
                    } else {
                        pdelta = (puts-bnfPrevPuts);
                        cdelta = (calls-bnfPrevCalls);
                        bnfPrevCalls = calls;
                        bnfPrevPuts = puts;
                    }
                    System.out.println("puts: " + puts + ", calls: " + calls + ", pDelta: " + pdelta + ", cDelta: " + cdelta + ", delta: " + (pdelta-cdelta) + ", spot: " + row[0]);
                    calls=0; puts=0; ++nifty;
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
