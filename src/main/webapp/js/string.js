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
	}
};

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