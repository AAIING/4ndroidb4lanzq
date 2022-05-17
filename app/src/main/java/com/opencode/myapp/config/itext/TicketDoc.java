package com.opencode.myapp.config.itext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

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
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class TicketDoc {

    public static final BaseColor BLUE_BLACK = new BaseColor(0, 66, 165);
    private int anchopagina=100;
    private int anchocontenido=90;
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Font BLACK_BOLD = new Font(Font.FontFamily.HELVETICA,48, Font.BOLD, BaseColor.BLACK);
    private Font BLACK_BOLD_TITULOS = new Font(Font.FontFamily.HELVETICA,44, Font.BOLD, BaseColor.BLACK);
    private ParagraphBorder paragraphBorder;
    private String pathFile, npedido="",tpedido="",comuna="",nomcliente="",direccion="", condominio="", tiempoIngreso="";
    private int cajas=1, bolsas=0;
    private boolean fragil = false, ticketIngreso = false;

    public TicketDoc(Context context) {
        this.context = context;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void openDocument(
            String tiempoIngreso,
                            //boolean ticketIngreso,
                            boolean fragil,
                            int cajas,
                             int bolsas,
                             String npedido,
                             String tpedido,
                             String comuna,
                             String condominio,
                             String nomcliente,
                             String direccion){

        this.tiempoIngreso = tiempoIngreso;
        //this.ticketIngreso = ticketIngreso;
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
             nomDoc = "TicketIngreso-PedidoN°-"+npedido+"-"+tiempoIngreso+".pdf";
        }else{
            nomDoc = "Ticket-" + UUID.randomUUID().toString() + ".pdf";
        }
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //pdfFile = new File (folder + "/" + nomDoc);
        pdfFile = new File (folder, nomDoc);
        pathFile = pdfFile.getPath();
        try{
            document = new Document(new Rectangle(850,  650), 1, 1, 1, 1);
            pdfWriter =  PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            paragraphBorder = new ParagraphBorder();
            pdfWriter.setPageEvent(paragraphBorder);
            document.open();
            /***/
            if(cajas >= 1) {
                for (int i = 1; i <= cajas; i++) {
                    docContent(i,cajas, 2);
                }
            }

            if(bolsas >= 1) {
                for (int i = 1; i <= bolsas; i++) {
                    docContent(i,bolsas, 1);
                }
            }

            document.close();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }


    void docContent(int index, int totalcajobol, int tipocajobol){
        //
        try {
                PdfPTable table;
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
                cell.setBorder(Rectangle.NO_BORDER);

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

                PdfPTable subTable3 = new PdfPTable(2);
                subTable3.setWidthPercentage(anchopagina);

                subTable3.addCell(createCellA("N° Pedido:"));
                subTable3.addCell(createCellA(npedido));
                subTable3.addCell(createCellA("Pedido:"));
                subTable3.addCell(createCellA(tpedido));

                if(tipocajobol > 1) {
                    subTable3.addCell(createCellA("Caja:"));
                    subTable3.addCell(createCellA(index + "/" + totalcajobol));
                } else {
                    subTable3.addCell(createCellA("Bolsa:"));
                    subTable3.addCell(createCellA(index + "/" + totalcajobol));
                }

                cell.addElement(subTable3);
                cell.setBorder(Rectangle.NO_BORDER);

                table.addCell(cell);

                document.add(table);

                /***/
                subTable3 = new PdfPTable(3);
                subTable3.setWidthPercentage(anchopagina);
                subTable3.setWidthPercentage(100);

                subTable3.addCell(createCellA("Comuna:"));
                cell = new PdfPCell(new Phrase(comuna, BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                subTable3.addCell(createCellA("Condominio:"));
                cell = new PdfPCell(new Phrase(condominio, BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                subTable3.addCell(createCellA("Dirección:"));
                cell = new PdfPCell(new Phrase(direccion, BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                subTable3.addCell(createCellA("Cliente:"));
                cell = new PdfPCell(new Phrase(nomcliente, BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                document.add(subTable3);

                if(fragil) {
                    //LOGO FRAGIL
                    Drawable drawableLogo2 = context.getResources().getDrawable(R.drawable.fragil_logo);
                    Bitmap bitDwLogo2 = ((BitmapDrawable) drawableLogo2).getBitmap();
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bitDwLogo2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    Image imageLogo2 = Image.getInstance(stream2.toByteArray());
                    /**POSICION IMAGEN*/
                    imageLogo2.setAbsolutePosition(650, 150);
                    /**TAMAÑO IMAGEN*/
                    imageLogo2.scaleAbsolute(145f, 145f);
                    document.add(imageLogo2);
                }

                document.newPage();

        } catch (Exception  e) {
            Log.e("titulo_documento",e.toString());
        }
    }

    public PdfPCell createCellA(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BLACK_BOLD_TITULOS));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

}
