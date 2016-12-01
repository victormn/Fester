package com.example.victor.fester.Toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class QRGenerator extends AppCompatActivity {

    public static void generateQR(EditText username, Context context) {
        Bitmap bitmap = null;
        byte[] photo = new byte[0];

        // Generating QRCode from username
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(username.getText().toString(), BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        if (bitmap == null) System.out.println("Erro ao gerar o QR Code");
        else photo = BitmapManager.bitmapToByteArray(bitmap);

        // Recebendo usuario do BD
        UserDBAdapter dbAdapter = new UserDBAdapter(context);
        dbAdapter.open();
        User user = dbAdapter.getUser();
        dbAdapter.close();

        user.setQr(photo);
        user.toDataBase(context);

    }
}
