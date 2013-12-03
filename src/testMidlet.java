import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import net.sf.jazzlib.GZIPInputStream;


public class testMidlet extends MIDlet {

	public testMidlet() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		try{
		String HTTP_URL = "http://localhost:8181/openmrs/moduleServlet/xforms/xformDownload?target=xforms";
		
		System.out.println(HTTP_URL);
		HttpConnection httpCon = (HttpConnection) Connector.open(HTTP_URL, Connector.READ_WRITE);

        
		httpCon.setRequestMethod(HttpConnection.POST);
		//httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		//httpCon.setRequestProperty("Accept", "application/octet-stream");
//		/httpCon.setRequestProperty("Content-Type",	"application/octet-stream");
		//((HttpConnection) con).setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.0");
		//((HttpConnection) con).setRequestProperty("Content-Language", "en-US");

		DataOutputStream dos = httpCon.openDataOutputStream();
		//OutputStreamWriter osw = new OutputStreamWriter(httpCon.getOutputStream());
		dos.writeUTF("admin");//openmrs username
		dos.writeUTF("Admin123");//openmrs password
		dos.writeUTF("xforms.xformSerializer");
		dos.writeUTF("en");
		dos.writeByte(3);//ACTION_DOWNLOAD_FORMS = 3;
			
		dos.flush();

		int status = httpCon.getResponseCode();
		if (status == HttpConnection.HTTP_OK){

			DataInputStream dis = new DataInputStream(httpCon.openDataInputStream());
			
			GZIPInputStream gz = new GZIPInputStream(dis);
			//ZInputStream gz = new ZInputStream(downloadedStream);
			dis = new DataInputStream(gz);

			byte responsestatus = dis.readByte();
			
			byte formslistsize = dis.readByte();
			
			for (int i = 0; i < formslistsize; i++) {
				String xml = dis.readUTF();
				xml.getBytes();
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
