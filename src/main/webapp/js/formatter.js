function formatSubmissionMini(data) {
	var obj = document.createElement('ul');
	$.each(data, function() {
		var d = this;
		var dPaths = d.attachment.split("/");
		$(obj).append(
			$(document.createElement('li')).append(
				$(document.createElement('a'))
					.html("<b>" + dPaths[dPaths.length - 1] + "</b> (" + Submissions.Fields.SUBMITTED + " " + formatTimestampForHumans(d.timestamp_submitted) + ")")
					.click(function() {
						removePopup();
						showSpinner();
						broadcast({
							attempt: Command.LOAD_MEDIA,
							options: {
								attachment: d.attachment
							}
						});
					})
			)
		);
	});
	return obj;
}

function formatSubmissionsForTable(data) {
	var obj = new Array();
	$.each(data, function() {
		var d = this;
		var dPaths = d.attachment.split("/");
		var row = $(document.createElement('tr'))
			.append(
				$(document.createElement('td'))
					.html("<b>" + dPaths[dPaths.length -1] + "</b>")
					.attr({
						'class': 'ic_toMedia',
						'id': d.attachment
					})
			)
			.append(
				$(document.createElement('td'))
					.html(MediaTypes.Names[d.media_type])
			)
			.append(
				$(document.createElement('td'))
					.html(d.timestamp_created ? formatTimestampForHumans(d.timestamp_created) : Submissions_str.N_A)
			)
			.append(
				$(document.createElement('td'))
					.html(d.timestamp_submitted ? formatTimestampForHumans(d.timestamp_submitted) : Submissions_str.N_A)
			)
			.append(
				$(document.createElement('td'))
					.html(d.timestamp_submitted ? formatTimestampForHumans(d.timestamp_submitted) : Submissions_str.N_A)
			)
			.append(
				$(document.createElement('td'))
					.html(d.submitted_by ? d.submitted_by : Submissions_str.UNKNOWN)
			)
			
		obj.push(row);
	});
	return obj;
}

function formatTimestampForHumans(ts) {
	var date = new Date(ts * 1000);
	return date.getDate() + " " + TimeAndDate.Months.SHORT[date.getMonth()] + " " + date.getFullYear() + ", " + date.getHours() + ":" + date.getMinutes();
}