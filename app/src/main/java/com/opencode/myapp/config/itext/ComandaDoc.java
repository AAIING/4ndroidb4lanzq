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
import com.opencode.myapp.Models.Productos;
import com.opencode.myapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComandaDoc {

    public static final BaseColor BLUE_BLACK = new BaseColor(0, 66, 165);
    private int anchopagina=100;
    private int anchocontenido=90;
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    //private Font BLUE_LINK = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.UNDERLINE, BaseColor.BLUE);
    //private Font BLUE_BLACK_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD, BLUE_BLACK);
    //private Font fontTituloParrafo = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD|Font.UNDERLINE, BaseColor.BLACK);
    private Font BLACK_BOLD = new Font(Font.FontFamily.HELVETICA,48, Font.BOLD, BaseColor.BLACK);
    //private Font fontTituloAdvertencia = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.BOLD|Font.UNDERLINE, BaseColor.RED);
    //private Font fontParrafoAdvertencia = new Font(Font.FontFamily.TIMES_ROMAN,10, Font.ITALIC, BaseColor.RED);
    private ParagraphBorder paragraphBorder;
    private String pathFile, fecha="",npedido="",tpedido="",empacador="",cliente="",armado="";
    private ArrayList<Pedidosd> listDetalle;

    public ComandaDoc(Context context) {
        this.context = context;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void openDocument(String fecha,
                             String npedido,
                             String tpedido,
                             String empacador,
                             String cliente,
                             String armado,
                             ArrayList<Pedidosd> listDetalle){
        /***/
        this.fecha = fecha;
        this.npedido=npedido;
        this.tpedido=tpedido;
        this.empacador=empacador;
        this.cliente=cliente;
        this.armado=armado;
        this.listDetalle = listDetalle;
        //
        String nomDoc = "Comanda-"+UUID.randomUUID().toString()+".pdf";
        //pdfFile = new File (context.getCacheDir(), nomDoc);
        //
/*
         File folder = new File(context.getFilesDir()+"/Download");
        if (!folder.exists()) {
            folder.mkdirs();
        }
*/
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
            docContent();
            document.close();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    void docContent(){
        //
        try {
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(anchopagina);
            PdfPCell cell, cellImg, cellSolicitud ;
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

            PdfPTable subTableimg = new PdfPTable(1);
            subTableimg.setWidthPercentage(90);
            subTableimg.addCell(cellImg);
            cell.addElement(subTableimg);

            table.addCell(cell);

            cell = new PdfPCell();
            cell.setColspan(3);

            PdfPTable subTable3;
            subTable3 = new PdfPTable(2);
            subTable3.setWidthPercentage(100);

            subTable3.addCell(createCellA("Fecha:"));
            subTable3.addCell(createCellA(fecha));
            subTable3.addCell(createCellA("N° Pedido:"));
            subTable3.addCell(createCellA(npedido));

            cell.addElement(subTable3);
            cell.setBorder(Rectangle.NO_BORDER);

            table.addCell(cell);

            document.add(table);

            subTable3 = new PdfPTable(3);
            subTable3.setWidthPercentage(100);

            subTable3.addCell(createCellA("Pedido:"));
            cell = new PdfPCell(new Phrase(tpedido, BLACK_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            subTable3.addCell(cell);
            //subTable3.addCell(createCellA(tpedido));
            subTable3.addCell(createCellA("Empacador:"));
            cell = new PdfPCell(new Phrase(empacador, BLACK_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            subTable3.addCell(cell);
            //subTable3.addCell(createCellA(empacador));
            subTable3.addCell(createCellA("Cliente:"));
            cell = new PdfPCell(new Phrase(cliente, BLACK_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            subTable3.addCell(cell);
            //subTable3.addCell(createCellA(cliente));
            subTable3.addCell(createCellA("Armado:"));
            cell = new PdfPCell(new Phrase(armado, BLACK_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            subTable3.addCell(cell);
            //subTable3.addCell(createCellA(armado));

            cell.addElement(subTable3);
            cell.setBorder(Rectangle.NO_BORDER);

            //table.addCell(cell);
            document.add(subTable3);

            /***/
            PdfPTable tabiadc ;
            tabiadc = new PdfPTable(7);
            tabiadc.setWidthPercentage(anchopagina);
            tabiadc.setSpacingBefore(20);

            cell= new PdfPCell(new Phrase("N°", BLACK_BOLD));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabiadc.addCell(cell);
            //
            cell= new PdfPCell(new Phrase("Nombre", BLACK_BOLD));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(4);
            tabiadc.addCell(cell);
            //
            cell= new PdfPCell(new Phrase("Cant", BLACK_BOLD));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabiadc.addCell(cell);
            //
            cell= new PdfPCell(new Phrase("Un", BLACK_BOLD));
            //cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabiadc.addCell(cell);
            document.add(tabiadc);

            int count=1;
            for(Pedidosd item: listDetalle){
                tabiadc = new PdfPTable(7);
                tabiadc.setWidthPercentage(anchopagina);
                tabiadc.setSpacingBefore(5);
                cell= new PdfPCell(new Phrase(count+"", BLACK_BOLD));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabiadc.addCell(cell);
                //
                cell= new PdfPCell(new Phrase(item.getDetalle(), BLACK_BOLD));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(4);
                tabiadc.addCell(cell);
                //
                cell= new PdfPCell(new Phrase(item.getCantidad()+"", BLACK_BOLD));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabiadc.addCell(cell);
                //
                cell= new PdfPCell(new Phrase(item.getUnidad(), BLACK_BOLD));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabiadc.addCell(cell);
                //
                count++;
                document.add(tabiadc);
            }

        } catch (Exception  e) {
            Log.e("titulo_documento",e.toString());
        }
    }

    public PdfPCell createCellA(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BLACK_BOLD));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
