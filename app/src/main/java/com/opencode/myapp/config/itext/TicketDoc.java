package com.opencode.myapp.config.itext;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencode.myapp.Models.Itemsid;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Presentaciones;
import com.opencode.myapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDoc {

    public static final BaseColor BLUE_BLACK = new BaseColor(0, 66, 165);
    private int anchopagina=100;
    private int anchocontenido=90;
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Font BLACK_BOLD = new Font(Font.FontFamily.HELVETICA,38, Font.BOLD, BaseColor.BLACK);
    private Font BLACK_BOLD_TITULOS = new Font(Font.FontFamily.HELVETICA,28, Font.BOLD, BaseColor.BLACK);
    private ParagraphBorder paragraphBorder;
    private String pathFile, npedido="",tpedido="",comuna="",nomcliente="",direccion="", condominio="", tiempoIngreso="", codigouid="", uidentrada="";
    private int cajas=1, bolsas=0;
    private boolean fragil = false, ticketIngreso = false;
    private List<Itemsid> listItems = new ArrayList<>();

    public TicketDoc(Context context) {
        this.context = context;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void openDocument(
                        String uidentrada,
                        String codigouid,
                        String tiempoIngreso,
                        boolean fragil,
                        int cajas,
                        int bolsas,
                        String npedido,
                        String tpedido,
                        String comuna,
                        String condominio,
                        String nomcliente,
                        String direccion){
        this.uidentrada = uidentrada;
        this.codigouid = codigouid;
        this.tiempoIngreso = tiempoIngreso;
        this.fragil = fragil;
        this.cajas=cajas;
        this.bolsas=bolsas;
        this.npedido=npedido;
        this.tpedido=tpedido;
        this.comuna=comuna;
        this.condominio=condominio;
        this.nomcliente=nomcliente;
        this.direccion=direccion;
        /***/
        String nomDoc;
        if(!tiempoIngreso.equals("")) {
             nomDoc = "Ticket_Ingreso_PedidoN°"+npedido+".pdf";
        }else{
            nomDoc = "Ticket_PedidoN°" +npedido+ ".pdf";
        }
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //pdfFile = new File (folder + "/" + nomDoc);
        pdfFile = new File (folder, nomDoc);
        pathFile = pdfFile.getPath();
        try{
            document = new Document(new Rectangle(700,  700), 5, 5, 5, 5);
            pdfWriter =  PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            paragraphBorder = new ParagraphBorder();
            pdfWriter.setPageEvent(paragraphBorder);
            document.open();
            //CODIGO UUID PARA TICKET INGRESO PRIMERO IMPRIME, AL CERRAR PEDIDO SE ENVIA CODIGO DE TICKET INGRESO
            if(uidentrada.isEmpty()) {
                /*
                Itemsid itemsid = new Itemsid();
                itemsid.setCodigo(codigouid);
                itemsid.setTipoitem("CAJA");
                itemsid.setPedidosregistro(Integer.parseInt(npedido));
                listItems.add(itemsid);
                */
            }else{
                //TICKET INGRESO SOLO PARA IMPRIMIR
                for (int i = 1; i == 1; i++) {
                    docContent(i,cajas, 0);
                    document.newPage();
                }
            }

            /**TICKET DE SALIDA O CIERRE*/
            if(cajas > 1) {
                for (int i = 1; i <= cajas-1; i++) {
                    docContent(i,cajas, 2);
                    document.newPage();
                }
            }

            if(bolsas >= 1) {
                for (int i = 1; i <= bolsas; i++) {
                    docContent(i,bolsas, 1);
                    document.newPage();
                }
            }

            document.close();

        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    public List<Itemsid> getListItems(){
        return listItems;
    }

    void docContent(int index, int totalcajobol, int tipocajobol){
        //
        try {
                PdfPTable table, subTable3;

                table = new PdfPTable(4);
                table.setWidthPercentage(anchopagina);
                PdfPCell cell, cellImg, cellSolicitud;
                //LOGO DOC EMPRESA
                Drawable drawableLogo = context.getResources().getDrawable(R.drawable.fishbox_logo);
                Bitmap bitDwLogo = ((BitmapDrawable) drawableLogo).getBitmap();
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitDwLogo.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                Image imageLogo = Image.getInstance(stream1.toByteArray());

                cell = new PdfPCell();
                cell.setBorder(Rectangle.LEFT | Rectangle.TOP);

                cellImg = new PdfPCell(imageLogo, true);
                cellImg.setBorder(Rectangle.NO_BORDER);

                PdfPTable subTableimg;
                subTableimg = new PdfPTable(1);
                subTableimg.setWidthPercentage(90);
                subTableimg.addCell(cellImg);
                cell.addElement(subTableimg);

                table.addCell(cell);

                cell = new PdfPCell();
                cell.setColspan(3);

                subTable3 = new PdfPTable(2); //PdfPTable
                subTable3.setWidthPercentage(anchopagina);

                subTable3.addCell(createCellA("N° Pedido:"));
                subTable3.addCell(createCellA(npedido));
                subTable3.addCell(createCellA("Pedido:"));
                subTable3.addCell(createCellA(tpedido));

                if(tipocajobol == 2) {
                    subTable3.addCell(createCellA("Caja:"));
                    subTable3.addCell(createCellA(index + "/" + totalcajobol));
                } else if(tipocajobol == 1) {
                    subTable3.addCell(createCellA("Bolsa:"));
                    subTable3.addCell(createCellA(index + "/" + totalcajobol));
                } else if(tipocajobol == 0) {
                    subTable3.addCell(createCellA("Caja:"));
                    subTable3.addCell(createCellA("1/1"));
                }

                cell.addElement(subTable3);
                cell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
                table.addCell(cell);

                document.add(table);

                /***/
                subTable3 = new PdfPTable(3);
                subTable3.setWidthPercentage(anchopagina);
                subTable3.setWidthPercentage(100);

                cell = new PdfPCell(new Phrase("Comuna:", BLACK_BOLD_TITULOS));
                cell.setBorder(Rectangle.LEFT);
                cell.setPaddingBottom(5);
                //subTable3.addCell(createCellA("Comuna:"));
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(comuna, BLACK_BOLD));
                cell.setColspan(2);
                cell.setPaddingBottom(5);
                cell.setBorder(Rectangle.RIGHT);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("Condominio:", BLACK_BOLD_TITULOS));
                cell.setBorder(Rectangle.LEFT);
                cell.setPaddingBottom(25);
                //subTable3.addCell(createCellA("Condominio:"));
                subTable3.addCell(cell);

                //COND SANTA TERESITA DE LISEUX
                cell = new PdfPCell(new Phrase(condominio, BLACK_BOLD));
                cell.setColspan(2);
                cell.setPaddingBottom(25);
                cell.setBorder(Rectangle.RIGHT);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("Dirección:", BLACK_BOLD_TITULOS));
                cell.setBorder(Rectangle.LEFT);
                cell.setPaddingBottom(20);
                //subTable3.addCell(createCellA("Dirección:"));
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(direccion, BLACK_BOLD));
                cell.setColspan(2);
                cell.setPaddingBottom(20);
                cell.setBorder(Rectangle.RIGHT);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("Cliente:", BLACK_BOLD_TITULOS));
                cell.setBorder(Rectangle.LEFT);
                cell.setPaddingBottom(50);
                //subTable3.addCell(createCellA("Cliente:"));
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(nomcliente, BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.RIGHT);
                cell.setPaddingBottom(20);
                subTable3.addCell(cell);

                /***/
                cell = new PdfPCell(new Phrase(" ", BLACK_BOLD_TITULOS));
                cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                //subTable3.addCell(createCellA("Cliente:"));
                cell.setPaddingTop(90);
                cell.setPaddingBottom(90);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(" ", BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                cell.setPaddingTop(90);
                cell.setPaddingBottom(90);
                subTable3.addCell(cell);

                document.add(subTable3);

                String shortuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
                Itemsid itemsid = new Itemsid();

                if(tipocajobol == 2) {
                    //subTable3.addCell(createCellA("Caja:"));
                    //subTable3.addCell(createCellA(index + "/" + totalcajobol));
                    itemsid.setCodigo(shortuid);
                    itemsid.setTipoitem("CAJA");
                    itemsid.setPedidosregistro(Integer.parseInt(npedido));
                    listItems.add(itemsid);
                } else if(tipocajobol == 1){
                    itemsid.setCodigo(shortuid);
                    itemsid.setTipoitem("BOLSA");
                    itemsid.setPedidosregistro(Integer.parseInt(npedido));
                    listItems.add(itemsid);
                    //subTable3.addCell(createCellA("Bolsa:"));
                    //subTable3.addCell(createCellA(index + "/" + totalcajobol));
                }

                Bitmap bitmapqr = null;
                //SI CODIGOUID = VACIO.. VIENE CON UID ENTRADA PARA SOLO IMPRIMIR QR
                if(codigouid.isEmpty()) {
                    bitmapqr = createBitmap(uidentrada);
                }else{
                    bitmapqr = createBitmap(shortuid);
                }

                ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
                bitmapqr.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                Image imageQr = Image.getInstance(stream3.toByteArray());
                imageQr.setAbsolutePosition(350, 80);
/*
                subTable3 = new PdfPTable(2);
                subTable3.setWidthPercentage(anchopagina);
                cell = new PdfPCell(imageQr, true);
                cell.setBorder(Rectangle.LEFT);
                //cell.setPaddingBottom(5);
                //subTable3.addCell(createCellA("Comuna:"));
                subTable3.addCell(cell);
*/

                document.add(imageQr);
                //document.add(subTable3);

                if(fragil) {
                    //LOGO FRAGIL
                    Drawable drawableLogo2 = context.getResources().getDrawable(R.drawable.fragil_logo);
                    Bitmap bitDwLogo2 = ((BitmapDrawable) drawableLogo2).getBitmap();
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bitDwLogo2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    Image imageLogo2 = Image.getInstance(stream2.toByteArray());
                    /**POSICION IMAGEN*/
                    imageLogo2.setAbsolutePosition(150, 80);
                    /**TAMAÑO IMAGEN*/
                    imageLogo2.scaleAbsolute(145f, 145f);
                    document.add(imageLogo2);
                }

                //document.newPage();
        } catch(Exception e) {
            Log.e("titulo_documento",e.toString());
        }
    }

    public PdfPCell createCellA(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BLACK_BOLD_TITULOS));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Bitmap createBitmap(String text){
        BitMatrix result;
        try{
            result = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 150, 150, null);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width*height];
        for(int x=0; x<height; x++){
            int offset = x* width;
            for(int k=0; k<width; k++){
                pixels[offset+k] = result.get(k, x) ? BLACK : WHITE;
            }
        }
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myBitmap.setPixels(pixels,0,width,0,0,width,height);
        return myBitmap;
    }

}
