function chooseMedia() {
	showSpinner();
	broadcast({
		attempt: Command.CHOOSE_MEDIA
	});
}

function searchMedia() {

}

function MediaStub() {
	var mediaPaths, currentView, currentDisplay, availableDisplays;
	var informa, type, title, submissionInfo, imageRegions;
	var options;
	
	this.availableDisplays = new Array();
	this.options = new Array();
	this.imageRegions = {
		types: new Array(),
		list: new Array()
	}
	
	this.attachMedia = function(data) {
		this.informa = data;
		
		this.paths = {
			local: null,
			redacted: null,
			unredacted: null
		};
		
		this.addDisplay(Display.REDACTED);
		this.type = this.informa.data.sourceType;
		
		this.title = this.informa.genealogy.localMediaPath.substring(
			this.informa.genealogy.localMediaPath.lastIndexOf("/") + 1
		);
		
		this.setPath(MediaPaths.LOCAL, this.informa.localPath);
		this.setPath(MediaPaths.REDACTED, this.informa.localPath.substring(
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
		
		
		if(this.informa.data.imageRegions) {
			$.each(this.informa.data.imageRegions, function(idx, item) {
				var ir = item;
				if(jQuery.inArray(ir.obfuscationType, media.imageRegions.types) == -1)
					media.imageRegions.types.push(ir.obfuscationType);
				media.imageRegions.list.push(ir);
			});
		}
				
		if(this.imageRegions.types.length > 0 &&
			(
				jQuery.inArray(ImageRegions.PIXELATE.name, this.imageRegions.types) != -1 ||
				jQuery.inArray(ImageRegions.REDACT.name, this.imageRegions.types) != -1
			)
		) {
			this.addDisplay(Display.UNREDACTED);
			this.setPath(MediaPaths.UNREDACTED, this.informa.localPath.substring(
				this.informa.localPath.lastIndexOf("/") + 1,
				this.informa.localPath.length - 4
			) + "_unredacted.jpg");
		}
		
		this.setCurrentDisplay(Display.REDACTED);	
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
			case MediaPaths.REDACTED:
				this.paths.redacted = path;
				break;
			case MediaPaths.UNREDACTED:
				this.paths.unredacted = path;
		}
	};
	
	this.addDisplay = function(which) {
		this.availableDisplays.push(which);
	};
	
	this.setCurrentDisplay = function(display) {
		if($.inArray(display, this.availableDisplays) != -1) {
			this.currentDisplay = display;
			switchDisplay();
		} else {
			showAlert(Alerts.Errors.MAIN_TITLE, Alerts.Errors.SELECTED_VIEW, false, {"%view": Display.Names[display]}, null);
			switch(this.currentDisplay) {
				case Display.REDACTED:
					toggleValue(document.getElementById('display_redacted'));
					break;
				case Display.UNREDACTED:
					toggleValue(document.getElementById('display_unredacted'));
					break;
			}
		}
	};
	
	this.setCurrentView = function(view) {
		this.currentView = view;
	};
}