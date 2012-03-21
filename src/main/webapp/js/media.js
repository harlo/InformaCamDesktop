var media;

function loadMedia() {
	cometd.publish(dc, {
		attempt: attempt.LOAD_MEDIA
	});
}

$(document).ready(function() {
	if(media == undefined) {
		ui.media.unload.call();
	}
});