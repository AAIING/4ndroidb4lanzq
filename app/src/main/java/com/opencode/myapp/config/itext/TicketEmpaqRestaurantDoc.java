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
import com.opencode.myapp.Models.Pedidos;
import com.opencode.myapp.Models.Pedidosd;
import com.opencode.myapp.Models.Presentaciones;
import com.opencode.myapp.Models.PresentacionesHasProductos;
import com.opencode.myapp.Models.Productos;
import com.opencode.myapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketEmpaqRestaurantDoc {

    public static final BaseColor BLUE_BLACK = new BaseColor(0, 66, 165);
    private int anchopagina=100;
    private int anchocontenido=90;
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Font BLACK_BOLD = new Font(Font.FontFamily.HELVETICA,38, Font.BOLD, BaseColor.BLACK);
    private Font TINY_BLACK_BOLD = new Font(Font.FontFamily.HELVETICA,14, Font.BOLD, BaseColor.BLACK);
    private Font MID_BLACK_BOLD = new Font(Font.FontFamily.HELVETICA,22, Font.BOLD, BaseColor.BLACK);
    private Font BLACK_BOLD_TITULOS = new Font(Font.FontFamily.HELVETICA,28, Font.BOLD, BaseColor.BLACK);
    private ParagraphBorder paragraphBorder;
    private String pathFile, npedido="",tpedido="",comuna="",nomcliente="",direccion="",
            condominio="", tiempoIngreso="", registro="", fechaelab="", categcliente="";
    private int cajas=1, bolsas=0;
    private boolean fragil = false, ticketIngreso = false;
    private List<Pedidosd> listPedidosd;
    private double pesototalneto;
    private List<Itemsid> listItems = new ArrayList<>();

    public TicketEmpaqRestaurantDoc(Context context) {
        this.context = context;
    }

    public String getPathFile() {
        return pathFile;
    }

    public List<Itemsid> getListItems(){
        return listItems;
    }

    public void openDocument(
            String categcliente,
            String fechaelab,
            double pesototalneto,
            List<Pedidosd> listPedidosd,
            //String tiempoIngreso,
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
        this.categcliente = categcliente;
        this.fechaelab = fechaelab;
        this.pesototalneto = pesototalneto;
        this.listPedidosd = listPedidosd;
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
        nomDoc = "Ticket_EmpaqueRestaurant_N°"+npedido+"-"+tiempoIngreso+".pdf";
        //File folder = new File (context.getCacheDir()+"");
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //pdfFile = new File (folder + "/" + nomDoc);
        pdfFile = new File (folder, nomDoc);
        pathFile = pdfFile.getPath();
        try{
            document = new Document(new Rectangle(700,  700), 1, 1, 1, 1);
            pdfWriter =  PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            paragraphBorder = new ParagraphBorder();
            pdfWriter.setPageEvent(paragraphBorder);
            document.open();
            /***/
            int count_caja =0;
            int count_bolsa =0;
            for(Pedidosd pedidosd: listPedidosd){
                count_caja++;
                String shortuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
                Itemsid itemsid = new Itemsid();
                itemsid.setPedidosregistro(Integer.parseInt(npedido));
                itemsid.setTipoitem("CAJA");
                itemsid.setCodigo(shortuid);
                listItems.add(itemsid);
                docContent(count_caja, bolsas, pedidosd, shortuid);
            }

            if(bolsas >= 1) {
                for (int i = 1; i <= bolsas; i++) {
                    Presentaciones pres = new Presentaciones();
                    pres.setNombre("BOLSA");
                    Pedidosd pedidosd = new Pedidosd();
                    pedidosd.setPresentaciones(pres);
                    pedidosd.setDetalle("BOLSA");
                    String shortuid = UUID.randomUUID().toString().replace("-","").substring(0,8);
                    Itemsid itemsid = new Itemsid();
                    itemsid.setPedidosregistro(Integer.parseInt(npedido));
                    itemsid.setTipoitem("BOLSA");
                    itemsid.setCodigo(shortuid);
                    listItems.add(itemsid);
                    count_bolsa++;
                    docContent(count_caja, count_bolsa, pedidosd, shortuid);
                }
            }
            //docContent();
            document.close();
        }catch (Exception e){
            Log.e("openDocument",e.toString());
        }
    }

    void docContent(int count_caja, int count_bolsa, Pedidosd pedidosd, String shortuid){
        //
        try {

            //SOLO SERIAN CAJAS POR EL TOTAL DE PRODUCTOS
            //for(Pedidosd pedidosd: listPedidosd) {

                PdfPTable table,  subTable3;
                table = new PdfPTable(4);
                table.setWidthPercentage(anchopagina);
                PdfPCell cell, cellImg, cellSolicitud;

                /***/
                subTable3 = new PdfPTable(8);
                subTable3.setWidthPercentage(anchopagina);
                subTable3.setWidthPercentage(100);

                //Productos item_prod = pedidosd.getProductos();
                Presentaciones item_pres = pedidosd.getPresentaciones();
                //subTable3.addCell(createCellA("Comuna:"));
                cell = new PdfPCell(new Phrase("CLIENTE", BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.LEFT | Rectangle.TOP );
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(nomcliente, BLACK_BOLD));
                cell.setColspan(4);
                cell.setBorder(Rectangle.TOP );
                //cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                //subTable3.addCell(createCellA("Condominio:"));
                cell = new PdfPCell(new Phrase("SUCURSAL", TINY_BLACK_BOLD));
                cell.setPaddingTop(65);
                cell.setBorder(Rectangle.TOP);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("SUCXXXXX", TINY_BLACK_BOLD));
                cell.setPaddingTop(65);
                cell.setBorder(Rectangle.TOP | Rectangle.RIGHT );
                subTable3.addCell(cell);

                //
                //subTable3.addCell(createCellA("Comuna:"));
                cell = new PdfPCell(new Phrase("DIRECCIÓN", TINY_BLACK_BOLD));
                cell.setPaddingLeft(20);
                cell.setColspan(2);
                cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(direccion, TINY_BLACK_BOLD));
                cell.setColspan(4);
                //cell.setBorder(Rectangle.BOTTOM);
                cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                //subTable3.addCell(createCellA("Condominio:"));
                cell = new PdfPCell(new Phrase("COMUNA", TINY_BLACK_BOLD));
                cell.setBorder(Rectangle.BOTTOM);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(comuna, TINY_BLACK_BOLD));
                cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
                subTable3.addCell(cell);

                //***/
                cell = new PdfPCell(new Phrase(pedidosd.Detalle, BLACK_BOLD));
                cell.setColspan(6);
                cell.setBorder(Rectangle.TOP | Rectangle.LEFT );
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("REFRIGERADO", TINY_BLACK_BOLD));
                cell.setPaddingTop(55);
                cell.setColspan(2);
                cell.setBorder(Rectangle.TOP | Rectangle.RIGHT );
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(item_pres.getNombre(), TINY_BLACK_BOLD));
                cell.setPaddingLeft(20);
                cell.setColspan(6);
                cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT );
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(categcliente, TINY_BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT );
                subTable3.addCell(cell);

                //***/
                cell = new PdfPCell(new Phrase("PESO NETO", BLACK_BOLD));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingTop(5);
                cell.setBorder(Rectangle.TOP | Rectangle.LEFT );
                cell.setColspan(3);

                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("CAJA N°", BLACK_BOLD));
                cell.setColspan(3);
                cell.setBorder(Rectangle.TOP );
                //cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase( count_caja+" DE "+listPedidosd.size(), BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.TOP | Rectangle.RIGHT );
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(pesototalneto+" KG", BLACK_BOLD));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT );
                cell.setColspan(3);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("BOLSA", BLACK_BOLD));
                cell.setColspan(3);
                cell.setBorder(Rectangle.BOTTOM );
                //cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(count_bolsa+" DE "+bolsas, BLACK_BOLD));
                cell.setColspan(2);
                cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT );
                subTable3.addCell(cell);

                //***/
                //subTable3.addCell(createCellA("Comuna:"));
                cell = new PdfPCell(new Phrase("FECHA ELABORACIÓN", MID_BLACK_BOLD));
                cell.setPaddingLeft(20);
                cell.setPaddingTop(5);
                cell.setColspan(3);
                cell.setBorder(Rectangle.TOP | Rectangle.LEFT );
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("CONSUMIR PREFERENTEMENTE", MID_BLACK_BOLD));
                cell.setColspan(5);
                cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
                //cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase(fechaelab, TINY_BLACK_BOLD));
                //cell.setPaddingLeft(20);
                cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT );
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(3);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("HASTA 5 DIAS DESPÚES DE LA FECHA ELABORACIÓN.", TINY_BLACK_BOLD));
                cell.setColspan(5);
                cell.setBorder(Rectangle.BOTTOM| Rectangle.RIGHT);
                //cell.setBorder(Rectangle.NO_BORDER);
                subTable3.addCell(cell);

                Bitmap bitmapqr = createBitmap(shortuid);
                ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                bitmapqr.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                Image imageQr = Image.getInstance(stream1.toByteArray());
                imageQr.setAbsolutePosition(600, 140);
                imageQr.scaleAbsolute(140f, 140f);
                document.add(imageQr);
                //cell = new PdfPCell(imageQr, true);
                cell = new PdfPCell(new Phrase(" ", BLACK_BOLD));
                cell.setPaddingTop(20);
                cell.setPaddingBottom(90);
                cell.setColspan(8);
                subTable3.addCell(cell);

                //FALTA ETIQUETA FRAGIL
                if(fragil) {
                    //LOGO FRAGIL
                    Drawable drawableLogo2 = context.getResources().getDrawable(R.drawable.fragil_logo);
                    Bitmap bitDwLogo2 = ((BitmapDrawable) drawableLogo2).getBitmap();
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bitDwLogo2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    Image imageLogo2 = Image.getInstance(stream2.toByteArray());
                    /**POSICION IMAGEN*/
                    imageLogo2.setAbsolutePosition(300, 135);
                    /**TAMAÑO IMAGEN*/
                    imageLogo2.scaleAbsolute(100f, 100f);
                    document.add(imageLogo2);
                }

                cell = new PdfPCell(new Phrase("ESTE PRODUCTO PASA POR UN RIGUROSO PROCESO DE EXTRACCIÓN DE ESPINAS, SIN EMBARGO ALGUNAS PUEDEN SER ENCONTRADAS, PRODUCTO MANTENER REFRIGERADO ENTRE -4 Y 2°C", TINY_BLACK_BOLD));
                cell.setColspan(8);
                subTable3.addCell(cell);

                cell = new PdfPCell(new Phrase("ELABORADO BAJO RESOLUCIÓN SANITARIA 19136146 PRODUCTO CHILENO WWW.ELMAR.CL", TINY_BLACK_BOLD));
                cell.setColspan(8);
                subTable3.addCell(cell);

                document.add(subTable3);
                document.newPage();

                //}
        } catch (Exception  e) {
            Log.e("titulo_documento",e.toString());
        }
    }

    public PdfPCell createCellA(String content) {
        PdfPCell cell = new PdfPCell(new Phrase(content, BLACK_BOLD_TITULOS));
        //cell.setBorder(Rectangle.NO_BORDER);
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
