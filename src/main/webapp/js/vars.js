var cometd;
var dc = '/service/desktopConnection';

var Command = {
	CHOOSE_MEDIA: 99,
	LOAD_MEDIA: 100,
	WAIT_FOR_PROCESS: 101,
	VIEW_SUBMISSIONS: 102
};

var media;
var MediaTypes = {
	VIDEO: 102,
	IMAGE: 101,
	Names: {
		102: MediaTypes_str.VIDEO,
		101: MediaTypes_str.IMAGE
	}
};

var MediaPaths = {
	LOCAL: 200,
	REDACTED: 201,
	UNREDACTED: 202
};

var Display = {
	REDACTED: 200,
	UNREDACTED: 201,
	Names: {
		200: Display_str.REDACTED,
		201: Display_str.UNREDACTED
	}
};

var View = {
	NORMAL : 300,
	MAP: 301,
	MOTION: 302,
	NETWORK: 303,
	Names: {
		300: View_str.NORMAL,
		301: View_str.MAP,
		302: View_str.MOTION,
		303: View_str.NETWORK
	}
};

var OwnershipTypes = {
	INDIVIDUAL: 400,
	ORGANIZATION: 401,
	Names: {
		400: Metadata.Intent.OwnershipTypes.INDIVIDUAL,
		401: Metadata.Intent.OwnershipTypes.ORGANIZATION
	}
};

var ImageRegions = {
	IDENTIFY: {
		name: "org.witness.ssc.image.filters.InformaTagger",
		label: Metadata.Data.ImageRegions.Filters.IDENTIY
	},
	PIXELATE: {
		name: "org.witness.ssc.image.filters.PixelizeObscure",
		label: Metadata.Data.ImageRegions.Filters.PIXELATE
	},
	BACKGROUND_PIXELATE: {
		name: "org.witness.ssc.image.filters.CrowdPixelizeObscure",
		label: Metadata.Data.ImageRegions.Filters.BACKGROUND_PIXELATE
	},
	REDACT: {
		name: "org.witness.ssc.image.filters.SolidObscure",
		label: Metadata.Data.ImageRegions.Filters.REDACT
	}
};

var Styles = {
	Color: {
		ACTIVE: "#C6FF00",
		INACTIVE: "#999999",
		INACTIVE_TAGGED: "#8FAE22"
	}
};

var ic, ui;
var header, nav, footer, main, alert_holder, popup_holder, spinner_holder;
var metadata_readout, media_options, media_options_menu, media_frame, media_overlay, mcx;
var frameRatio;
var regionsTraced = true;

function getNameByValue(group, value) {
	var name;
	for(var key in group) {
		if(key == value)
			name = group[key]
	}
	return group[value];
}

function parseDate(millis) {
	var date = new Date(millis).toString().split(" ");;
	var dstr = new Array();
	dstr.push(date[0]);
	dstr.push(date[1]);
	dstr.push(date[2] + ",");
	dstr.push(date[3]);
	dstr.push("(" + date[4]);
	dstr.push(date[5] + ")");
	
	return dstr.join(" ");
}