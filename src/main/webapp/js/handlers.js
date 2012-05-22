function handleDesktopServiceMessage(data) {
	if(data.command) {
		switch(data.command) {
			case Command.VIEW_SUBMISSIONS:
				if(data.metadata != null) {
					populateTable(formatSubmissionsForTable(data.metadata), $("#ui_submissions"));
					removeSpinner();
				} else {
					var opts = [
						{
							label: Alerts.Basic.OK,
							action: removeAlert()
						}
					];
					showAlert(Alerts.Submissions.MAIN_TITLE, Alerts.Submissions.NO_NEW_SUBMISSIONS, true, null, opts);
				}
				break;
			case Command.CHOOSE_MEDIA:
				if(data.metadata != null) {
					removeSpinner();
					showPopup(
						Submissions.CHOOSER_TITLE,
						formatSubmissionMini(data.metadata)
					);
				} else {
					var opts = [
						{
							label: Alerts.Basic.OK,
							action: removeAlert()
						}
					];
					showAlert(Alerts.Submissions.MAIN_TITLE, Alerts.Submissions.NO_NEW_SUBMISSIONS, true, null, opts);
				}
				break;
			case Command.LOAD_MEDIA:
				if(data.metadata != null) {
					if(!isEmptyObject(data.metadata)) {
						removeSpinner();
						media = new MediaStub();
						media.attachMedia(data.metadata);
					}
				} else {
					console.info("MEDIA IS NULL");
					var opts = [
						{
							label: Alerts.Basic.YES, 
							action: media.displayOnScreen()
						},
						{
							label: Alerts.Basic.No, 
							action: removeAlert()
						}
					];
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