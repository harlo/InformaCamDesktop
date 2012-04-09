function handleDesktopServiceMessage(data) {
	if(data.command) {
		switch(data.command) {
			case command.LOAD_MEDIA:
				removeAlert();
				if(data.metadata != null)
					attachMedia(data);
				break;

		}
	}
}

function broadcast(obj) {
	cometd.publish(dc, obj);
}