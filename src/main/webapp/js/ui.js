var ic, ui;
var header, nav, footer, main;
var metadata_readout, media_options, media_options_menu;

function clearUi() {
	$.each(ui, function(item) {
		this.root.css('display','none');
		this.tab.removeClass("active");
	});
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

function initLayout() {
	header = $('#ic_header');
	nav = $('#ic_nav');
	footer = $('#ic_footer');
	main = $('#ic_main');
	
	metadata_readout = $("#metadata_readout");
	media_options = $("#media_options");
	media_options_menu = $(media_options.children("ul")[0]);
	
	main.css('height',(($(window).height() - 100) - (header.height() + footer.height())));
	
	ui = {
		media: {
			root: $('#ui_media'),
			tab: $(nav.children()[0]),
			unload: function() {
				$.each(media_options.children(), function(item) {
					if(item > 0)
						$(this).css('display','none');
				});
				$("#media_title").css('display','none');
				metadata_readout.css('display','none');
			},
			load: function() {
				alert('loading media ui');
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
	
	media_options_menu.mouseleave(function() {
		$(this).css('display','none');
	});
	
	$.each(media_options_menu.children("li"), function(item) {
		$(this).mouseover(function() {
			$(this).addClass("hover");
		});
		$(this).mouseout(function() {
			$(this).removeClass("hover");
		});
	});
	
	metadata_readout.css({
		'height': main.height() * 0.92
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