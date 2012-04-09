package org.witness.informa;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.AbstractService;
import org.witness.informa.utils.Constants.DC.Attempts;
import org.witness.informa.utils.Constants.Media;
import org.witness.informa.utils.Constants;
import org.witness.informa.utils.InformaMessage;
import org.witness.informa.utils.MediaLoader;

public class DesktopService extends AbstractService implements Constants {
	MediaLoader ml;
	
	public DesktopService(BayeuxServer bayeux) {
		super(bayeux, "desktopConnection");
		addService("/service/desktopConnection", "desktopResponse");
	}
	
	public void desktopResponse(ServerSession remote, Message message) {
		InformaMessage msg = new InformaMessage(message);
		if(msg.in.containsKey(Attempts.TAG)) {
			long l = (Long) msg.in.get(Attempts.TAG);
			switch((int) l) {
			case Attempts.LOAD_MEDIA:
				try {
					ml = new MediaLoader();
					msg.put(DC.Keys.COMMAND, DC.Commands.LOAD_MEDIA);
					msg.put(DC.Keys.METADATA, ml.loadMedia());
					
				} catch (Exception e) {
					log(e.toString());
				}
				break;
			}
		}
		
		remote.deliver(getServerSession(), "/desktopConnection", msg.out(), null);
	}
	
	private static void log(String l) {
		System.out.println(LOG + ": " + l);
	}
}
