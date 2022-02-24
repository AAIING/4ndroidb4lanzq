package WebServices;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.opencode.myapp.Models.Capturas;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class wsSincroniza {

    private Context context;

    private static final String PREFER_NAME = "xxxxxxxxxxxx";
    private SharedPreferences sharedPreferences;
    private final String SOAP_ACTION = "http://tempuri.org/xxxxxxxxxx";
    private final String OPERATION_NAME = "xxxxxxxxxxxxxxxxxxx";
    private  final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ADDRESS = "http://wsxxxxxxxxxxxxx.cl/WebServicexxxx.asmx";


    public wsSincroniza(Context context) {
        this.context = context;
    }

    public String Subir(String rut,String nombre, String empresa,String cuartel,String telefono,String nosinc,String producto,String variedad,Double cantidad) {

       Log.e("sincronizando NOSINC", nosinc);

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,OPERATION_NAME);

        PropertyInfo pi=new PropertyInfo();

        pi.setName("rut");
        pi.setValue(rut.trim());
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("nombre");
        pi.setValue(nombre);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("empresa");
        pi.setValue(empresa);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("cuartel");
        pi.setValue(cuartel);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("telefono");
        pi.setValue(telefono);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("nosinc");
        pi.setValue(nosinc);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("producto");
        pi.setValue(producto);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("variedad");
        pi.setValue(variedad);
        pi.setType(String.class);
        request.addProperty(pi);

        pi=new PropertyInfo();
        pi.setName("cantidad");
        pi.setValue(cantidad);
        pi.setType(Double.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        //envelope.implicitTypes = true;
        envelope.encodingStyle = SoapSerializationEnvelope.XSD;
        MarshalDouble md = new MarshalDouble();
        md.register(envelope);

        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        SoapObject response=null;

        try
        {
//            Log.e("sincroniza try rut", rut);
//            Log.e("sincroniza try cantidad", String.valueOf(cantidad));

            httpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive results = (SoapPrimitive)envelope.getResponse();
            String res=results.toString();
//            Log.e("RPPTA wsSincroniza", res);

            if(res.equals("ok") )
            {return "1";}
            else
            {return "0";}
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

    }

    public class MarshalDouble implements Marshal
    {

        public Object readInstance(XmlPullParser parser, String namespace, String name,
                                   PropertyInfo expected) throws IOException, XmlPullParserException {

            return Double.parseDouble(parser.nextText());
        }


        public void register(SoapSerializationEnvelope cm) {
            cm.addMapping(cm.xsd, "double", Double.class, this);

        }


        public void writeInstance(XmlSerializer writer, Object obj) throws IOException {
            writer.text(obj.toString());
        }

    }

}