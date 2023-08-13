import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QRFormatter {

    private int w=400,h=400;



    private String data;
    private String path;

    String charset="UTF-8";
    Map map;
    public QRFormatter(){

        setUpPath();
        setUpHash();

    }
    public QRFormatter(String data, int w, int h){

        this.data = data;
        this.w = w;
        this.h = h;
        setUpPath();
        setUpHash();
    }

    public QRFormatter(String data, int w, int h, String path){

        this.data = data;
        this.w = w;
        this.h = h;
        this.path = path;
        setUpHash();

    }

    private void setUpHash(){
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        //generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        this.map = hashMap;
    }

    private void setUpPath() {
        File temp = new File(System.getProperty("java.io.tmpdir"));
        String fileName = new SimpleDateFormat("yyyyMMddHHmm'.png'").format(new Date());
        this.path = temp.getPath() + "/" + fileName;
    }

    public String getPath() {return path;}

    public Path pathAsPath() {return Paths.get(path);}

    public String getData() {return data;}

    public int getW() {return w;}

    public int getH() { return h;}


    public void setData(String data){this.data = data;}

    public void setPath(String path) {this.path = path;}
}
