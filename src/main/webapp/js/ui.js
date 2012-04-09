function clearUi() {
	$.each(ui, function(item) {
		this.root.css('display','none');
		this.tab.removeClass("active");
	});
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
	which.tab.addClass("active");
}

function showMenu(root, which) {
	if(which == undefined)
		which = 0;
	$($("#" + root).children("ul")[which]).css('display','block');
}

function showAlert(title, txt, isDismissable, replacements) {
	if(title == undefined || title == null) {
		title = $("#alert_title").html();
	}
	
	$('#alert_title').html(title);
	$('#alert_text').html(txt);
	
	if(isDismissable) {
		$("#alert_button").css('display','block');
	} else {
		$("#alert_button").css('display','none');
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
	
	alert_holder = $("#alert_holder");
	alert_holder.css('margin-top', ($(window).height()/2) -100);
	
	ui = {
		media: {
			root: $('#ui_media'),
			tab: $(nav.children()[0]),
			load: function() {
				
			},
			unload: function() {
				
			}
		},
		submissions: {
			root: $('#ui_submissions'),
			tab: $(nav.children()[1])
		},
		admin: {
			root: $('#ui_admin'),
			tab: $(nav.children()[2])
		},
		help: {
			root: $('#ui_help'),
			tab: $(nav.children()[3])
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
		
	});
	
	ic.run('#/');
}

$(document).ready(function() {
	initLayout();
});