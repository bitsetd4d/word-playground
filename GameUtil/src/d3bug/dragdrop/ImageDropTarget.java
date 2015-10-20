
package d3bug.dragdrop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import d3bug.graphics.Imaging;

public class ImageDropTarget {

	private JLabel label;
	private boolean supported;
	private byte[] imageBytes;
	private BufferedImage bufferedImage;
	
	private static int MAX_WIDTH = 137;
	private static int MAX_HEIGHT = 176;
	
	private final static DataFlavor URL_FLAVOR = DataFlavor.stringFlavor;
	//private final static DataFlavor URL_FLAVOR = new DataFlavor("text/uri-list","Unicode String");

	public ImageDropTarget(JLabel label) {
		super();
		this.label = label;
		try {
			setupDropTarget();
		} catch (TooManyListenersException e) {
			ahPickles(e);
		}
	}
	
	public void pasteFromClipboard() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		for (DataFlavor f : clipboard.getAvailableDataFlavors()) {
			System.out.println(">>>>>> "+f);
		}
		Transferable tf = clipboard.getContents(null);
		if (tf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				String address = (String)tf.getTransferData(DataFlavor.stringFlavor);
				loadImageFromAddress(address);
				setLabelThumbnailFromBytes();
				setBufferedImageFromBytes();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Doh@");
		}
		
	}
	
	public byte[] getImageBytes() {
		return imageBytes; 
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	private void setupDropTarget() throws TooManyListenersException {
		label.setDropTarget(new DropTarget());
		label.getDropTarget().addDropTargetListener(new DropTargetListener() {

			public void dragEnter(DropTargetDragEvent ev) {
				debugLog(ev);
				supported = ev.isDataFlavorSupported(URL_FLAVOR);
				System.out.println("Supported? "+supported);	
				if (!supported) {
					ev.rejectDrag();
				} else {
					ev.acceptDrag(DnDConstants.ACTION_COPY);
				}
			}

			public void dragExit(DropTargetEvent ev) {}
			public void dragOver(DropTargetDragEvent ev) {}

			public void drop(DropTargetDropEvent ev) {
				if (!supported) return;
				ev.acceptDrop(DnDConstants.ACTION_COPY);
				System.out.println("In drop() - " + ev);
				Transferable transferable = ev.getTransferable();
				Class cls = URL_FLAVOR.getDefaultRepresentationClass();
				System.out.println("Drop class is "+cls);
				try {
					String address = (String) transferable.getTransferData(URL_FLAVOR);
					loadImageFromAddress(address);
					setLabelThumbnailFromBytes();
					setBufferedImageFromBytes();
				} catch (UnsupportedFlavorException e) {
					ahPickles(e);
				} catch (IOException e) {
					ahPickles(e);
				}
			}

			public void dropActionChanged(DropTargetDragEvent ev) {
				System.out.println("In dropActionChanged() - " + ev);
			}
		});
	}
	
	protected void loadImageFromAddress(String address) throws IOException {
		try {
			URL urlLoader = new URL(address);
			URLConnection connection =  urlLoader.openConnection();
			String type = connection.getContentType();
			if (type.indexOf('/') >=0) {
				type = type.split("/")[1];
			}
			System.out.println("Type is "+type);
			// Reject if not jpeg or gif or png
			InputStream is = new BufferedInputStream(connection.getInputStream());
			ArrayList<Byte> byteArray = new ArrayList<Byte>();
			while (true) {
				int i = is.read();
				if (i == -1) break;
				byte b = (byte)i;
				byteArray.add(b);
			}
			
			int len = byteArray.size();
			byte[] bytes = new byte[len];
			for (int i=0; i<len; i++) {
				bytes[i] = byteArray.get(i);
			}
			
			imageBytes = bytes;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}

	protected void setLabelThumbnailFromBytes() {
		//Image rawImage = Toolkit.getDefaultToolkit().createImage(getImageBytes());
		ImageIcon icon = new ImageIcon(getImageBytes());
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		System.out.println("Image dimensions, width: "+w+", height: "+h);
		ImageIcon newIcon = Imaging.newImagePreserveRatio(icon, label.getWidth(), label.getHeight(), false);
		label.setIcon(newIcon);
	}
	
	protected void setBufferedImageFromBytes() {
		InputStream is = new ByteArrayInputStream(getImageBytes());
		try {
			BufferedImage image = ImageIO.read(is);
			bufferedImage = Imaging.newBufferredImagePreserveRatio(image, MAX_WIDTH, MAX_HEIGHT, false);
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	protected void debugLog(DropTargetDragEvent ev) {
		System.out.println("In dragEnter() - " + ev);
		System.out.println("ev.getCurrentDataFlavorsAsList(): "
				+ ev.getCurrentDataFlavorsAsList());
		System.out.println(ev.getSource());
		System.out.println(ev.getSourceActions());
		for (DataFlavor flavor : ev.getCurrentDataFlavors()) {
			System.out.println("Flavor "
					+ flavor.getHumanPresentableName() + " Class: "
					+ flavor.getDefaultRepresentationClassAsString()
					+ "   MimeType: " + flavor.getMimeType()
					+ flavor.match(flavor));
		}
	}

	protected void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	private void ahPickles(Exception e) {
		System.err.println("Drag and drop error - Ah pickles...");
		e.printStackTrace();
	}

}
