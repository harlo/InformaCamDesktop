var media;

function loadMedia() {
	cometd.publish(dc, {
		attempt: attempt.LOAD_MEDIA
	});
	showAlert("Media Loading...");
}

function attachMedia(m) {
	ui.media.load.call();
	console.log(m);
	media = {
		type: m.metadata.mediaType,
		dimensions: m.dimensions,
		path: m.filePath
	}
	if(media.type == MediaTypes.VIDEO) {
		
	}
}

$(document).ready(function() {
	if(media == undefined) {
		ui.media.unload.call();
	}
});