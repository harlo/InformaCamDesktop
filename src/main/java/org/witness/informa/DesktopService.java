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
	MediaLoader ml = null;
	
	public DesktopService(BayeuxServer bayeux) {
		super(bayeux, "desktopConnection");
		addService("/service/desktopConnection", "desktopResponse");
	}
	
	public void desktopResponse(ServerSession remote, Message message) {
		InformaMessage msg = new InformaMessage(message);
		if(msg.in.containsKey(Attempts.TAG)) {
			long l = (Long) msg.in.get(Attempts.TAG);
			switch((int) l) {
			case Attempts.CHOOSE_MEDIA:
				if(ml == null)
					ml = new MediaLoader();
				
				msg.put(DC.Keys.COMMAND, DC.Commands.CHOOSE_MEDIA);
				msg.put(DC.Keys.METADATA, ml.getSubmissions());
				break;
			case Attempts.LOAD_MEDIA:
				try {
					if(ml == null)
						ml = new MediaLoader();
					
					msg.put(DC.Keys.COMMAND, DC.Commands.LOAD_MEDIA);
					msg.put(DC.Keys.METADATA, ml.loadMedia((String) msg.opts.get(DC.Options.LOCAL_MEDIA_PATH)));
					
				} catch (Exception e) {
					Log(e.toString());
				}
				break;
			case Attempts.VIEW_SUBMISSIONS:
				if(ml == null)
					ml = new MediaLoader();
				
				msg.put(DC.Keys.COMMAND, DC.Commands.VIEW_SUBMISSIONS);
				msg.put(DC.Keys.METADATA, ml.getSubmissions());
				break;
			}
		}
		
		remote.deliver(getServerSession(), "/desktopConnection", msg.out(), null);
	}
	
	public static void Log(String l) {
		System.out.println(LOG + ": " + l);
	}
}
