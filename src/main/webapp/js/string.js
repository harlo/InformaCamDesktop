var TimeAndDate = {
	Days: {
		SHORT: [
			"Mon",
			"Tue",
			"Wed",
			"Thu",
			"Fri",
			"Sat",
			"Sun"
		],
		LONG: [
			"Monday",
			"Tuesday",
			"Wednesday",
			"Thursday",
			"Friday",
			"Saturday",
			"Sunday"
		]
	},
	Months: {
		SHORT: [
			"Jan",
			"Feb",
			"Mar",
			"Apr",
			"May",
			"Jun",
			"Jul",
			"Aug",
			"Sep",
			"Oct",
			"Nov",
			"Dec"
		],
		LONG: [
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"December"
		]
	}
};

var Alerts = {
	Errors: {
		MAIN_TITLE: "Error",
		SELECTED_VIEW: "Sorry.  %view is not an available view.",
		NO_METADATA: "There is no informa-styled metadata attached to this image.  Load anyway?"
	},
	MediaLoading: {
		MAIN_TITLE: "Media Loading...",
		SELECT_MEDIA: "Please select your media",
		WAITING: "Please wait.  Your media is being processed."
	},
	Basic: {
		YES: "Yes",
		NO: "No",
		OK: "OK"
	},
	Submissions: {
		MAIN_TITLE: "No Submissions",
		NO_NEW_SUBMISSIONS: "There are no submissions for you to view."
		
	}
};

var Submissions = {
	MAIN_TITLE: "Submissions",
	CHOOSER_TITLE: "Please choose an image/video...",
	Fields: {
		FILENAME: "Filename",
		MEDIA_TYPE: "Media Type",
		SIZE: "Size",
		TIME_SUBMITTED: "Time Submitted",
		TIME_CREATED: "Time Created",
		TIME_RECEIVED: "Time Received",
		SUBMITTED: "Submitted on",
		SUBMITTED_BY: "Submitted by",
		UNKNOWN: "unknown",
		N_A: "n/a"
	}
};

var Menus = {
	Media: {
		Image: {
			SHARE_IMAGE: "Share Image"
		},
		Video: {
			SHARE_VIDEO: "Share Video"
		},
		EXPORT_METADATA: "Export Metadata As...",
		VIEW_SUBMISSION_INFO: "View Submission Info"
	},
	Main: {
		MEDIA: "Media",
		SUBMISSIONS: "Submissions",
		ADMIN: "Admin",
		HELP: "Help"
	}
};

var Submissions_str = {
	FILENAME: "Filename",
	MEDIA_TYPE: "Media Type",
	SIZE: "Size",
	TIME_SUBMITTED: "Time Submitted",
	TIME_CREATED: "Time Created",
	TIME_RECEIVED: "Time Received",
	SUBMITTED: "Submitted on",
	SUBMITTED_BY: "Submitted by",
	UNKNOWN: "unknown",
	N_A: "n/a"
}

var Display_str = {
	REDACTED: "Redacted",
	UNREDACTED: "Unredacted"
}

var View_str = {
	NORMAL: "Normal View",
	MAP: "Map View",
	MOTION: "Motion View",
	NETWORK: "Network View"
}

var MediaTypes_str = {
	IMAGE: "Image",
	VIDEO: "Video"
}

var ImageRegion_str = {
	ON: "On",
	OFF: "Off"
}

var Metadata = {
	Intent: {
		label: "Intent",
		SUBMITTED_BY: "Submitted by: %=sigKeyId",
		OWNERSHIP_TYPE: "(Ownership type: %=ownershipType)",
		OwnershipTypes: {
			INDIVIDUAL: "Individual",
			ORGANIZATION: "Organization"
		}
	},
	Genealogy: {
		label: "Genealogy",
		DATE_CREATED: "Media created on: %=dateCreated",
		DATE_ACQUIRED: "Acquired by submitting device on: %=dateAcquired"
	},
	Data: {
		label: "Data",
		Device: {
			label: "About the device submitting this media:",
			DEVICE_BLUETOOTH_NAME: "Bluetooth Name: %=deviceBTName",
			DEVICE_BLUETOOTH_ADDRESS: "Bluetooth Address: %=deviceBTAddress",
			DEVICE_IMEI: "Handset IMEI: %=imei",
			Integrity: {
				label: "Device Integrity",
				RATING: "InformaCam is %=deviceIntegrityRating % certain that this media was captured by the device indicated by its handset IMEI."
			}
		},
		ImageRegions: {
			label: "Image Regions",
			Filters: {
				IDENTIFY: "Identify",
				PIXELATE: "Pixelate",
				BACKGROUND_PIXELATE: "Background Pixelate",
				REDACT: "Redact"
			}
		}
	}
};

var Search = {
	MAIN_TITLE: "Search",
	REFINE: "Refine",
	By_Saved_Search: {
		LABEL: "Load saved search..."
	},
	By_Keyword:  {
		LABEL: "By Keyword"
	},
	By_Type: {
		LABEL: "By Type",
		Fields: {
			IMAGE: MediaTypes_str.IMAGE,
			VIDEO: MediaTypes_str.VIDEO
		}
	},
	By_Timeframe: {
		LABEL: "By Timeframe",
		Fields: {
			PAST_24_HOURS: "Past 24 hours",
			PAST_WEEK: "Past week",
			PAST_MONTH: "Past month",
			PAST_YEAR: "Past year",
			CUSTOM_RANGE: "Custom range..."
		}
	},
	By_Location: {
		LABEL: "By Location",
		Fields: {
			HINT: "enter coordinates, city, or country",
			MAP: "Map..."
		}
	},
	Results: {
		LABEL: "Results",
		Fields: {
		
		}
	}
}