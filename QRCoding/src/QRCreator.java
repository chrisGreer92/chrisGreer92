import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QRCreator {
    //static function that creates QR Code
    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException
    {
        //the BitMatrix class represents the 2D matrix of bits
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }

    public static void generateQR(QRFormatter format) throws WriterException, IOException, NotFoundException
    {
        generateQRcode(format.getData(),format.getPath(),format.charset,format.map,format.getH(),format.getW());
    }
}
