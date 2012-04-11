var Informa = {
	MetadataReadout: {
		Intent: {
			label: Metadata.Intent.label,
			loads: [
				[Metadata.Intent.SUBMITTED_BY, media.informa.intent.owner.sigKeyId],
				[Metadata.Intent.OWNERSHIP_TYPE, media.informa.intent.owner.ownershipType]
			]
		},
		Genealogy: {
			label: Metadata.Genealogy.label,
			loads: [
				[Metadata.Genealogy.DATE_CREATED, media.informa.genealogy.dateCreated],
				[Metadata.Genealogy.DATE_ACQUIRED, media.informa.genealogy.dateAcquired],
				Metadata.Data.Device.label,
				[Metadata.Data.Device.DEVICE_BLUETOOTH_NAME, media.informa.data.device.bluetoothInfo.deviceBTName],
				[Metadata.Data.Device.DEVICE_BLUETOOTH_ADDRESS,media.informa.data.device.bluetoothInfo.deviceBTAddress],
				[Metadata.Data.Device.DEVICE_IMEI, media.informa.data.device.imei],
				Metadata.Data.Device.Integrity.label,
				[Metadata.Data.Device.Integrity.RATING, 100]
			]
		}
	}
}

function parseForReplacementMetadata(item) {
	var mod = '<h3>' + item + '</h3>';
	
	if(typeof item != "string") {
		var keyword;
		var keywordStart = item[0].indexOf("%=");
		
		if(keywordStart != -1) {
			keyword = item[0].substring(keywordStart);
			
			var keywordEnd = keyword.indexOf(" ");
			
			if(keywordEnd == -1) {
				keywordEnd = keyword.indexOf(")");
			}
			
			if(keywordEnd == -1) {
				keywordEnd = keyword.indexOf(".");
				
			}
			
			if(keywordEnd != -1)
				keyword = keyword.substring(0, keywordEnd);
				
			console.info(keyword);
			mod = item[0].replace(keyword, '<a href="#details/' + keyword.substring(2) + '/' + escape(item[1]) + '">' + item[1] + '</a>');
		} else
			mod = item[1];
	}
	
	return mod;
}