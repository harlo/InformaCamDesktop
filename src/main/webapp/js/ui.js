function clearUi() {
	$.each(ui, function(item) {
		this.root.css('display','none');
		if(this.tab)
			this.tab.removeClass("active");
	});
	
	alert_holder.css('display','none');
	popup_holder.css('display','none');
	spinner_holder.css('display','none');
}

function toggleValue(el, isMulti) {
	var group = $(el).parent();
	$(el).addClass('selected');
	if(isMulti == undefined) {
		$.each(group.children('a'), function() {
			if(this != el)
				$(this).removeClass('selected');
		});
	}
}

function launchUi(which) {
	clearUi();
	which.root.css('display','block');
	if(which.tab)
		which.tab.addClass("active");
	if(which.init)
		which.init.call();
}

function showMenu(root, which) {
	if(which == undefined)
		which = 0;
	$($("#" + root).children("ul")[which]).css('display','block');
}

function toggleMediaView(state) {
	if(state) {
		$.each($($("#media_options").children('ul')[0]).children('li'), function(idx) {
			if(idx > 1)
				$(this).css('visibility','visible');
		});
		$.each($("#metadata_holder").children(), function() {
			$(this).css('visibility','visible');
		});
		$("#media_frame").css('visibility','visible');
		
	} else {
		$.each($($("#media_options").children('ul')[0]).children('li'), function(idx) {
			if(idx > 1)
				$(this).css('visibility','hidden');
		});
		$.each($("#metadata_holder").children(), function() {
			$(this).css('visibility','hidden');
		});
		$("#media_frame").css('visibility','hidden');
	}
}

function setImageRatio() {
	var ratio = media.informa.imageDimensions[0]/media.informa.imageDimensions[1];
	var displayWidth, displayHeight;
	var maxWidth = media_frame.width() * 0.9;
	var maxHeight = media_frame.height() * 0.9;
	
	if(
		media.informa.imageDimensions[0] > media.informa.imageDimensions[1] &&
		ratio <= 1
	) {
		displayWidth = maxWidth;
		displayHeight = displayWidth * ratio;
	} else if(media.informa.imageDimensions[1] >= maxHeight) {
		displayHeight = maxHeight;
		displayWidth = displayHeight/ratio;
	} else if(media.informa.imageDimensions[0] == media.informa.imageDimensions[1]) {
		displayHeight = displayWidth = maxHeight;
	}
	
	frameRatio = displayWidth/maxWidth;
	console.info("frameRation is supposedly: " + frameRatio);
	
	var margLeft = (parseInt(maxWidth) - parseInt(displayWidth)) * 0.5;
	var margTop = (parseInt(maxHeight) - parseInt(displayHeight)) * 0.5;
	
	console.info("max width: " + media_frame.width());
	console.info("max height: " + media_frame.height());
	
	console.info("width: " + displayWidth);
	console.info("height: " + displayHeight);

	console.info("marLeft: " + margLeft);
	console.info("margTop: " + margTop);
	
	media_overlay.prop({
			'width' : displayWidth,
			'height' : displayHeight
	});
	
	media_overlay.css({
		'margin-left': margLeft,
		'margin-top': margTop
	});
}

function placeMedia() {
	if(media != null && media != undefined) {
		$("#media_title").html(media.title);
		setImageRatio();
		loadMediaOptions();
		setMetadata();
		toggleMediaView(true);
		removeAlert();
	}
}

function loadMediaOptions() {
	$("#options_menu").empty();
	$.each(media.options, function() {
		var opt = this;
		$("#options_menu").append(
			$(document.createElement('li'))
				.html(opt.label)
				.click(function() {opt.action.call();})
				.mouseenter(function() {
					$(this).addClass('hover');
				})
				.mouseleave(function() {
					$(this).removeClass('hover');
				})
		);
	});
}

function traceRegions() {
	regionsTraced = true;
	
	mcx.lineWidth = 4;
	$.each(media.imageRegions.list, function() {
		if(this.obfuscationType == ImageRegions.IDENTIFY)
			mcx.strokeStyle = Styles.Color.INACTIVE_TAGGED;
		else
			mcx.strokeStyle = Styles.Color.INACTIVE;
			
		mcx.strokeRect(
			this.regionCoordinates.region_left, 
			this.regionCoordinates.region_top,
			this.regionDimensions.region_height,
			this.regionDimensions.region_width
		);
		
	});
}

function hideRegions() {
	regionsTraced = false;
	mcx.clearRect(0, 0, $(media_overlay).width(), $(media_overlay).height());
	
}

function setMetadata() {
	$(document.getElementsByTagName('HEAD').item(0)).append(
		$(document.createElement('script'))
			.prop({
				'type': 'text/javascript',
				'src': 'js/informa.js'
			})
	);
	
	$("#metadata_readout").empty();
	$.each(Informa.MetadataReadout, function() {
		var dataGroup = this;
		var dataPoints = dataGroup.loads;
		
		$("#metadata_readout").append(
			$(document.createElement('h2'))
				.html(dataGroup.label)
		);
		
		var readout = document.createElement('ul');
		$.each(dataPoints, function(idx, item) {
			$(readout).append(
				$(document.createElement('li'))
					.html(parseForReplacementMetadata(item))
			);
		});
		
		$("#metadata_readout").append(readout);

	});
}

function switchDisplay() {
	var currentDisplay;
	switch(media.currentDisplay) {
		case Display.REDACTED:
			currentDisplay = media.paths.redacted;
			break;
		case Display.UNREDACTED:
			currentDisplay = media.paths.unredacted;
			break;
	}
		
	if(media.type == MediaTypes.IMAGE) {
		media_overlay.css({
			'background-image': "url('images/session_cache/" + currentDisplay + "')",
			'background-repeat': 'no-repeat',
			'background-size': 'contain',
			'background-position': 'center'
		});
	} else if(media.type == MediaTypes.VIDEO) {
	
	}
}

function showSpinner() {
	spinner_holder.css('display','block');
}

function removeSpinner() {
	spinner_holder.css('display','none');
}

function showPopup(title, content) {
	if(title == undefined || title == null) {
		title = $("#popup_title").html();
	}
	
	$("#popup_title").html(title);
	$("#popup_content").empty();
	$("#popup_content").append(content);
	
	popup_holder.css('display','block');
}

function removePopup() {
	popup_holder.css('display','none');
}

function showAlert(title, txt, isDismissable, replacements, options) {
	if(title == undefined || title == null) {
		title = $("#alert_title").html();
	}
	
	$('#alert_title').html(title);
	$('#alert_text').html(txt);
	
	$("#alert_options").empty();
	if(isDismissable) {
		if(options != null && options != undefined ) {
			$.each(options, function() {
				$("#alert_options").append(
					$(document.createElement('a'))
						.html(this.label)
						.addClass('dismissal')
						.click(function() {this.action.call();})
				);
			});
		} else {
			$("#alert_options").append(
				$(document.createElement('a'))
					.html(Alerts.Basic.OK)
					.addClass('dismissal')
					.click(function() {removeAlert();})
			);
		}
		$("#alert_button").css('display','block');
	} else {
		$("#alert_button").css('display','none');
		setTimeout(removeAlert, 1750);
	}
	
	if(replacements != undefined && replacements != null) {
		for(var key in replacements) {
			txt = $("#alert_text").html().replace(key, replacements[key]);
			$("#alert_text").html(txt);
		}
	}
	
	alert_holder.css('display','block');	
}

function removeAlert() {
	alert_holder.css('display','none');
}

function populateTable(data, root) {
	var table = $(root).children("table")[0];
	$.each($($(table).children("tbody")[0]).children("tr"), function() {
		if(!$(this).hasClass("tr_header"))
			$(this).remove();
	});
	
	$.each(data, function() {
		$(table).append(this);
	});
	$("#ui_submissions").css('display','block');
}

function initLayout() {
	header = $('#ic_header');
	nav = $('#ic_nav');
	footer = $('#ic_footer');
	main = $('#ic_main');
	
	metadata_readout = $("#metadata_readout");
	media_frame = $("#media_frame");
	
	main.css('height',(($(window).height() - 100) - (header.height() + footer.height())));
	
	metadata_readout.css('height',main.height());
	media_frame.css('height', main.height());
	
	media_overlay = $("#media_overlay");
	mcx = document.getElementById("media_overlay").getContext("2d");
	
	alert_holder = $("#alert_holder");
	alert_holder.css('margin-top', ($(window).height()/2) -100);
	
	popup_holder = $("#popup_holder");
	popup_holder.css('margin-top', $(window).height()/4);
	
	spinner_holder = $("#spinner_holder");
	spinner_holder.css('margin-top', $(window).height()/2 - 100);
	
	ui = {
		media: {
			root: $('#ui_media'),
			tab: $(nav.children()[0])
		},
		submissions: {
			root: $('#ui_submissions'),
			tab: $(nav.children()[1]),
			init: function() {
				getSubmissions();
				
			}
		},
		admin: {
			root: $('#ui_admin'),
			tab: $(nav.children()[2])
		},
		help: {
			root: $('#ui_help'),
			tab: $(nav.children()[3])
		},
		details: {
			root: $("#ui_details")
		},
		search: {
			root: $("#ui_search"),
			tab: $(nav.children()[0])
		}
	};
	
	$.each(ui, function(item) {
		this.root.css({
			'width': main.width(),
			'height': main.height()
		});
	});
	
	$(".ic_menu_button li").mouseenter(function() {
		if($(this).children('div.ic_dropdown') != undefined) {
			$($(this).children('div.ic_dropdown')[0]).css('display','block');
		}
	});
	
	$(".ic_menu_button li").mouseleave(function() {
		if($(this).children('div.ic_dropdown') != undefined) {
			$($(this).children('div.ic_dropdown')[0]).css('display','none');
		}
	});
	
	$(".ic_menu_button li div ul li").mouseenter(function() {
		$(this).addClass('hover');
	});
	
	$(".ic_menu_button li div ul li").mouseleave(function() {
		$(this).removeClass('hover');
	});
	
	$(".tr_header td").click(function() {
		resortListBy($(this).attr('id'));
	});
	
	toggleMediaView(false);
	
	ic = Sammy("#ic_main", function() {
		this.get('#/', function() {
			clearUi();
		});
		
		this.get('#media/', function() {
			launchUi(ui.media);
		});
		
		this.get('#submissions/', function() {
			launchUi(ui.submissions);
		});
				
		this.get('#admin/', function() {
			launchUi(ui.admin);
		});
		
		this.get('#help/', function() {
			launchUi(ui.help);
		});
		
		this.get('#details/:dType/:dId', function() {
			launchUi(ui.details);
		});
		
		this.get('#search/', function() {
			launchUi(ui.search);
		});
		
		$(".ic_toMedia").live('click', function() {
			window.location = '#media/';	//why does sammy.redirect not work?
			selectSubmission(this);
		});
		
	});
	
	ic.run('#media/');
}

$(document).ready(function() {
	initLayout();
});