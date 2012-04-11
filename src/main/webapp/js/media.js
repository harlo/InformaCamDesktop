function loadMedia() {
	showAlert(Alerts.MediaLoading.MAIN_TITLE, Alerts.MediaLoading.SELECT_MEDIA, false, null);
	broadcast({
		attempt: Command.LOAD_MEDIA
	});
}

function MediaStub() {
	var mediaPaths, currentView, currentDisplay, availableDisplays;
	var informa, type, title, submissionInfo;
	var options;
	
	this.availableDisplays = new Array();
	this.options = new Array();
	
	this.attachMedia = function(data) {
		this.informa = data;
		
		this.paths = {
			local: null,
			clone: null
		};
		
		this.addDisplay(Display.REDACTED);
		this.type = this.informa.data.sourceType;
		
		this.setCurrentDisplay(Display.REDACTED);
		this.title = this.informa.genealogy.localMediaPath.substring(
			this.informa.genealogy.localMediaPath.lastIndexOf("/") + 1
		);
		
		this.setPath(MediaPaths.LOCAL, this.informa.localPath);
		this.setPath(MediaPaths.CLONE, this.informa.localPath.substring(
			this.informa.localPath.lastIndexOf("/") + 1
		));
		
		switch(this.type) {
			case MediaTypes.IMAGE:
				this.options.push(
					{
						label: Menus.Media.Image.SHARE_IMAGE,
						action: function() {
							media.shareMedia();
						}
					}
				);
				break;
			case MediaTypes.VIDEO:
				break;
		}
		
		this.options.push({
			label: Menus.Media.EXPORT_METADATA,
			action: media.exportMetadata
		});
		
		this.options.push({
			label: Menus.Media.VIEW_SUBMISSION_INFO,
			action: media.viewSubmissionInfo
		});
		
		placeMedia();
	};
	
	this.shareMedia = function() {
		console.info("SHARING THE MEDIA");
	};
	
	this.exportMetadata = function() {
	
	};
	
	this.viewSubmissionInfo = function() {
	
	};
	
	this.setPath = function(which, path) {
		switch(which) {
			case MediaPaths.LOCAL:
				this.paths.local = path;
				
				break;
			case MediaPaths.CLONE:
				this.paths.clone = path;
				break;
		}
	};
	
	this.addDisplay = function(which) {
		this.availableDisplays.push(which);
	};
	
	this.setCurrentDisplay = function(display) {
		if($.inArray(display, this.availableDisplays) != -1) {
			this.currentDisplay = display;
		} else {
			showAlert(Alerts.Errors.MAIN_TITLE, Alerts.Errors.SELECTED_VIEW, false, {"%view": Display.Names[display]}, null);
			switch(this.currentDisplay) {
				case Displays.REDACTED:
					toggleValue(document.getElementById('display_redacted'));
					break;
				case Displays.UNREDACTED:
					toggleValue(document.getElementById('display_unredacted'));
					break;
			}
		}
	};
	
	this.setCurrentView = function(view) {
		this.currentView = view;
	};
	
}