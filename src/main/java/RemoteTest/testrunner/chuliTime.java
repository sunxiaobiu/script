package RemoteTest.testrunner;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.IncrementHashMap;
import util.Regex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class chuliTime {

    public static void main(String[] args) throws IOException {
        Map<String, Integer> exceptionMap = new HashMap();

        InputStream is = new FileInputStream(new File("/Users/xsun0035/Desktop/CrowdTest/test_runner.xlsx"));
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(1000)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(15000)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);            // InputStream or File for XLSX file (required)

        for (Sheet sheet : workbook){
            System.out.println(sheet.getSheetName());
            for(int i=2; i<=sheet.getLastRowNum(); i++){
                Row currentRow = sheet.getRow(i);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(currentRow.getCell(5).getStringCellValue(), formatter);
                System.out.println(date); // 2010-01-02

                Row lastRow = sheet.getRow(i-1);
                LocalDate lastDate = LocalDate.parse(lastRow.getCell(5).getStringCellValue(), formatter);
                System.out.println(lastDate); // 2010-01-02

                Duration duration = Duration.between(lastDate, date);
                if(Integer.valueOf(duration.toString()) > 1){
                    System.out.println("hhhh");
                }

            }
        }

    }
}
