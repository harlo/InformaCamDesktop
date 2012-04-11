var cometd;
var dc = '/service/desktopConnection';

var Command = {
	LOAD_MEDIA: 100,
	WAIT_FOR_PROCESS: 101
};

var media;
var MediaTypes = {
	VIDEO: 102,
	IMAGE: 101
};
var MediaPaths = {
	LOCAL: 200,
	CLONE: 201
}
var Display = {
	REDACTED: 200,
	UNREDACTED: 201,
	Names: {
		200: "Redacted",
		201: "Unredacted"
	}
}

var ic, ui;
var header, nav, footer, main, alert_holder;
var metadata_readout, media_options, media_options_menu, media_frame, media_overlay, mcx;

function getNameByValue(group, value) {
	var name;
	for(var key in group) {
		if(key == value)
			name = group[key]
	}
	return group[value];
}