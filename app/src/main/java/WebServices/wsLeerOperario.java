package WebServices;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class wsLeerOperario {

    private Context context;

    private static final String PREFER_NAME = "xxxxxxxxxx";
    private SharedPreferences sharedPreferences;
    private final String SOAP_ACTION = "http://tempuri.org/xxxxxxxx";
    private final String OPERATION_NAME = "xxxxxxx";
    private final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";
    private final String SOAP_ADDRESS = "http://wsxxxxx.cl/WebServicexxxxx.asmx";


    public wsLeerOperario(Context context) {
        this.context = context;
    }

    public String Leer(String rut) {
        Log.e("rut-esc", rut);
        Log.e("rut-barra1", rut);
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("rut");
        pi.setValue(rut);
        pi.setType(String.class);
        request.addProperty(pi);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);
        Log.e("Codigo-barra2", rut);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        try {

            Log.e("Codigo-barra3", rut);

            httpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive respuesta = (SoapPrimitive)envelope.getResponse();
            Log.e("respta wsLeerOperario", respuesta.toString());
            return respuesta.toString();

        } catch (Exception errores) {
            Log.e("Error-wsLeerOperario", String.valueOf(errores));
            return "-1";
        }
    }
}