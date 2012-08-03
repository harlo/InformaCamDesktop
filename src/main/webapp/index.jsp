<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <script type="text/javascript" src="js/string.js"></script>
    <script type="text/javascript" src="js/vars.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/json2.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/org/cometd.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery.cometd.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script type="text/javascript" src="js/formatter.js"></script>
    <%--
    The reason to use a JSP is that it is very easy to obtain server-side configuration
    information (such as the contextPath) and pass it to the JavaScript environment on the client.
    --%>
    <script type="text/javascript">
        var config = {
            contextPath: '${pageContext.request.contextPath}'
        };
    </script>
    <script type="text/javascript" src="js/handlers.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/sammy.js"></script>
	<script type="text/javascript" src="js/ui.js"></script>
	<script type="text/javascript" src="js/media.js"></script>
	<script type="text/javascript" src="js/submissions.js"></script>
	<link rel="stylesheet" type="text/css" href="css/ic.css" />
    <title>InformaCam - powered by The Guardian Project</title>
</head>
<body>
	<div id="spinner_holder">
		<img src="images/spinner_gif.gif" />
	</div>
	<div id="alert_holder">
		<h1 id="alert_title"></h1>
		<div id="alert_text"></div>
		<div id="alert_options"></div>
	</div>
	
	<div id="popup_holder">
		<h1 id="popup_title"></h1>
		<div id="popup_content"></div>
	</div>
	
	<div id="ic_header">
		<div id="ic_logo">
			<table>
				<tr>
					<td><img src="images/ic_logo.png" /></td>
					<td>
						<h1>InformaCam</h1>
						<p>Powered by<br /><a href="#help/">The Guardian Project</a></p>
					</td>
				</tr>
			</table>
		</div>
		
		<ul id="ic_nav">
			<li><a href="#media/">
				<script type="text/javascript">
					document.write(Menus.Main.MEDIA);
				</script>
			</a></li>
			<li><a href="#submissions/">
				<script type="text/javascript">
					document.write(Menus.Main.SUBMISSIONS);
				</script>
			</a></li>
			<li><a href="#admin/">
				<script type="text/javascript">
					document.write(Menus.Main.ADMIN);
				</script>
			</a></li>
			<li><a href="#help/">
				<script type="text/javascript">
					document.write(Menus.Main.HELP);
				</script>
			</a></li>
		</ul>
	</div>
	
	<div id="ic_main">
		<div id="ui_media">
			<table class="ic_table">
				<tr>
					<td id="media_holder" width="60%">

						<div id="media_options">
							<ul class="ic_menu_button">
								<li>
									<a onclick="chooseMedia();">Load</a>
								</li>
								<li>
									<a href="#search/">Search</a>
								</li>
								<li>
									<a>Views</a>
									<div class="ic_dropdown">
										<ul id="views_menu">
											<li onclick="media.setCurrentView(View.NORMAL);">
												<script type="text/javascript">
													document.write(View_str.NORMAL);
												</script>
											</li>
											<li onclick="media.setCurrentView(View.MAP);">
												<script type="text/javascript">
													document.write(View_str.MAP);
												</script>
											</li>
											<li onclick="media.setCurrentView(View.MOTION);">
												<script type="text/javascript">
													document.write(View_str.MOTION);
												</script>
											</li>
											<li onclick="media.setCurrentView(View.NETWORK);">
												<script type="text/javascript">
													document.write(View_str.NETWORK);
												</script>
											</li>
										</ul>
									</div>
								</li>
								<li>
									<a>Options</a>
									<div class="ic_dropdown">
										<ul id="options_menu"></ul>
									</div>
								</li>
								<li class="ic_menu_buttonOverride">
									<span>Display: </span>
									<div class="ic_toggle" id="mediaView">
										<a id="display_redacted" onclick="toggleValue(this);media.setCurrentDisplay(Display.REDACTED);" rel="redacted" class="selected">
											<script type="text/javascript">
												document.write(Display_str.REDACTED);
											</script>
										</a><a id="display_unredacted" onclick="toggleValue(this);media.setCurrentDisplay(Display.UNREDACTED);" rel="unredacted">
											<script type="text/javascript">
												document.write(Display_str.UNREDACTED);
											</script>
										</a>
									</div>
								</li>
								<li class="ic_menu_buttonOverride">
									<span>ImageRegion Tracing: </span>
									<div class="ic_toggle" id="imageRegionView">
										<a id="irTracing_on" onclick="toggleValue(this);traceRegions();" rel="tracingOn" class="selected">
											<script type="text/javascript">
												document.write(ImageRegion_str.ON);
											</script>
										</a><a id="irTracing_off" onclick="toggleValue(this);hideRegions();" rel="tracingOff">
											<script type="text/javascript">
												document.write(ImageRegion_str.OFF);
											</script>
										</a>
									</div>
								</li>
							</ul>
						</div>
						<div id="media_frame">
							<canvas id="media_overlay" />
						</div>
					</td>
					<td id="metadata_holder">
						<h2 id="media_title"></h2>
						<div id="metadata_readout"></div>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="ui_submissions">
			<table>
				<tr class="tr_header">
					<td id="submissions_filename">
						<script type="text/javascript">
							document.write(Submissions_str.FILENAME);
						</script>
					</td>
					<td id="submissions_mediaType">
						<script type="text/javascript">
							document.write(Submissions_str.MEDIA_TYPE);
						</script>
					</td>
					<td id="submissions_timeCreated">
						<script type="text/javascript">
							document.write(Submissions_str.TIME_CREATED);
						</script>
					</td>
					<td id="submissions_timeSubmitted">
						<script type="text/javascript">
							document.write(Submissions_str.TIME_SUBMITTED);
						</script>
					</td>
					<td id="submissions_timeReceived">
						<script type="text/javascript">
							document.write(Submissions_str.TIME_RECEIVED);
						</script>
					</td>
					<td id="submissions_submittedBy">
						<script type="text/javascript">
							document.write(Submissions_str.SUBMITTED_BY);
						</script>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="ui_admin">
			<h1>admin main</h1>
		</div>
		
		<div id="ui_help">
			<h1>help main</h1>
		</div>
		
		<div id="ui_details">
			<h1>details viewer</h1>
		</div>
		
		<div id="ui_search">
			<h1>
				<script type="text/javascript">
					document.write(Search.MAIN_TITLE);
				</script>
			</h1>
			<table class="ic_search ic_table">
				<tr>
					<td width="20%">
						<div id="search_refine_options">
							<h2>
								<script type="text/javascript">
									document.write(Search.REFINE);
								</script>
							</h2>
							
							<a onclick="" class="ic_as_li" id="media_saved_search">
								<script type="text/javascript">
									document.write(Search.By_Saved_Search.LABEL);
								</script>
							</a>
							
							<h3 style="margin-top:10px;">
								<script type="text/javascript">
									document.write(Search.By_Keyword.LABEL);
								</script>
							</h3>
							<input id="media_keyword" type="text" />
							
							<h3>
								<script type="text/javascript">
									document.write(Search.By_Type.LABEL);
								</script>
							</h3>
							<ul id="media_type">
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Type.Fields.IMAGE);
									</script>
								</a></li>
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Type.Fields.VIDEO);
									</script>
								</a></li>
							</ul>
							
							<h3>
								<script type="text/javascript">
									document.write(Search.By_Timeframe.LABEL);
								</script>
							</h3>
							<ul id="media_timeframe">
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Timeframe.Fields.PAST_24_HOURS);
									</script>
								</a></li>
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Timeframe.Fields.PAST_WEEK);
									</script>
								</a></li>
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Timeframe.Fields.PAST_MONTH);
									</script>
								</a></li>
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Timeframe.Fields.PAST_YEAR);
									</script>
								</a></li>
								<li><a onclick="">
									<script type="text/javascript">
										document.write(Search.By_Timeframe.Fields.CUSTOM_RANGE);
									</script>
								</a></li>
							</ul>
							
							<h3>
								<script type="text/javascript">
									document.write(Search.By_Location.LABEL);
								</script>
							</h3>
							<input id="media_location"  class="hinted" type="text" />
							<a class="ic_as_li" onclick="">
								<script type="text/javascript">
									document.write(Search.By_Location.Fields.MAP);
								</script>
							</a>
						</div>
					</td>
					<td id="search_results_holder" style="visibility:hidden">
						<div id="search_results">
							
						</div>
					</td>
				</tr>
			</table>
		</div>
		
	</div>
	
	<div id="ic_footer">
		<div id="ic_login"></div>
	</div>
	
	</body>
</html>