function getSubmissions() {
	showSpinner();
	broadcast({
		attempt: Command.VIEW_SUBMISSIONS
	});
}

function selectSubmission(el) {
	showSpinner();
	broadcast({
		attempt: Command.LOAD_MEDIA,
		options: {
			attachment: $(el).attr('id')
		}
	});
}