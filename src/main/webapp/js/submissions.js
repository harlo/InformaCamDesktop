function getSubmissions() {
	showSpinner();
	broadcast({
		attempt: Command.VIEW_SUBMISSIONS
	});
}

function selectSubmission(el) {
	broadcast({
		attempt: Command.LOAD_MEDIA,
		options: {
			attachment: $(el).attr('id')
		}
	});
}