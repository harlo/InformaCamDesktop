function loadMedia() {
	media = new MediaStub();
	showAlert(Alerts.MediaLoading.MAIN_TITLE, Alerts.MediaLoading.SELECT_MEDIA, false);
	broadcast({
		attempt: Command.LOAD_MEDIA
	});
	console.log('submitting');
}

function MediaStub() {
	var paths, currentView, currentDisplay, informa, submissionInfo, availableViews;
	
	this.availableViews = new Array();
	
	function setPath(which, path) {
		switch(which) {
			case MediaPaths.LOCAL:
				this.paths.local = path;
				break;
			case MediaPaths.CLONE:
				this.paths.clone = path;
				break;
		}
	}
	
	function addView(which) {
		this.availableViews.push(which);
	}
	
	this.setCurrentDisplay = function(display) {
		if($.inArray(display, this.availableViews) != -1) {
			this.currentDisplay = display;
		} else {
			showAlert(Alerts.Errors.MAIN_TITLE, Alerts.Errors.SELECTED_VIEW, false, {"%view": Display.Names[display]});
		}
	}
	
	function setCurrentView(view) {
		this.currentView = view;
	}
	
}