function handleDesktopServiceMessage(data) {
	if(data.command) {
		switch(data.command) {
			case Command.LOAD_MEDIA:
				if(data.metadata != null) {
					if(!isEmptyObject(data.metadata)) {
						media = new MediaStub();
						media.attachMedia(data.metadata);
						console.info("WHY U NO?");
					} else {
						console.info("this object is empty.  did user cancel?");
					}
				} else {
					Console.info("MEDIA IS NULL");
					var opts = [
						{
							label: Alerts.Basic.YES, 
							action: media.displayOnScreen()
						},
						{
							label: Alerts.Basic.No, 
							action: removeAlert()
						}
					]
					showAlert(Alerts.Errors.MAIN_TITLE, Alerts.Errors.NO_METADATA, true, null, opts);
				}
					
				break;

		}
	}
}

function broadcast(obj) {
	cometd.publish(dc, obj);
}

function isEmptyObject(obj) {
	return Object.keys(obj).length === 0;
}